package com.latex.generator.backend;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;


public class Database {
    public static MongoDatabase db = null;

    public static void connect() {
        int port_no = 27017;
        String host_name = "67.207.85.11", db_name = "ProjectDB";
        String client_url = "mongodb://" + host_name + ":" + port_no + "/" + db_name;
        MongoClientURI uri = new MongoClientURI(client_url);

        MongoClient mongo_client = new MongoClient(uri);
        db = mongo_client.getDatabase(db_name);
        while (db==null);
    }

}
