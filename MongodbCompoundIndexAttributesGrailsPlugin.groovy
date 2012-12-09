class MongodbCompoundIndexAttributesGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "* > 1.3.0"

    // the other plugins this plugin depends on
    def loadAfter = ["mongodb"]

    def title = "Mongodb Compound Index Attributes Plugin" // Headline display name of the plugin
    def author = "Masatoshi Hayashi"
    def authorEmail = "literalice@monochromeroad.com"
    def description = '''\
The plugin provides a functionality to add attributes to MongoDB's compound index.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/mongodb-compound-index-attributes"

    def license = "APACHE"

    // Online location of the plugin's browseable source code.
    def scm = [url: "https://github.com/literalice/grails-mongodb-compound-index-attributes"]


    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
