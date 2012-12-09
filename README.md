# MongoDB Compound Index Attributes Plugin for Grails #

## Usage ##

    // Domain Class
    class Book {

        static mapWith = "mongo"

        static mapping = {
            compoundIndex contributor: 1, name: 1, indexAttributes: [name: "contributorUniqueName", unique: true]
        }

    }


