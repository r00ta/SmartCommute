package com.r00ta.telematics.platform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.r00ta.telematics.platform.mongo.MongoQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.mongodb.client.model.Filters.eq;


public class MongoStorageManager implements IStorageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoStorageManager.class);

    protected MongoDatabase database;

    @Override
    public <T> boolean create(String key, T request, String index) {
        MongoCollection<T> collection = getOrCreateCollection(index, request.getClass());

        collection.insertOne(request);

        return true;
    }

    @Override
    public <T> List<T> search(SmartQuery query, String index, Class<T> type) {
        MongoCollection<T> collection = getOrCreateCollection(index, type);

        Iterator<T> i = collection.find(MongoQueryFactory.build(query)).iterator();
        List<T> copy = new ArrayList<T>();
        while (i.hasNext()) {
            copy.add(i.next());
        }

        return copy;
    }

    public <T> boolean update(SmartQuery query, T request, String index){
        MongoCollection<T> collection = getOrCreateCollection(index, request.getClass());
        collection.deleteOne(MongoQueryFactory.build(query));
        collection.insertOne(request);
        return true;
    }

    @Override
    public <T> boolean delete(SmartQuery query, String index, Class<T> type) {
        MongoCollection<T> collection = getOrCreateCollection(index, type);
        collection.deleteMany(MongoQueryFactory.build(query));
        return true;
    }

//    @Override
//    public boolean deleteIndex(String index) {
//        MongoCollection collection = database.getCollection(index);
//        collection.drop();
//        return true;
//    }

    private MongoCollection getOrCreateCollection(String collection, Class type) {
        return database.getCollection(collection, type);
    }
}
