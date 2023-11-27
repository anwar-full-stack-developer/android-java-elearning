package com.example.elarning.ui.course;

import android.content.Context;
import android.os.Bundle;

import android.app.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.elarning.MainActivity;
import com.example.elarning.R;
import com.example.elarning.data.model.CourseModal;
import com.example.elarning.databinding.ActivityCourseBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class CourseActivity extends AppCompatActivity {
    // creating a variables for our recycler view.
    private RecyclerView coursesRV;
    private static final int ADD_COURSE_REQUEST = 1;
    private static final int EDIT_COURSE_REQUEST = 2;
    private static final String REQUEST_CODE = "REQUEST_CODE";
    private CourseViewModel courseViewModel;
    private ActivityCourseBinding binding;

    private ActivityResultLauncher<Intent> launcherNewCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_course);

        binding = ActivityCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);
//        getApplication();


        courseViewModel = new ViewModelProvider(this, new CourseViewModelFactory(getApplication()))
                .get(CourseViewModel.class);

//        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        final Button btnNewCourse = binding.btnNewCourse;
        btnNewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CourseActivity", "Course Button Clicked");
                Toast.makeText(getApplicationContext(), "Course button Clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), NewCourseActivity.class);
                //pass parameter
                intent.putExtra("dispatchFrom", "CourseActivity_to_NewCourseActivity");
                intent.putExtra(REQUEST_CODE, ADD_COURSE_REQUEST);
                launcherNewCourse.launch(intent);
//                v.getContext().startActivity(intent);
            }
        });


        coursesRV = binding.idRVCourses;

// setting layout manager to our adapter class.
        coursesRV.setLayoutManager(new LinearLayoutManager(this));
        coursesRV.setHasFixedSize(true);

//        // initializing adapter for recycler view.
        final CourseRVAdapter adapter = new CourseRVAdapter();

//        // setting adapter class for recycler view.
        coursesRV.setAdapter(adapter);


        // below line is use to get all the courses from view modal.
        courseViewModel.getAllCourses().observe(this, new Observer<List<CourseModal>>() {
            @Override
            public void onChanged(List<CourseModal> models) {
                // when the data is changed in our models we are
                // adding that list to our adapter class.
                adapter.submitList(models);
            }
        });

        // below method is use to add swipe to delete method for item of recycler view.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // on recycler view item swiped then we are deleting the item of our recycler view.
                //avoid main thread, execute task in background task on a separate thread
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        courseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                    }
                });
                Toast.makeText(CourseActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).
                // below line is use to attach this to recycler view.
                        attachToRecyclerView(coursesRV);

        // below line is use to set item click listener for our item of recycler view.
        adapter.setOnItemClickListener(new CourseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CourseModal model) {
                // after clicking on item of recycler view
                // we are opening a new activity and passing
                // a data to our activity.
                Intent intent = new Intent(CourseActivity.this, NewCourseActivity.class);
                intent.putExtra(NewCourseActivity.EXTRA_ID, model.getId());
                intent.putExtra(NewCourseActivity.EXTRA_COURSE_NAME, model.getCourseName());
                intent.putExtra(NewCourseActivity.EXTRA_DESCRIPTION, model.getCourseDescription());
                intent.putExtra(NewCourseActivity.EXTRA_DURATION, model.getCourseDuration());

                // below line is to start a new activity and
                // adding a edit course constant.
                //pass parameter
                intent.putExtra("dispatchFrom", "CourseActivity_to_NewCourseActivity");
                intent.putExtra(REQUEST_CODE, EDIT_COURSE_REQUEST);
                launcherNewCourse.launch(intent);
            }
        });


        launcherNewCourse = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    Log.d("CourseActivity", "New course call back");
                    Log.d("CourseActivity", "RequestCode: "
                            + data.getIntExtra(REQUEST_CODE, 0)
                            + ", ResultCode: "
                                    + result.getResultCode()

                            );
                    if (result.getResultCode() == RESULT_OK) {
//                        Intent data = result.getData();
                        int requestCode = data.getIntExtra(REQUEST_CODE, 0);
                        onActivityResultCB(requestCode, result.getResultCode(), data);

//                        tvResult.setText(data.getStringExtra("result"));
                    }
                }
        );
    }


//    @Override
    protected void onActivityResultCB(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        Log.d("CourseActivity", "RequestCode: "
                + data.getIntExtra(REQUEST_CODE, 0)
                + ", ResultCode: "
                + resultCode

        );
        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {
            Log.d("CourseActivity", "Adding New Course");
            String courseName = data.getStringExtra(NewCourseActivity.EXTRA_COURSE_NAME);
            String courseDescription = data.getStringExtra(NewCourseActivity.EXTRA_DESCRIPTION);
            String courseDuration = data.getStringExtra(NewCourseActivity.EXTRA_DURATION);
            CourseModal model = new CourseModal(courseName, courseDescription, courseDuration);
            //avoid main thread, execute task in background task on a separate thread
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    courseViewModel.insert(model);
                }
            });

            Toast.makeText(this, "Course saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            Log.d("CourseActivity", "Update New Course");
            int id = data.getIntExtra(NewCourseActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String courseName = data.getStringExtra(NewCourseActivity.EXTRA_COURSE_NAME);
            String courseDesc = data.getStringExtra(NewCourseActivity.EXTRA_DESCRIPTION);
            String courseDuration = data.getStringExtra(NewCourseActivity.EXTRA_DURATION);
            CourseModal model = new CourseModal(courseName, courseDesc, courseDuration);
            model.setId(id);

            //avoid main thread, execute task in background task on a separate thread
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    courseViewModel.update(model);
                }
            });

            Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("CourseActivity", "No action");
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show();
        }
    }
}