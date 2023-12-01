package com.example.taskmanagementapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //creating users table by ahmed
    private static final String DATABASE_NAME = "Mydata.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "users_table";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create users table
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTableQuery);
        //create projects table
        createProjectsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        db.execSQL("DROP TABLE IF EXISTS projects");
        onCreate(db);
    }
    //---------------Check for duplicate username
    public boolean usernameExists(String username) {
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    //---------------Insert new User
    public void insertUser(String username, String password) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_NAME, null, values);
        //db.close();
    }
    //---------------Check if user information is correct
    public boolean checkCredentials(String username, String password) {
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean isValid;
        if(cursor.getCount() > 0){
            isValid=true;
        }
        else{
            isValid=false;
        }
        cursor.close();
        return isValid;
    }
    //---------------Create Project Table
    //here will be project table methods by basil (not complete)
    private void createProjectsTable(SQLiteDatabase db){
        String create_project_table_sql = "CREATE TABLE project (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "description TEXT," +
                " admin TEXT)";
        db.execSQL(create_project_table_sql);
    }
    //---------------insert new project
    // a method that will be used to inset a project
    public boolean insertProject(String project_name,String project_description,String admin){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",project_name);
        contentValues.put("description",project_description);
        contentValues.put("admin",admin);
        long result=db.insert("projects",null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    //---------------Retrieve all projects for a user
    /*
    public ArrayList<String> getAll_projects(){
        ArrayList<String> projects_list=new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor myCursor=db.rawQuery("SELECT * FROM projects",null);
        if (myCursor.moveToFirst()) {
            do {
                String projectName = myCursor.getString(myCursor.getColumnIndexOrThrow("name"));
                String projectDesc = myCursor.getString(myCursor.getColumnIndexOrThrow("description"));
                projects_list.add(projectName);
                projects_list.add(projectDesc);
            } while (myCursor.moveToNext());
        }
        myCursor.close();
        return projects_list;
    }

     */
    //new VERSION
    public List<Pair<String, String>> getAllProjects() {
        List<Pair<String, String>> projectsList = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor myCursor = db.rawQuery("SELECT * FROM projects", null);
        if (myCursor.moveToFirst()) {
            do {
                String projectName = myCursor.getString(myCursor.getColumnIndexOrThrow("name"));
                String projectDesc = myCursor.getString(myCursor.getColumnIndexOrThrow("description"));
                Pair<String, String> project = new Pair<>(projectName, projectDesc);
                projectsList.add(project);
            } while (myCursor.moveToNext());
        }
        myCursor.close();
        return projectsList;
    }
    //---------------Retrieve project description
    public String getDescribtion(String proj_name){
        db=this.getReadableDatabase();
        String descriptionQuery = "SELECT description FROM projects WHERE name = ?";

        // Execute the query and retrieve the description value
        Cursor cursor = db.rawQuery(descriptionQuery, new String[]{proj_name});

        String description="";
        if (cursor.moveToFirst()) {
            description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        }

        cursor.close();
        return description;
    }

}
