package com.example.elarning.data.course;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.elarning.data.model.CourseModal;

import java.util.List;

public class CourseRepository {
    private static volatile CourseRepository instance;

    private CourseDataSource dataSource;

    private LiveData<List<CourseModal>> allCourses;

    // private constructor : singleton access
    private CourseRepository(CourseDataSource dataSource) {
        this.dataSource = dataSource;
//        this.dataSource = new CourseDataSource(application);
        allCourses = dataSource.getAllCourses();
    }

//    public CourseRepository() {
//
//    }


    public static synchronized CourseRepository getInstance(CourseDataSource dataSource) {
        if (instance == null) {
            instance = new CourseRepository(dataSource);
        }
        return instance;
    }


    // creating a method to insert the data to our database.
    public void insert(CourseModal model) {
        dataSource.insert(model);
    }

    // creating a method to update data in database.
    public void update(CourseModal model) {
        dataSource.update(model);
    }

    // creating a method to delete the data in our database.
    public void delete(CourseModal model) {
        dataSource.delete(model);
    }

    // below is the method to delete all the courses.
    public void deleteAllCourses() {
        dataSource.deleteAllCourses();
    }

    // below method is to read all the courses.
    public LiveData<List<CourseModal>> getAllCourses() {
        return allCourses;
    }

}
