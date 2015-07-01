package com.afs

class ApplicationMetadata {

    String metadataKey
    String metadataValue = ''

    static constraints = {
        metadataKey unique: true, blank: false, nullable: false
        metadataValue blank: false, nullable: false
    }
}
