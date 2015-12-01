package com.apc.model;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esdrasbb on 22/11/15.
 */
public class Student {

    private String id;
    private String name;
    private List<String> participatedClasses = new ArrayList<>();

    public Student(DBObject dbObject) {
        this.id = (dbObject.get("_id")).toString();
        this.name = (dbObject.get("name")).toString();
        BasicDBList participatedClassesList = (BasicDBList) dbObject.get("participatedClasses");
        if (participatedClassesList != null) {
            for (int i = 0; i < participatedClassesList.size(); i++) {
                participatedClasses.add(participatedClassesList.get(i).toString());
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<String> getParticipatedClasses() {
        return participatedClasses;
    }

}
