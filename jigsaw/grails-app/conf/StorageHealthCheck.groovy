import com.codahale.metrics.health.HealthCheck

class StorageHealthCheck extends HealthCheck {
 
	private final File storagePath
    private final long minimumSpace // minimum space, default is .5GB
 
 	public static final String DEFAULT_PATH = "/"
    public static final long DEFAULT_FREE_SPACE = 10e8 / 2 // 1GB
 
    StorageHealthCheck(String storagePath = DEFAULT_PATH,long minimumSpace = DEFAULT_FREE_SPACE) {
    	this.storagePath = new File(new File(storagePath).getAbsolutePath())
        this.minimumSpace = minimumSpace
    }
 
    @Override
    public HealthCheck.Result check() throws Exception {
    	if (storagePath.getFreeSpace() > minimumSpace) {
        	return HealthCheck.Result.healthy(storagePath.getFreeSpace().toString() + "," + storagePath.getTotalSpace().toString())
       	} else {
        	return HealthCheck.Result.unhealthy(storagePath.getFreeSpace().toString() + "," + storagePath.getTotalSpace().toString())
       	}
    }
}