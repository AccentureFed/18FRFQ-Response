import jigsaw.CacheUpdateJob


class BootStrap {

    def init = { servletContext ->
        CacheUpdateJob.triggerNow()
    }

    def destroy = {
    }
}
