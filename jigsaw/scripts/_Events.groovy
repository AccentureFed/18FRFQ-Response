eventCreateWarStart = { warName, stagingDir ->
    def unknownValue = 'UNKNOWN'
    
    def buildNumberEnvironment = 'BUILD_NUMBER'
    def scmRevisionEnvironment = 'GIT_COMMIT'
    def jobNameEnironment = 'JOB_NAME'
    
    def buildNumberProperty = 'build.number'
    def scmRevisionProperty = 'build.revision'
    def jobNameProperty = 'build.jobname'
    
    def buildNumber = System.getenv(buildNumberEnvironment)
    if( !buildNumber ) {
        buildNumber = System.getProperty(buildNumberProperty, unknownValue)
    }
    
    def scmRevision = System.getenv(scmRevisionEnvironment)
    if( !scmRevision ) {
        scmRevision = System.getProperty(scmRevisionProperty, unknownValue)
    }
    
    def jobName = System.getenv(jobNameEnironment)
    if( !jobName ) {
        jobName = System.getProperty(jobNameProperty, unknownValue)
    }
    
    ant.propertyfile(file:"${stagingDir}/WEB-INF/classes/application.properties") {
        entry(key:'app.version.buildNumber', value: buildNumber)
        entry(key:'app.scm.revision', value: scmRevision)
    }
    
    ant.manifest(file: "${stagingDir}/META-INF/MANIFEST.MF", mode: "update") {
        attribute(name: "Build-Time", value: new Date())
        section(name: "Grails Application") {
            attribute(name: "Implementation-Job-Name", value: jobName)
            attribute(name: "Implementation-Build-Number", value: buildNumber)
            attribute(name: "Implementation-SCM-Revision", value: scmRevision)
        }
    }
}