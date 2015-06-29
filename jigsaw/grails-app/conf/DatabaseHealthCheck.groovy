import groovy.sql.Sql
import grails.util.Holders
import com.codahale.metrics.health.HealthCheck
import org.springframework.stereotype.Component

@Component
class DatabaseHealthCheck extends HealthCheck {
  	
     DatabaseHealthCheck() {
     }
 
     @Override
     public HealthCheck.Result check() throws Exception {
     	Sql sqlCon = new Sql(Holders.getApplicationContext().getBean('dataSource'))
     	if (sqlCon != null){
        	if (sqlCon.getDataSource()?.getConnection()?.isValid(1000)) {
         		return HealthCheck.Result.healthy()
         	}
         	else
         	{
         		return HealthCheck.Result.unhealthy("Cannot connect to database.")
         	}
       	} else {
        	return HealthCheck.Result.unhealthy("Database connection is null.")
       	}
     }
   }