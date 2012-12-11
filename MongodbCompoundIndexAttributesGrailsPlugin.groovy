@SuppressWarnings("GroovyUnusedDeclaration")
class MongodbCompoundIndexAttributesGrailsPlugin {
    // the plugin version
    def version = "1.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.7 > *"

    // the other plugins this plugin depends on
    def loadAfter = ["mongodb"]

    def title = "MongoDB Compound Index Attributes Plugin" // Headline display name of the plugin
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

    def doWithSpring = {
        final mongoConfig
        if (application.config?.grails?.mongo) {
            mongoConfig = (application.config.grails.mongo as ConfigObject).clone() as ConfigObject
        } else {
            mongoConfig = new ConfigObject()
        }
        mongoDatastore(com.monochromeroad.grails.plugins.mongo.cia.MongoDatastoreFactoryBean) {
            mongo = ref("mongoBean")
            mappingContext = ref("mongoMappingContext")
            config = mongoConfig.toProperties()
        }
    }

}
