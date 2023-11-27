package com.example.elarning.ui.course;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.example.elarning.data.course.CourseDataSource;
import com.example.elarning.data.course.CourseRepository;


/**
 * ViewModel provider factory to instantiate CourseViewModel.
 * Required given CourseViewModel has a non-empty constructor
 */
public class CourseViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    public CourseViewModelFactory(@NonNull Application application){
        this.application = application;
    }
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CourseViewModel.class)) {
//            CourseRepository cr = CourseRepository.getInstance(new CourseDataSource(application));
//            return  (T) new CourseViewModel(cr);
            return  (T) new CourseViewModel(CourseRepository.getInstance(new CourseDataSource(application)));

        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}