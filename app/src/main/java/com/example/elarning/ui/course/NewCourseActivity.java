package com.example.elarning.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.elarning.R;
import com.example.elarning.databinding.ActivityCourseBinding;
import com.example.elarning.databinding.ActivityNewCourseBinding;

public class NewCourseActivity extends AppCompatActivity {
    // creating a constant string variable for our
    // course name, description and duration.
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_COURSE_NAME = "EXTRA_COURSE_NAME";
    public static final String EXTRA_DESCRIPTION = "EXTRA_COURSE_DESCRIPTION";
    public static final String EXTRA_DURATION = "EXTRA_COURSE_DURATION";
    public static final String REQUEST_CODE = "REQUEST_CODE";


    // creating a variables for our button and edittext.
    private EditText courseNameEdt, courseDescEdt, courseDurationEdt;
    private Button courseBtn;
    private int actionRequestCode = 0;
    private ActivityNewCourseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_new_course);

        binding = ActivityNewCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initializing our variables for each view.
        courseNameEdt = binding.idEdtCourseName;
        courseDescEdt = binding.idEdtCourseDescription;
        courseDurationEdt = binding.idEdtCourseDuration;
        courseBtn = binding.idBtnSaveCourse;

        Intent intent = getIntent();

        actionRequestCode = intent.getIntExtra(REQUEST_CODE, 0);

        if (intent.hasExtra(EXTRA_ID)) {
            // if we get id for our data then we are
            // setting values to our edit text fields.
            courseNameEdt.setText(intent.getStringExtra(EXTRA_COURSE_NAME));
            courseDescEdt.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            courseDurationEdt.setText(intent.getStringExtra(EXTRA_DURATION));
        }

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        // adding on click listener for our save button.
        courseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text value from edittext and validating if
                // the text fields are empty or not.
                String courseName = courseNameEdt.getText().toString();
                String courseDesc = courseDescEdt.getText().toString();
                String courseDuration = courseDurationEdt.getText().toString();
                if (courseName.isEmpty() || courseDesc.isEmpty() || courseDuration.isEmpty()) {
                    Toast.makeText(NewCourseActivity.this, "Please enter the valid course details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to save our course.
                saveCourse(courseName, courseDesc, courseDuration);
            }
        });
    }

    private void saveCourse(String courseName, String courseDescription, String courseDuration) {
        // inside this method we are passing
        // all the data via an intent.
        Intent data = new Intent();
        // in below line we are passing all our course detail.
        data.putExtra(REQUEST_CODE, actionRequestCode);
        data.putExtra(EXTRA_COURSE_NAME, courseName);
        data.putExtra(EXTRA_DESCRIPTION, courseDescription);
        data.putExtra(EXTRA_DURATION, courseDuration);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            // in below line we are passing our id.
            data.putExtra(EXTRA_ID, id);
        }

        // at last we are setting result as data.
        setResult(RESULT_OK, data);

        finish(); // close the activity
        // displaying a toast message after adding the data
//        Toast.makeText(this, "Course has been saved to Room Database. ", Toast.LENGTH_SHORT).show();

    }
}