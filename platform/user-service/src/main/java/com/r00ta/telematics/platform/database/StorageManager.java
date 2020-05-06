package com.r00ta.telematics.platform.database;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.mongodb.client.MongoClient;
import com.r00ta.telematics.platform.ElasticSearchStorageManager;
import com.r00ta.telematics.platform.MongoStorageManager;

@ApplicationScoped
public class StorageManager extends MongoStorageManager {

    @Inject
    MongoClient defaultMongoClient;

    @PostConstruct
    protected void setup() {
        this.database = defaultMongoClient.getDatabase("myMongoDb");
    }
}

