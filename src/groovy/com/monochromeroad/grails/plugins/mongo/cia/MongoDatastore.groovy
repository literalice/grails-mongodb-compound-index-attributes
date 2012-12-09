package com.monochromeroad.grails.plugins.mongo.cia

import com.mongodb.Mongo
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.dao.DataAccessException
import org.grails.datastore.mapping.model.ClassMapping
import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.datastore.mapping.model.PersistentProperty
import org.grails.datastore.mapping.model.PropertyMapping
import org.grails.datastore.mapping.mongo.config.MongoAttribute
import org.grails.datastore.mapping.mongo.config.MongoCollection
import org.grails.datastore.mapping.mongo.config.MongoMappingContext

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBObject
import com.mongodb.MongoException
import org.springframework.data.mongodb.core.DbCallback
import org.springframework.data.mongodb.core.MongoTemplate

/**
 * @author Masatoshi Hayashi
 */
class MongoDatastore extends org.grails.datastore.mapping.mongo.MongoDatastore {

    public MongoDatastore(MongoMappingContext mappingContext, Mongo mongo,
                                    Map<String, String> connectionDetails, ConfigurableApplicationContext ctx) {
        super(mappingContext, mongo, connectionDetails, ctx);
    }

    /**
     * Constructs a MongoDatastore using the given MappingContext and connection details map.
     *
     * @param mappingContext The MongoMappingContext
     * @param connectionDetails The connection details containing the {@link #MONGO_HOST} and {@link #MONGO_PORT} settings
     */
    public MongoDatastore(MongoMappingContext mappingContext, Map<String, String> connectionDetails, ConfigurableApplicationContext ctx) {
        super(mappingContext, connectionDetails, ctx)
    }

    /**
     * Indexes any properties that are mapped with index:true
     * @param entity The entity
     * @param template The template
     */
    protected void initializeIndices(final PersistentEntity entity, final MongoTemplate template) {
        template.execute(new DbCallback<Object>() {
            @SuppressWarnings("GroovyUncheckedAssignmentOfMemberOfRawType")
            public Object doInDB(DB db) throws MongoException, DataAccessException {
                final DBCollection collection = db.getCollection(getCollectionName(entity));

                final ClassMapping<MongoCollection> classMapping = entity.getMapping();
                if (classMapping != null) {
                    final MongoCollection mappedForm = classMapping.getMappedForm();
                    if (mappedForm != null) {
                        for (Map compoundIndex : mappedForm.getCompoundIndices()) {
                            if (compoundIndex.indexAttributes) {
                                DBObject options = new BasicDBObject(compoundIndex.indexAttributes as Map)
                                compoundIndex.indexAttributes = null
                                DBObject indexDef = new BasicDBObject(compoundIndex);
                                collection.ensureIndex(indexDef, options);
                            } else {
                                DBObject indexDef = new BasicDBObject(compoundIndex);
                                collection.ensureIndex(indexDef);
                            }
                        }
                    }
                }

                for (PersistentProperty<MongoAttribute> property : entity.getPersistentProperties()) {
                    final boolean indexed = isIndexed(property);

                    if (indexed) {
                        final MongoAttribute mongoAttributeMapping = property.getMapping().getMappedForm();
                        DBObject dbObject = new BasicDBObject();
                        final String fieldName = getMongoFieldNameForProperty(property);
                        dbObject.put(fieldName,1);
                        DBObject options = new BasicDBObject();
                        if (mongoAttributeMapping != null) {
                            Map attributes = mongoAttributeMapping.getIndexAttributes();
                            if (attributes != null){
                                attributes = new HashMap(attributes);
                                if (attributes.containsKey(MongoAttribute.INDEX_TYPE)) {
                                    dbObject.put(fieldName, attributes.remove(MongoAttribute.INDEX_TYPE));
                                }
                                options.putAll(attributes);
                            }
                        }
                        if (options.toMap().isEmpty()) {
                            collection.ensureIndex(dbObject);
                        }
                        else {
                            collection.ensureIndex(dbObject, options);
                        }
                    }
                }

                return null;
            }

            String getMongoFieldNameForProperty(PersistentProperty<MongoAttribute> property) {
                PropertyMapping<MongoAttribute> pm = property.getMapping();
                String propKey = null;
                if (pm.getMappedForm() != null) {
                    propKey = pm.getMappedForm().getField();
                }
                if (propKey == null) {
                    propKey = property.getName();
                }
                return propKey;
            }
        });
    }
}
