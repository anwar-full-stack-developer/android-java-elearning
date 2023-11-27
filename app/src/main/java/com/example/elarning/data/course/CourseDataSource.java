package com.example.elarning.data.course;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.elarning.data.model.AppDatabase;
import com.example.elarning.data.model.CourseDao;
import com.example.elarning.data.model.CourseModal;

import java.util.List;

public class CourseDataSource {
    private final CourseDao courseDao;
    private LiveData<List<CourseModal>> allCourses;
    public CourseDataSource(@NonNull Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses();

    }

    // below method is to read all the courses.
    public LiveData<List<CourseModal>> getAllCourses() {
        return allCourses;
    }

    // creating a method to insert the data to our database.
    public void insert(CourseModal model) {
        courseDao.insert(model);
    }

    // creating a method to update data in database.
    public void update(CourseModal model) {
        courseDao.update(model);
    }

    // creating a method to delete the data in our database.
    public void delete(CourseModal model) {
        courseDao.delete(model);
    }

    // below is the method to delete all the courses.
    public void deleteAllCourses() {
        courseDao.deleteAllCourses();
    }

}
