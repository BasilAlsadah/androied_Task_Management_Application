package com.example.taskmanagementapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //creating users table by ahmed
    private static final String DATABASE_NAME = "Mydata.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_NAME = "users_table";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //enable foreign key constraints
        db.execSQL("PRAGMA foreign_keys=ON;");
        //create users table
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTableQuery);
        //create projects table
        createProjectsTable(db);
        //create members tale
        createMembersTable(db);
        //create tasks table
        createTasksTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        db.execSQL("DROP TABLE IF EXISTS projects");
        db.execSQL("DROP TABLE IF EXISTS Members");
        db.execSQL("DROP TABLE IF EXISTS Tasks");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        db.execSQL("DROP TABLE IF EXISTS projects");
        db.execSQL("DROP TABLE IF EXISTS Members");
        db.execSQL("DROP TABLE IF EXISTS Tasks");
        onCreate(db);
    }

    //---------------to enable foreign key
    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
        super.onOpen(db);
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
        if (cursor.getCount() > 0) {
            isValid = true;
        } else {
            isValid = false;
        }
        cursor.close();
        return isValid;
    }

    //---------------Check if table is exist
    public boolean isTableExists(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    //---------------Create Project Table
    //here will be project table methods by basil
    public void createProjectsTable(SQLiteDatabase db) {
        String create_project_table_sql = "CREATE TABLE IF NOT EXISTS projects (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "description TEXT," +
                " admin TEXT," +
                "FOREIGN KEY (admin) REFERENCES users_table(username))";
        db.execSQL(create_project_table_sql);
    }

    //---------------insert new project
    // a method that will be used to insert a project
    public boolean insertProject(String project_name, String project_description, String admin) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", project_name);
        contentValues.put("description", project_description);
        contentValues.put("admin", admin);
        long result = db.insert("projects", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    //---------------Retrieve all projects for a user *BROKEN*

    //Another version
    public List<Pair<Integer, Pair<String, String>>> getAllProjects(String current_username) {
        List<Pair<Integer, Pair<String, String>>> projectsList = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor myCursor = db.rawQuery("SELECT projects.* FROM projects JOIN Members ON "
                + "projects.ID=Members.project_id "
                + "WHERE Members.username= ?", new String[]{current_username});
        if (myCursor.moveToFirst()) {
            do {
                int projectId = myCursor.getInt(myCursor.getColumnIndexOrThrow("ID"));
                String projectName = myCursor.getString(myCursor.getColumnIndexOrThrow("name"));
                String projectDesc = myCursor.getString(myCursor.getColumnIndexOrThrow("description"));
                Pair<String, String> project = new Pair<>(projectName, projectDesc);
                projectsList.add(new Pair<>(projectId, project));
            } while (myCursor.moveToNext());
        }
        myCursor.close();
        return projectsList;
    }

    //---------------Retrieve project description
    public String getDescribtion(String proj_name) {
        db = this.getReadableDatabase();
        String descriptionQuery = "SELECT description FROM projects WHERE name = ?";

        // Execute the query and retrieve the description value
        Cursor cursor = db.rawQuery(descriptionQuery, new String[]{proj_name});

        String description = "";
        if (cursor.moveToFirst()) {
            description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        }

        cursor.close();
        return description;
    }

    //a method that we use to get the id of the last project that was inserted
    public int getLastProject_Id() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(ID) FROM projects";
        Cursor cursor = db.rawQuery(query, null);
        int lastId = -1;
        if (cursor.moveToFirst()) {
            lastId = cursor.getInt(0);
        }
        cursor.close();
        return lastId;
    }

    //---------------Create Tasks Table
    //here will be Tasks table methods by basil
    public void createTasksTable(SQLiteDatabase db) {
        String create_tasks_table_sql = "CREATE TABLE Tasks (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "project_id INTEGER, " +
                "username TEXT," +
                "title TEXT," +
                "due_date TEXT," +
                "priority TEXT," +
                "status TEXT ,"+
                "FOREIGN KEY (project_id) REFERENCES projects(ID)," +
                "FOREIGN KEY (username) REFERENCES users_table(username))";
        db.execSQL(create_tasks_table_sql);
    }
    //---------------insert a task
    public boolean insertTask(int project_id, String username,String title,String due_date,
                              String priority,String status){
        SQLiteDatabase db = this.getWritableDatabase();
        // Check if the username and project_id exist in the members table
        Cursor cursor = db.rawQuery("SELECT * FROM Members WHERE username = ? AND project_id = ?",
                new String[]{username, String.valueOf(project_id)});
        boolean usernameIs_member = cursor.getCount() > 0;
        cursor.close();

        if (usernameIs_member) {
            ContentValues values = new ContentValues();
            values.put("project_id", project_id);
            values.put("username", username);
            values.put("title", title);
            values.put("due_date", due_date);
            values.put("priority", priority);
            values.put("status", status);

            long result = db.insert("tasks", null, values);
            return result != -1;
        }
        else {
            return false;
        }
    }
    //---------------retrieve task
    public ArrayList<Task> project_tasks(int project_id){
        ArrayList<Task> tasks_list = new ArrayList<>();
        db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID,username,title,due_date,priority,status FROM Tasks " +
                "WHERE project_id=?",new String[]{String.valueOf(project_id)});
        if (cursor.moveToFirst()){
            do {
                String get_task_id = cursor.getString(cursor.getColumnIndexOrThrow("ID"));
                String get_project_id=String.valueOf(project_id);
                String get_task_title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String get_task_assignTo = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String get_priority = cursor.getString(cursor.getColumnIndexOrThrow("priority"));
                String get_task_dueDate = cursor.getString(cursor.getColumnIndexOrThrow("due_date"));
                String get_task_status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                // Create a new Task object and add it to the list
                Task task = new Task(get_task_id,get_project_id, get_task_title, get_task_assignTo, get_task_dueDate,get_priority, get_task_status);
                tasks_list.add(task);
            } while (cursor.moveToNext());
        }
        return  tasks_list;
    }
    //---------------Update task
    public boolean updateTask(int taskId, String newTitle,String newUsername, String newDueDate, String newPriority, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", newTitle);
        values.put("username", newUsername);
        values.put("due_date", newDueDate);
        values.put("priority", newPriority);
        values.put("status", newStatus);

        int rowsAffected = db.update("Tasks", values, "ID=?", new String[]{String.valueOf(taskId)});
        db.close();

        return rowsAffected > 0;
    }
    //---------------retrieve all user task
    public ArrayList<Task> get_userTasks(String current_user){
        ArrayList<Task> tasks_list = new ArrayList<>();
        db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID,project_id,username,title,due_date,priority,status FROM Tasks " +
                "WHERE username=?",new String[]{String.valueOf(current_user)});
        if (cursor.moveToFirst()){
            do {
                String get_task_id = cursor.getString(cursor.getColumnIndexOrThrow("ID"));
                String get_project_id=cursor.getString(cursor.getColumnIndexOrThrow("project_id"));
                String get_task_title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String get_task_assignTo = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String get_priority = cursor.getString(cursor.getColumnIndexOrThrow("priority"));
                String get_task_dueDate = cursor.getString(cursor.getColumnIndexOrThrow("due_date"));
                String get_task_status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                // Create a new Task object and add it to the list
                Task task = new Task(get_task_id,get_project_id, get_task_title, get_task_assignTo, get_task_dueDate,get_priority, get_task_status);
                tasks_list.add(task);
            } while (cursor.moveToNext());
        }
        return  tasks_list;
    }
    //---------------Task sorting method
    public ArrayList<Task> sort_userTasks(String current_user, String sortingOrder) {
        ArrayList<Task> tasks_list = new ArrayList<>();
        db = this.getReadableDatabase();

        String query = "SELECT ID,project_id, username, title, due_date, priority, status FROM Tasks " +
                "WHERE username=? ORDER BY due_date " + sortingOrder;

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(current_user)});
        if (cursor.moveToFirst()) {
            do {
                // Retrieve task details from the cursor
                String get_task_id = cursor.getString(cursor.getColumnIndexOrThrow("ID"));
                String get_project_id=cursor.getString(cursor.getColumnIndexOrThrow("project_id"));
                String get_task_title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String get_task_assignTo = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String get_priority = cursor.getString(cursor.getColumnIndexOrThrow("priority"));
                String get_task_dueDate = cursor.getString(cursor.getColumnIndexOrThrow("due_date"));
                String get_task_status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

                // Create a new Task object and add it to the list
                Task task = new Task(get_task_id,get_project_id, get_task_title, get_task_assignTo, get_task_dueDate, get_priority, get_task_status);
                tasks_list.add(task);
            } while (cursor.moveToNext());
        }

        return tasks_list;
    }
    //---------------Getting project name by it's id
    public String getProjectName(String proj_id){
        String proj_name = new String();
        db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM projects WHERE ID=?",
                new String[]{String.valueOf(proj_id)});
        if (cursor.moveToFirst()){
            do{
                proj_name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            }while(cursor.moveToNext());
        }
        return proj_name;
    }

    //---------------Create project_members Table
    //here will be project_members table methods by basil
    public void createMembersTable(SQLiteDatabase db) {
        String create_members_table_sql = "CREATE TABLE IF NOT EXISTS Members (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "project_id INTEGER, " +
                "is_admin TEXT," +
                "username TEXT," +
                "FOREIGN KEY (project_id) REFERENCES projects(ID)," +
                "FOREIGN KEY (username) REFERENCES users_table(username))";
        db.execSQL(create_members_table_sql);
    }

    //---------------Add new member to a project
    public boolean insertMember(String username, int project_id, String is_admin) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("project_id", project_id);
        contentValues.put("username", username);
        contentValues.put("is_admin", is_admin);
        long result = db.insert("Members", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //---------------a method that will return an array containing all members in particular project
    public ArrayList<String> member_array(int project_id) {
        ArrayList<String> membersList = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM Members WHERE project_id=?", new String[]{String.valueOf(project_id)});
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                membersList.add(username);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return membersList;
    }

    //---------------a method that will remove a member from a project
    public boolean removeMember(int project_id, String username) {
        db = this.getWritableDatabase();
        try {
            // Execute a SELECT query to check if is_admin is "yes"
            Cursor cursor = db.rawQuery("SELECT is_admin FROM Members WHERE project_id=? AND username=?", new String[]{String.valueOf(project_id), String.valueOf(username)});
            if (cursor.moveToFirst()) {
                String is_admin = cursor.getString(cursor.getColumnIndexOrThrow("is_admin"));
                // If the member is the admin of the project then you can not remove him
                if (is_admin.equals("yes")) {
                    return false;
                }
            }
            // If is_admin is not "yes", execute the DELETE statement
            db.execSQL("DELETE FROM Members WHERE project_id=? AND username=?", new String[]{String.valueOf(project_id), String.valueOf(username)});
            return true;
        } catch (SQLException e) {
            Log.e("removeMember", "Failed to delete member", e);
            return false;
        }
    }
}
