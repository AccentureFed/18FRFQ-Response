import grails.util.Environment

import java.lang.management.ManagementFactory

import javax.servlet.*

import org.grails.plugins.metrics.groovy.HealthChecks
import org.grails.plugins.metrics.groovy.Metrics

import com.afs.security.Role
import com.afs.security.User
import com.afs.security.UserRole
import com.codahale.metrics.jvm.BufferPoolMetricSet
import com.codahale.metrics.jvm.GarbageCollectorMetricSet
import com.codahale.metrics.jvm.MemoryUsageGaugeSet
import com.codahale.metrics.jvm.ThreadStatesGaugeSet
import com.codahale.metrics.jvm.FileDescriptorRatioGauge
import com.codahale.metrics.servlet.InstrumentedFilter

import jigsaw.CacheUpdateJob

class BootStrap {
    
    def grailsApplication

    def init = { servletContext ->
        if(Environment.current != Environment.TEST) {

            // only add the admin user once
            if(User.count() == 0) {
                // add admin user
                def adminRole = new Role()
                adminRole.authority = grailsApplication.config.security.adminRole
                adminRole.save()

                // create a super user
                def adminUser = new User()
                adminUser.username = "admin"
                adminUser.password = "afs18F"
                adminUser.save()

                UserRole.create(adminUser, adminRole)
            }

            /**
             *Adding Metrics
             */
            log.debug("Registering Http Instrumented Filter")
            servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE, Metrics.getRegistry())
            FilterRegistration.Dynamic metricsFilter = servletContext.addFilter('webappMetricsFilter', new InstrumentedFilter())
            metricsFilter.addMappingForUrlPatterns(null, true, '/*')
            metricsFilter.setAsyncSupported(true)
            log.debug("Registering JVM gauges")
            Metrics.getRegistry().register("jvm.buffers", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()))
            Metrics.getRegistry().register("jvm.gc", new GarbageCollectorMetricSet())
            Metrics.getRegistry().register("jvm.memory", new MemoryUsageGaugeSet())
            Metrics.getRegistry().register("jvm.threads", new ThreadStatesGaugeSet())
            Metrics.getRegistry().register("jvm.files", new FileDescriptorRatioGauge())
            log.info("Setting up Database healthcheck")
            HealthChecks.register("database",new DatabaseHealthCheck())
            log.info("Setting up Storage healthcheck")
            HealthChecks.register("filestorage",new StorageHealthCheck())

            //run cache job
            //CacheUpdateJob.triggerNow()
        }
    }

    def destroy = {
    }
}
