package com.example.elarning.ui.course;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.elarning.data.course.CourseRepository;
import com.example.elarning.data.model.CourseModal;

import java.util.List;

public class CourseViewModel extends ViewModel {


    // creating a new variable for course repository.
    private CourseRepository repository;

    // below line is to create a variable for live
    // data where all the courses are present.
    private LiveData<List<CourseModal>> allCourses;

    public CourseViewModel(CourseRepository courseRepository) {
        this.repository = courseRepository;
//        repository = CourseRepository.getInstance(application);
        this.allCourses = repository.getAllCourses();
    }

    // below method is use to insert the data to our repository.
    public void insert(CourseModal model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(CourseModal model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(CourseModal model) {
        repository.delete(model);
    }

    // below method is to delete all the courses in our list.
    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    // below method is to get all the courses in our list.
    public LiveData<List<CourseModal>> getAllCourses() {
        return allCourses;
    }
}
