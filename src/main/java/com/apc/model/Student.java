package com.apc.model;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by esdrasbb on 22/11/15.
 */
public class Student {

    private String id;
    private String name;
    private List<String> participatedClasses = new ArrayList<>();

    public Student(DBObject dbObject, List<Classes> classes) {
        this.id = (dbObject.get("_id")).toString();
        this.name = (dbObject.get("name")).toString();
        BasicDBList participatedClassesList = (BasicDBList) dbObject.get("participatedClasses");
        if (participatedClassesList != null && CollectionUtils.isNotEmpty(classes)) {
            for (int i = 0; i < participatedClassesList.size(); i++) {
                final String id = participatedClassesList.get(i).toString();
                Optional<Classes> classesLocal = classes.stream().filter(c -> c.getId().equals(id)).findFirst();
                participatedClasses.add(classesLocal.isPresent() ? classesLocal.get().getDate() : null);
            }
            if (CollectionUtils.isNotEmpty(participatedClasses)) {
                Collections.sort(participatedClasses);
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
