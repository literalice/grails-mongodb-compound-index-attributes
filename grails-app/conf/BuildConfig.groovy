grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {

    inherits("global") {}

    log "warn"

    repositories {
        //noinspection GroovyAssignabilityCheck
        mavenLocal()
        grailsCentral()
        mavenCentral()
    }

    dependencies {
    }

    plugins {
        build(":tomcat:$grailsVersion",
              ":release:2.0.3",
              ":rest-client-builder:1.0.2") {
            export = false
        }
        compile(":mongodb:1.0.0.GA") {
            export = false
        }
    }

}

