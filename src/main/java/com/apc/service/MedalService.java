package com.apc.service;

import com.apc.model.Classes;
import com.apc.model.Medal;
import com.apc.model.Student;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Created by esdrasbb on 22/11/15.
 */
public class MedalService {

    private final DB db;

    public MedalService(DB db) throws Exception {
        this.db = db;
    }

    public List<Medal> findMedals() {
        List<Medal> medals = new ArrayList<>();
        List<Student> students = this.findAllStudents();
        students.forEach(s -> medals.add(new Medal(s.getName(), s.getParticipatedClasses().size())));
        Collections.sort(medals);
        return medals;
    }

    public List<Student> findAllStudents() {
        List<Student> students = new ArrayList<>();
        DBCursor dbObjects = db.getCollection("students").find();
        while (dbObjects.hasNext()) {
            students.add(new Student(dbObjects.next()));
        }
        return students;
    }

    public List<Classes> findAllClasses() {
        List<Classes> classes = new ArrayList<>();
        DBCursor dbObjects = db.getCollection("classes").find();
        while (dbObjects.hasNext()) {
            classes.add(new Classes((BasicDBObject) dbObjects.next()));
        }
        return classes;
    }

    public Student findStudent(String id) {
        return new Student(db.getCollection("students").findOne(new BasicDBObject("_id", new ObjectId(id))));
    }

    public Classes findClass(String id) {
        return new Classes((BasicDBObject) db.getCollection("classes").findOne(new BasicDBObject("_id", new ObjectId(id))));
    }

    public void createNewStudent(String body) {
        Student student = new Gson().fromJson(body, Student.class);
        db.getCollection("students").insert(new BasicDBObject("name", student.getName()).append("createdOn", new Date()));
    }

    public void createNewClass(String body) {
        Classes classes = new Gson().fromJson(body, Classes.class);
        db.getCollection("classes").insert(new BasicDBObject("date", classes.getDate()));
    }

    public Student addClassToStudent(String studentId, String classId) {
        BasicDBObject cmd = new BasicDBObject().append("$push", new BasicDBObject("participatedClasses", classId));
        db.getCollection("students").update(new BasicDBObject("_id", new ObjectId(studentId)), cmd);
        return this.findStudent(studentId);
    }

    public Student updateClass(String studentId, String body) {
        Student medal = new Gson().fromJson(body, Student.class);
        //TODO ajustar a collection de data
        //collection.update(new BasicDBObject("_id", new ObjectId(medalId)), new BasicDBObject("$set", new BasicDBObject("done", medal.isDone())));
        return this.findStudent(studentId);
    }

}
