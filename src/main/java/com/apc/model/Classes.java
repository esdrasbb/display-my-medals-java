package com.apc.model;

import com.mongodb.BasicDBObject;

/**
 * Created by esdrasbb on 25/11/15.
 */
public class Classes {

    private String id;
    private String date;

    public Classes(BasicDBObject dbObject) {
        this.id = (dbObject.get("_id")).toString();
        this.date = dbObject.getString("date");
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}
