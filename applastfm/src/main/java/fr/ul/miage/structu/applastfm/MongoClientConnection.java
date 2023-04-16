package fr.ul.miage.structu.applastfm;

import java.util.ArrayList;
import java.util.Collection;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.InsertOneResult;

public class MongoClientConnection {

    public static MongoDatabase connect() {
        String uri = "mongodb://localhost:27017";
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi).build();
        try {
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("SD2023_SAINT-DIZIER_MANUELLI");
            return database;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
