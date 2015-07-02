package jigsaw

import groovy.time.TimeCategory

import java.text.SimpleDateFormat

import org.slf4j.LoggerFactory

import com.afs.ApplicationMetadata

/**
 * Job to update our local cache from the API.
 */
class CacheUpdateJob {

    static triggers = {
        // runs at 4am everyday
        cron name: 'cacheUpdater', cronExpression: "0 0 4 * * ?"
    }
    def group = 'Jigsaw'
    def description = 'Updates the Jigsaw cache with new data.'

    private static final def log = LoggerFactory.getLogger(CacheUpdateJob.class)
    private static final def LAST_UPDATED_METADATA_KEY = 'lastUpdated'
    private static final def LAST_UPDATE_FORMAT = 'yyyy-MM-dd'

    def foodRecallService

    /**
     * Run at cron trigger interval listed above for the class
     */
    def execute() {
        log.info('Checking for cache update...')
        try {
            if(updateNeeded()) {
                log.info('Cache update is needed, starting to update')
                foodRecallService.updateLocalCache()

                // get the current or a new one
                def metadata = ApplicationMetadata.findByMetadataKey(LAST_UPDATED_METADATA_KEY) ?: new ApplicationMetadata()
                metadata.metadataKey = LAST_UPDATED_METADATA_KEY
                metadata.metadataValue = new SimpleDateFormat(LAST_UPDATE_FORMAT).format(new Date())
                if(!metadata.save()) {
                    log.error("Error saving the last updated metadata: ${metadata.errors}")
                }
            }
        } catch(all) {
            log.error('An error occurred while updating the cache, will try again in 5 minnutes...', all)

            def currentDate = new Date()
            def scheduledDate
            use(TimeCategory) {
                scheduledDate = currentDate + 5.minutes
            }
            CacheUpdateJob.schedule(scheduledDate)
        }
        log.info('Cache update finished')
    }

    /**
     * Determines if the API data has been updated since our last check
     *
     * @return true if we need to send new notifications
     */
    def updateNeeded() {
        def formatter = new SimpleDateFormat(LAST_UPDATE_FORMAT)

        def lastAPIUpdateDate = formatter.parse(foodRecallService.fetchLastUpdatedDate())
        def metadata = ApplicationMetadata.findByMetadataKey(LAST_UPDATED_METADATA_KEY)

        // we need to update if we never updated before OR if the last API update is newer than our last update
        return metadata == null || lastAPIUpdateDate > formatter.parse(metadata.metadataValue)
    }
}
