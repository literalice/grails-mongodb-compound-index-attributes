# MongoDB Compound Index Attributes Plugin for Grails #

This plugin is a [MongoDB GORM plugin](http://grails.org/plugin/mongodb)'s add-on that provides a functionality to add attributes to MongoDB's compound index.

See also http://jira.grails.org/browse/GPMONGODB-229

## Install ##

    //Build.groovy
    grails.project.dependency.resolution = {

        // ...
        plugins {
            // ...

            compile ":mongodb:1.0.0.GA"
            compile ":mongodb-compound-index-attributes:1.1"
        }

        // ...
    }

## Usage ##

    // Domain Class
    class Book {

        static mapWith = "mongo"

        static mapping = {
            compoundIndex contributor: 1, name: 1, indexAttributes: [name: "contributorUniqueName", unique: true]
        }

    }

