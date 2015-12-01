package com.apc;


import com.apc.resource.MedalResource;
import com.apc.service.MedalService;
import com.mongodb.*;

import static spark.Spark.setIpAddress;
import static spark.Spark.setPort;
import static spark.SparkBase.staticFileLocation;

/**
 * Created by esdrasbragabarreto on 16/10/15.
 */
public class Main {

    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        setIpAddress(IP_ADDRESS);
        setPort(PORT);
        staticFileLocation("/public");
        new MedalResource(new MedalService(mongo()));
    }

    private static DB mongo() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost");
        return mongoClient.getDB("medalapp");
    }

}
