package com.example.elarning.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

//@Database(entities = [CourseModal.class, Trainer.class], version = 1)
@Database(entities = {CourseModal.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // below line is to create instance
    // for our database class.
    private static AppDatabase instance;

    public abstract CourseDao courseDao();


    // on below line we are getting instance for our database.
    public static synchronized AppDatabase getInstance(Context context) {
        // below line is to check if
        // the instance is null or not.
        if (instance == null) {
            // if the instance is null we
            // are creating a new instance
            instance =
                    // for creating a instance for our database
                    // we are creating a database builder and passing
                    // our database class with our database name.
                    Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "course_database")
                            // below line is use to add fall back to
                            // destructive migration to our database.
                            .fallbackToDestructiveMigration()
                            // below line is to add callback
                            // to our database.
                            .addCallback(roomDatabaseCallback)
                            // below line is to
                            // build our database.
                            .build();
        }
        // after creating an instance
        // we are returning our instance
        return instance;
    }

    // below line is to create a callback for our room database.
    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // this method is called when database is created
            // and below line is to populate our data.
//            new PopulateDbAsyncTask(instance).execute();

//            /// additional option
//                String SQL_CREATE_TABLE = "CREATE TABLE categories" + "(" +
//                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                        "cat_name TEXT, " +
//                        "cat_type TEXT)";
//
//                db.execSQL(SQL_CREATE_TABLE);
//                ContentValues contentValues = new ContentValues();
//                contentValues.put("cat_name", "electronics");
//                contentValues.put("cat_type", "commerce");
//                db.insert("categories", OnConflictStrategy.IGNORE, contentValues);
//                Log.d("db create ","table created when db created first time in  onCreate");
            }
            public void onOpen (SupportSQLiteDatabase db){
//                ContentValues contentValues = new ContentValues();
//                contentValues.put("open_time", new Date().getTime());
//                db.insert("dbusage", OnConflictStrategy.IGNORE, contentValues);
//                Log.d("db open ","adding db open date record");
            }
    };

}
