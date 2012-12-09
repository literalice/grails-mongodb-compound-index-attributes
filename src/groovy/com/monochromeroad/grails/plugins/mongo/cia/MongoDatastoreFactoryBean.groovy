package com.monochromeroad.grails.plugins.mongo.cia

import org.grails.datastore.gorm.events.AutoTimestampEventListener
import org.grails.datastore.gorm.events.DomainEventListener
import org.grails.datastore.mapping.mongo.config.MongoMappingContext

import org.springframework.context.ConfigurableApplicationContext

/**
 * @author Masatoshi Hayashi
 */
class MongoDatastoreFactoryBean extends org.grails.datastore.gorm.mongo.bean.factory.MongoDatastoreFactoryBean {

    MongoDatastore getObject() {
        final datastore
        final mappingContext = mappingContext as MongoMappingContext
        final applicationContext = applicationContext as ConfigurableApplicationContext
        if (mongo) {
            datastore = new MongoDatastore(mappingContext, mongo, config, applicationContext)
        } else {
            datastore = new MongoDatastore(mappingContext, config, applicationContext)
        }

        applicationContext.addApplicationListener new DomainEventListener(datastore)
        applicationContext.addApplicationListener new AutoTimestampEventListener(datastore)

        datastore.afterPropertiesSet()
        datastore
    }

}
