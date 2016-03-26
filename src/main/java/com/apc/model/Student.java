package com.apc.model;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by esdrasbb on 22/11/15.
 */
public class Student implements Comparable<Student>{

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (!id.equals(student.id)) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        return !(participatedClasses != null ? !participatedClasses.equals(student.participatedClasses) : student.participatedClasses != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (participatedClasses != null ? participatedClasses.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Student s1) {
        if (CollectionUtils.isNotEmpty(s1.getParticipatedClasses()) && CollectionUtils.isNotEmpty(this.getParticipatedClasses())){
            return Integer.compare(s1.getParticipatedClasses().size(), this.participatedClasses.size());
        }else {
            return this.getName().compareTo(s1.getName());
        }
    }

}
