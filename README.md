# MongoDB Compound Index Attributes Plugin for Grails #

## Install ##

    //Build.groovy
    grails.project.dependency.resolution = {
        // ...

        repositories {
            // ...

            // For the plugin
            mavenRepo "http://repository-monochromeroad.forge.cloudbees.com/release"
        }

        // ...
        plugins {
            // ...

            compile ":mongodb:1.0.0.GA"
            compile ":mongodb-compound-index-attributes:1.1"
        }
    }

## Usage ##

    // Domain Class
    class Book {

        static mapWith = "mongo"

        static mapping = {
            compoundIndex contributor: 1, name: 1, indexAttributes: [name: "contributorUniqueName", unique: true]
        }

    }


