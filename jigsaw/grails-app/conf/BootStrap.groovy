import grails.util.Environment
import java.lang.management.ManagementFactory
import javax.servlet.*

import org.grails.plugins.metrics.groovy.HealthChecks
import org.grails.plugins.metrics.groovy.Metrics

import com.codahale.metrics.jvm.BufferPoolMetricSet
import com.codahale.metrics.jvm.GarbageCollectorMetricSet
import com.codahale.metrics.jvm.MemoryUsageGaugeSet
import com.codahale.metrics.jvm.ThreadStatesGaugeSet
import com.codahale.metrics.jvm.FileDescriptorRatioGauge
import com.codahale.metrics.servlet.InstrumentedFilter

import jigsaw.CacheUpdateJob

class BootStrap {

    def init = { servletContext ->
            
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
        CacheUpdateJob.triggerNow()
    }

    def destroy = {
    }
}
