package com.latex.generator.backend;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
public class Database {
    public static MongoDatabase db = null;

    public static void connect() {
        int portNumber = 27017;
        String hostName = "localhost", databaseName = "ProjectDB";
        String client_url = "mongodb://" + hostName + ":" + portNumber + "/" + databaseName;
        MongoClientURI uri = new MongoClientURI(client_url);
        MongoClient mongo_client = new MongoClient(uri);
        db = mongo_client.getDatabase(databaseName);
        while (db==null);
    }

}
