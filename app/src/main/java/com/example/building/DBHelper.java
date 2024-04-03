package com.example.building;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";
    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }
    private static final String TABLE_BLOCKS = "Blocks";
    private static final String COLUMN_ID = "blockID";
    private static final String COLUMN_BLOCK = "block";
    private static final String COLUMN_BLOCK_NAME = "blockName";
    private static final String COLUMN_PARKING_AVAILABILITY = "parkingAvailability";
    private static final String COLUMN_FURNISHED = "furnished";

    // Create table query
    private static final String CREATE_TABLE_BLOCKS = "CREATE TABLE IF NOT EXISTS " + TABLE_BLOCKS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BLOCK + " TEXT, " +
            COLUMN_BLOCK_NAME + " TEXT, " +
            COLUMN_PARKING_AVAILABILITY + " INTEGER, " +
            COLUMN_FURNISHED + " INTEGER);";


    private static final String TABLE_STAFF = "Staff";
    private static final String STAFF_COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PROFESSION = "profession";
    private static final String COLUMN_STAFF_BLOCK = "blockID";
    private static final String COLUMN_PHONE_NUMBER = "phoneNumber";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_JOINING_DATE = "joiningDate";

    // SQL query to create Staff table
    private static final String CREATE_STAFF_TABLE = "CREATE TABLE " + TABLE_STAFF + " (" +
            STAFF_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT," +
            COLUMN_PROFESSION + " TEXT," +
            COLUMN_STAFF_BLOCK + " INTEGER," +
            COLUMN_PHONE_NUMBER + " TEXT," +
            COLUMN_USERNAME + " TEXT," +
            COLUMN_JOINING_DATE + " DATE,"+
            "FOREIGN KEY(" + COLUMN_STAFF_BLOCK + ") REFERENCES Blocks(" + COLUMN_ID + "))";

    // Managers table
    private static final String TABLE_MANAGERS = "Managers";
    private static final String COLUMN_MANAGER_ID = "managerID";
    private static final String COLUMN_MANAGER_NAME = "name";
    private static final String COLUMN_DESIGNATION = "designation";
    private static final String COLUMN_DATE_OF_JOINING = "dateOfJoining";
    private static final String COLUMN_ASSIGNED_BLOCK = "assignedBlockID";
    private static final String COLUMN_MANAGER_PHONE_NUMBER = "phoneNumber";
    private static final String COLUMN_NATIONALITY = "nationality";

    // Create table query
    private static final String CREATE_TABLE_MANAGERS = "CREATE TABLE IF NOT EXISTS " + TABLE_MANAGERS + "(" +
            COLUMN_MANAGER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MANAGER_NAME + " TEXT, " +
            COLUMN_DESIGNATION + " TEXT DEFAULT 'Manager', " +
            COLUMN_DATE_OF_JOINING + " TEXT, " +
            COLUMN_ASSIGNED_BLOCK + " INTEGER, " +
            COLUMN_MANAGER_PHONE_NUMBER + " TEXT, " +
            COLUMN_NATIONALITY + " TEXT," +
            "FOREIGN KEY(" + COLUMN_ASSIGNED_BLOCK + ") REFERENCES Blocks(" + COLUMN_ID + "))";

    // Events table
    private static final String TABLE_EVENTS = "Events";
    private static final String COLUMN_EVENT_ID = "eventID";
    private static final String COLUMN_EVENT_NAME = "eventName";
    private static final String COLUMN_EVENT_TYPE = "eventType";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_NUMBER_OF_PEOPLE = "numberOfPeople";

    // Create table query
    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE IF NOT EXISTS " + TABLE_EVENTS + "(" +
            COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_EVENT_NAME + " TEXT, " +
            COLUMN_EVENT_TYPE + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_TIME + " TEXT, " +
            COLUMN_NUMBER_OF_PEOPLE + " INTEGER);";


    public static final String TABLE_ANNOUNCEMENTS = "Announcements";
    public static final String ANNOUNCEMENT_COLUMN_ID = "announcementID";
    public static final String COLUMN_ANNOUNCEMENT_TYPE = "type";
    public static final String COLUMN_ANNOUNCEMENT_DESCRIPTION = "description";

    // SQL query to create Announcements table
    private static final String CREATE_ANNOUNCEMENTS_TABLE = "CREATE TABLE " + TABLE_ANNOUNCEMENTS + " (" +
            ANNOUNCEMENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ANNOUNCEMENT_TYPE + " TEXT," +
            COLUMN_ANNOUNCEMENT_DESCRIPTION + " TEXT);";

    private static final String TABLE_CONTACTS = "EmergencyContacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "contactName";
    private static final String KEY_NUMBER = "contactNumber";

    String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT,"
            + KEY_NUMBER + " TEXT" + ")";

    private static final String TABLE_COMPLAINTS = "Complaints";
    private static final String COMPLAINT_ID = "complaintID";
    private static final String BLOCK = "blockID";
    private static final String FLOOR = "floor";
    private static final String DOOR_NO = "doorNo";
    private static final String ISSUE_TYPE = "issueType";
    private static final String REPEATED_ISSUE = "repeatedIssue";
    private static final String WORK_REQUIREMENT = "workRequirement";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String ISSUE_DESCRIPTION = "issueDescription";
    private static final String DATE_OF_REPORT = "dateOfReport";
    private static final String COMPLAINT_STATUS = "complaintStatus";
    private static final String KEY_COMPLAINT_ISSUER_ID = "complaintIssuerId"; // Foreign key


    String CREATE_COMPLAINTS_TABLE = "CREATE TABLE " + TABLE_COMPLAINTS + "("
            + COMPLAINT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + BLOCK + " INTEGER,"
            + FLOOR + " TEXT,"
            + DOOR_NO + " TEXT,"
            + ISSUE_TYPE + " TEXT,"
            + REPEATED_ISSUE + " TEXT,"
            + WORK_REQUIREMENT + " TEXT,"
            + PHONE_NUMBER + " TEXT,"
            + ISSUE_DESCRIPTION + " TEXT,"
            + DATE_OF_REPORT + " TEXT DEFAULT (strftime('%Y-%m-%d %H:%M:%S', 'now', 'localtime')),"
            + COMPLAINT_STATUS + " TEXT, "
            + KEY_COMPLAINT_ISSUER_ID + " INTEGER,"
            + "FOREIGN KEY(" + BLOCK + ") REFERENCES " + TABLE_BLOCKS + "(" + COLUMN_ID + "),"
            + "FOREIGN KEY(" + KEY_COMPLAINT_ISSUER_ID + ") REFERENCES " + "users" + "(" + "userID" + "))";


    private static final String TABLE_REVIEWS = "Reviews";
    private static final String COLUMN_REVIEW_ID = "reviewID";
    private static final String COLUMN_COMPLAINT_ID = "complaintID";
    private static final String COLUMN_PROFESSIONALISM = "professionalism";
    private static final String COLUMN_QUALITY_OF_WORK = "qualityOfWork";
    private static final String COLUMN_COMMUNICATION = "communication";
    private static final String COLUMN_PUNCTUALITY = "punctuality";
    private static final String COLUMN_COMMENTS = "comments";


    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "userID";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_USER_DESIGNATION = "designation";
    private static final String COLUMN_BLOCK_ID = "blockID";
    private static final String COLUMN_USER_EMAIL = "emailId";
    private static final String COLUMN_USER_PHONE_NUMBER = "phoneNumber";

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                "userID INTEGER PRIMARY KEY, " +
                "username TEXT UNIQUE, " +
                "password TEXT, " +
                "designation TEXT, " +
                "blockID INTEGER, " +
                "emailId TEXT, " +
                "phoneNumber TEXT, " +
                "FOREIGN KEY(blockID) REFERENCES Blocks(" + COLUMN_ID + "));");


        MyDB.execSQL("INSERT INTO " + "users" + " (" + COLUMN_USER_ID + ", " + COLUMN_USER_USERNAME + ", " +
                COLUMN_PASSWORD + ", " + COLUMN_USER_EMAIL + ", " +
                COLUMN_USER_PHONE_NUMBER + ", " + COLUMN_USER_DESIGNATION + ", " + COLUMN_BLOCK_ID + ") " +
                "VALUES (1, 'Admin', 'Admin','admin@gmail.com','9876543210', 'Admin', NULL)");

        // Insert user2
        MyDB.execSQL("INSERT INTO " + "users" + " (" + COLUMN_USER_ID + ", " + COLUMN_USER_USERNAME + ", " +
                COLUMN_PASSWORD + ", " + COLUMN_USER_EMAIL + ", " +
                COLUMN_USER_PHONE_NUMBER + ", " + COLUMN_USER_DESIGNATION + ", " + COLUMN_BLOCK_ID + ") " +
                "VALUES (2, 'Manager1', 'Manager1','manager1@gmail.com','9876543210', 'Manager', 2)");

        // Insert user3
        MyDB.execSQL("INSERT INTO " + "users" + " (" + COLUMN_USER_ID + ", " + COLUMN_USER_USERNAME + ", " +
                COLUMN_PASSWORD + ", " + COLUMN_USER_EMAIL + ", " +
                COLUMN_USER_PHONE_NUMBER + ", " + COLUMN_USER_DESIGNATION + ", " + COLUMN_BLOCK_ID + ") " +
                "VALUES (3, 'Manager2', 'Manager2','manager2@gmail.com','9876543210', 'Manager', 1)");

        // Insert user4
        MyDB.execSQL("INSERT INTO " + "users" + " (" + COLUMN_USER_ID + ", " + COLUMN_USER_USERNAME + ", " +
                COLUMN_PASSWORD + ", " + COLUMN_USER_EMAIL + ", " +
                COLUMN_USER_PHONE_NUMBER + ", " + COLUMN_USER_DESIGNATION + ", " + COLUMN_BLOCK_ID + ") " +
                "VALUES (4, 'User1', 'User1','user1@gmail.com','9876543210', 'User', 1)");

        // Insert user5
        MyDB.execSQL("INSERT INTO " + "users" + " (" + COLUMN_USER_ID + ", " + COLUMN_USER_USERNAME + ", " +
                COLUMN_PASSWORD + ", " + COLUMN_USER_EMAIL + ", " +
                COLUMN_USER_PHONE_NUMBER + ", " + COLUMN_USER_DESIGNATION + ", " + COLUMN_BLOCK_ID + ") " +
                "VALUES (5, 'User2', 'User2','user2@gmail.com','9876543210', 'User', 2)");


        MyDB.execSQL(CREATE_TABLE_BLOCKS);
        MyDB.execSQL("INSERT INTO " + TABLE_BLOCKS + " (" + COLUMN_BLOCK + ", " + COLUMN_BLOCK_NAME + ", " +
                COLUMN_PARKING_AVAILABILITY + ", " + COLUMN_FURNISHED + ") VALUES " +
                "('Block A', 'Analog', 1, 1)," +
                "('Block B', 'Bird', 1, 1);");

        MyDB.execSQL(CREATE_STAFF_TABLE);
        MyDB.execSQL(CREATE_TABLE_MANAGERS);
        MyDB.execSQL(CREATE_TABLE_EVENTS);
        MyDB.execSQL(CREATE_ANNOUNCEMENTS_TABLE);
        MyDB.execSQL(CREATE_CONTACTS_TABLE);
        MyDB.execSQL(CREATE_COMPLAINTS_TABLE);

        MyDB.execSQL("CREATE TABLE " + TABLE_REVIEWS + "(" +
                COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COMPLAINT_ID + " INTEGER, " +
                COLUMN_PROFESSIONALISM + " REAL CHECK(" + COLUMN_PROFESSIONALISM + " BETWEEN 0.0 AND 5.0), " +
                COLUMN_QUALITY_OF_WORK + " REAL CHECK(" + COLUMN_QUALITY_OF_WORK + " BETWEEN 0.0 AND 5.0), " +
                COLUMN_COMMUNICATION + " REAL CHECK(" + COLUMN_COMMUNICATION + " BETWEEN 0.0 AND 5.0), " +
                COLUMN_PUNCTUALITY + " REAL CHECK(" + COLUMN_PUNCTUALITY + " BETWEEN 0.0 AND 5.0), " +
                COLUMN_COMMENTS + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_COMPLAINT_ID + ") REFERENCES Complaints(" + COLUMN_COMPLAINT_ID + "));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists Blocks");
        MyDB.execSQL("drop Table if exists Staff");
        MyDB.execSQL("drop Table if exists Managers");
        MyDB.execSQL("drop Table if exists Announcements");
        MyDB.execSQL("drop Table if exists EmergencyContacts");
        MyDB.execSQL("drop Table if exists Complaints");

    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    @SuppressLint("Range")
    public User getUserDetails(int userID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] columns = {
                "userID",
                "username",
                "password",
                "designation",
                "blockID",
                "emailId",
                "phoneNumber"
                // Add other columns as needed
        };

        Cursor cursor = MyDB.query("users", columns, "userID = ?", new String[]{String.valueOf(userID)}, null, null, null);

        User user = null;

        if (cursor.moveToFirst()) {
            // Create a User object and populate it with details from the cursor
            user = new User();
            user.setUserID(cursor.getInt(cursor.getColumnIndex("userID")));
            user.setUserName(cursor.getString(cursor.getColumnIndex("username")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setDesignation(cursor.getString(cursor.getColumnIndex("designation")));
            user.setBlockID(cursor.getInt(cursor.getColumnIndex("blockID")));
            user.setEmailId(cursor.getString(cursor.getColumnIndex("emailId")));
            user.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phoneNumber")));
            // Add other details as needed
        }

        cursor.close();
        return user;
    }



    @SuppressLint("Range")
    public int checkusernamepassword(String username, String password, String designation) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT userID FROM users WHERE username = ? AND password = ? AND designation = ?", new String[]{username, password, designation});

        int userID = -1;

        if (cursor.moveToFirst()) {
            userID = cursor.getInt(cursor.getColumnIndex("userID"));
        }

        cursor.close();
        return userID;
    }


    public long insertBlock(String block, String blockName, boolean parkingAvailability, boolean furnished) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (isBlockExists(db, block)) {
            // Block already exists, return -1 or handle the situation as needed
            return -1;
        }
        values.put(COLUMN_BLOCK, block);
        values.put(COLUMN_BLOCK_NAME, blockName);
        values.put(COLUMN_PARKING_AVAILABILITY, parkingAvailability ? 1 : 0);
        values.put(COLUMN_FURNISHED, furnished ? 1 : 0);

        return db.insert(TABLE_BLOCKS, null, values);
    }

    private boolean isBlockExists(SQLiteDatabase db, String block) {
        Cursor cursor = db.query(
                TABLE_BLOCKS,                           // Table name
                new String[]{COLUMN_BLOCK},            // Columns to return
                COLUMN_BLOCK + "=?",                   // Selection (WHERE clause)
                new String[]{block},                   // Selection arguments
                null, null, null, null
        );

        // If the cursor has any rows, the block already exists
        boolean exists = cursor.getCount() > 0;

        // Close the cursor
        cursor.close();

        return exists;
    }

    @SuppressLint("Range")
    public List<BlockCardModel> getAllBlocks() {
        List<BlockCardModel> blockList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BLOCKS, null);

        if (cursor.moveToFirst()) {
            do {
                BlockCardModel block = new BlockCardModel();
                block.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                block.setBlock(cursor.getString(cursor.getColumnIndex(COLUMN_BLOCK)));
                block.setBlockName(cursor.getString(cursor.getColumnIndex(COLUMN_BLOCK_NAME)));
                block.setParkingAvailability(cursor.getInt(cursor.getColumnIndex(COLUMN_PARKING_AVAILABILITY)) == 1);
                block.setFurnished(cursor.getInt(cursor.getColumnIndex(COLUMN_FURNISHED)) == 1);

                blockList.add(block);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return blockList;
    }

    public int deleteBlock(long blockId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BLOCKS, COLUMN_ID + " = ?", new String[]{String.valueOf(blockId)});
    }

    public int updateBlock(long blockId, String newBlock, String newBlockName, boolean newParkingAvailability, boolean newFurnished) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BLOCK, newBlock);
        values.put(COLUMN_BLOCK_NAME, newBlockName);
        values.put(COLUMN_PARKING_AVAILABILITY, newParkingAvailability ? 1 : 0);
        values.put(COLUMN_FURNISHED, newFurnished ? 1 : 0);

        return db.update(TABLE_BLOCKS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(blockId)});
    }

    @SuppressLint("Range")
    public List<StaffModel> getAllStaff() {
        List<StaffModel> staffList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to select all records from the Staff table
        String query = "SELECT * FROM " + TABLE_STAFF;

        Cursor cursor = db.rawQuery(query, null);

        // Loop through the cursor to retrieve each staff record
        if (cursor.moveToFirst()) {
            do {
                StaffModel staff = new StaffModel();
                staff.setId(cursor.getLong(cursor.getColumnIndex(STAFF_COLUMN_ID)));
                staff.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                staff.setProfession(cursor.getString(cursor.getColumnIndex(COLUMN_PROFESSION)));
                staff.setBlockID(cursor.getLong(cursor.getColumnIndex(COLUMN_STAFF_BLOCK)));
                staff.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
                staff.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
                staff.setJoiningDate(cursor.getString(cursor.getColumnIndex(COLUMN_JOINING_DATE)));

                staffList.add(staff);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return staffList;
    }


    public long insertStaff(String name, String profession, long block, String phoneNumber, String username, String joiningDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PROFESSION, profession);
        values.put(COLUMN_STAFF_BLOCK, block);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_JOINING_DATE, joiningDate);

        long newRowId = db.insert(TABLE_STAFF, null, values);

        db.close();

        return newRowId;
    }

    public int updateStaff(long id, String name, String profession, long block, String phoneNumber, String username, String joiningDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PROFESSION, profession);
        values.put(COLUMN_STAFF_BLOCK, block);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_JOINING_DATE, joiningDate);

        int rowsAffected = db.update(
                TABLE_STAFF,
                values,
                STAFF_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return rowsAffected;
    }

    public int deleteStaff(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int rowsAffected = db.delete(
                TABLE_STAFF,
                STAFF_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return rowsAffected;
    }

    public long insertManager(String name, String dateOfJoining, long assignedBlock, String phoneNumber, String nationality) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_MANAGER_NAME, name);
        values.put(COLUMN_DATE_OF_JOINING, dateOfJoining);
        values.put(COLUMN_ASSIGNED_BLOCK, assignedBlock);
        values.put(COLUMN_MANAGER_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_NATIONALITY, nationality);

        return db.insert(TABLE_MANAGERS, null, values);
    }

    // Get all managers
    @SuppressLint("Range")
    public List<ManagerModel> getAllManagers() {
        List<ManagerModel> managerList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MANAGERS, null);

        if (cursor.moveToFirst()) {
            do {
                ManagerModel manager = new ManagerModel();
                manager.setManagerID(cursor.getLong(cursor.getColumnIndex(COLUMN_MANAGER_ID)));
                manager.setManagerName(cursor.getString(cursor.getColumnIndex(COLUMN_MANAGER_NAME)));
                manager.setDesignation(cursor.getString(cursor.getColumnIndex(COLUMN_DESIGNATION)));
                manager.setDateOfJoining(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_OF_JOINING)));
                manager.setAssignedBlockID(cursor.getInt(cursor.getColumnIndex(COLUMN_ASSIGNED_BLOCK)));
                manager.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_MANAGER_PHONE_NUMBER)));
                manager.setNationality(cursor.getString(cursor.getColumnIndex(COLUMN_NATIONALITY)));

                managerList.add(manager);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return managerList;
    }

    // Delete a manager
    public int deleteManager(long managerID) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_MANAGERS, COLUMN_MANAGER_ID + " = ?", new String[]{String.valueOf(managerID)});
    }

    // Update a manager
    public int updateManager(long managerID, String name, String dateOfJoining, long assignedBlock, String phoneNumber, String nationality) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_MANAGER_NAME, name);
        values.put(COLUMN_DATE_OF_JOINING, dateOfJoining);
        values.put(COLUMN_ASSIGNED_BLOCK, assignedBlock);
        values.put(COLUMN_MANAGER_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_NATIONALITY, nationality);

        return db.update(TABLE_MANAGERS, values, COLUMN_MANAGER_ID + " = ?", new String[]{String.valueOf(managerID)});
    }

    public long addEvent(String eventName, String eventType, String date, String time, long numberOfPeople) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_EVENT_NAME, eventName);
        values.put(COLUMN_EVENT_TYPE, eventType);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_NUMBER_OF_PEOPLE, numberOfPeople);

        return db.insert(TABLE_EVENTS, null, values);
    }

    // Get all events
    @SuppressLint("Range")
    public List<EventModel> getEvents() {
        List<EventModel> eventList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);

        if (cursor.moveToFirst()) {
            do {
                EventModel event = new EventModel();
                event.setEventID(cursor.getLong(cursor.getColumnIndex(COLUMN_EVENT_ID)));
                event.setEventName(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_NAME)));
                event.setEventType(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TYPE)));
                event.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                event.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
                event.setNumberOfPeople(cursor.getString(cursor.getColumnIndex(COLUMN_NUMBER_OF_PEOPLE)));

                eventList.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return eventList;
    }


    public long insertAnnouncement(String type, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ANNOUNCEMENT_TYPE, type);
        values.put(COLUMN_ANNOUNCEMENT_DESCRIPTION, description);
        return db.insert(TABLE_ANNOUNCEMENTS, null, values);
    }

    @SuppressLint("Range")
    public List<AnnouncementModel> getAnnouncements() {
        List<AnnouncementModel> announcementList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ANNOUNCEMENTS, null);

        if (cursor.moveToFirst()) {
            do {
                AnnouncementModel announcement = new AnnouncementModel();
                announcement.setAnnouncementID(cursor.getLong(cursor.getColumnIndex(ANNOUNCEMENT_COLUMN_ID)));
                announcement.setType(cursor.getString(cursor.getColumnIndex(COLUMN_ANNOUNCEMENT_TYPE)));
                announcement.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_ANNOUNCEMENT_DESCRIPTION)));

                announcementList.add(announcement);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return announcementList;
    }

    public long insertContact(String contactName, String contactNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contactName);
        values.put(KEY_NUMBER, contactNumber);

        // Inserting Row
        long id = db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
        return id;
    }

    // Method to get all contacts
    @SuppressLint("Range")
    public List<EmergencyContactModel> getAllContacts() {
        List<EmergencyContactModel> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EmergencyContactModel contact = new EmergencyContactModel();
                contact.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                contact.setContactName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                contact.setContactNumber(cursor.getString(cursor.getColumnIndex(KEY_NUMBER)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // close cursor and database
        cursor.close();
        db.close();

        // return contact list
        return contactList;
    }

    public long insertComplaint(ComplaintModel complaint) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BLOCK, complaint.getBlockID());
        values.put(FLOOR, complaint.getFloor());
        values.put(DOOR_NO, complaint.getDoorNo());
        values.put(ISSUE_TYPE, complaint.getIssueType());
        values.put(REPEATED_ISSUE, complaint.getRepeatedIssue());
        values.put(WORK_REQUIREMENT, complaint.getWorkRequirement());
        values.put(PHONE_NUMBER, complaint.getPhoneNumber());
        values.put(ISSUE_DESCRIPTION, complaint.getIssueDescription());
        values.put(KEY_COMPLAINT_ISSUER_ID, complaint.getIssuerID());
        values.put(COMPLAINT_STATUS, complaint.getStatus());

        // Inserting the complaint and getting its ID
        long complaintId = db.insert(TABLE_COMPLAINTS, null, values);

        db.close(); // Closing database connection

        return complaintId;
    }

    // Method to get all complaints
    @SuppressLint("Range")
    public List<ComplaintModel> getComplaints() {
        List<ComplaintModel> complaintsList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_COMPLAINTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ComplaintModel complaint = new ComplaintModel();
                complaint.setComplaintID(cursor.getInt(cursor.getColumnIndex(COMPLAINT_ID)));
                complaint.setBlockID(cursor.getLong(cursor.getColumnIndex(BLOCK)));
                complaint.setFloor(cursor.getString(cursor.getColumnIndex(FLOOR)));
                complaint.setDoorNo(cursor.getString(cursor.getColumnIndex(DOOR_NO)));
                complaint.setIssueType(cursor.getString(cursor.getColumnIndex(ISSUE_TYPE)));
                complaint.setRepeatedIssue(cursor.getString(cursor.getColumnIndex(REPEATED_ISSUE)));
                complaint.setWorkRequirement(cursor.getString(cursor.getColumnIndex(WORK_REQUIREMENT)));
                complaint.setPhoneNumber(cursor.getString(cursor.getColumnIndex(PHONE_NUMBER)));
                complaint.setIssueDescription(cursor.getString(cursor.getColumnIndex(ISSUE_DESCRIPTION)));
                complaint.setDateOfReport(cursor.getString(cursor.getColumnIndex(DATE_OF_REPORT)));
                complaint.setIssuerID(cursor.getInt(cursor.getColumnIndex(KEY_COMPLAINT_ISSUER_ID)));
                complaint.setStatus(cursor.getString(cursor.getColumnIndex(COMPLAINT_STATUS)));
                // Adding complaint to list
                complaintsList.add(complaint);
            } while (cursor.moveToNext());
        }

        // Closing cursor and database connection
        cursor.close();
        db.close();

        // Return complaints list
        return complaintsList;
    }

    public String getBlockNameById(int blockId) {
        String blockName = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Define the columns you want to retrieve
            String[] columns = {COLUMN_BLOCK};

            // Define the selection criteria
            String selection = COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(blockId)};

            // Query the database
            cursor = db.query(TABLE_BLOCKS, columns, selection, selectionArgs, null, null, null);

            // Check if the cursor has data
            if (cursor != null && cursor.moveToFirst()) {
                // Retrieve the blockName from the cursor
                blockName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BLOCK));
            }
        } finally {
            // Close the cursor to avoid memory leaks
            if (cursor != null) {
                cursor.close();
            }
            // Close the database connection
            db.close();
        }

        return blockName;
    }

    public boolean deleteComplaint(int complaintID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COMPLAINT_ID + " = ?";
        String[] whereArgs = {String.valueOf(complaintID)};

        // Attempt to delete the complaint
        int deletedRows = db.delete(TABLE_COMPLAINTS, whereClause, whereArgs);

        db.close();

        // Check if any rows were deleted
        return deletedRows > 0;
    }

    public boolean updateComplaintStatus(int complaintID, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COMPLAINT_STATUS, newStatus);

        String whereClause = COMPLAINT_ID + " = ?";
        String[] whereArgs = {String.valueOf(complaintID)};

        // Attempt to update the status
        int updatedRows = db.update(TABLE_COMPLAINTS, values, whereClause, whereArgs);

        db.close();

        // Check if any rows were updated
        return updatedRows > 0;
    }

    public long insertReview(int complaintID, float professionalism, float qualityOfWork, float communication, float punctuality, String comments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPLAINT_ID, complaintID);
        values.put(COLUMN_PROFESSIONALISM, professionalism);
        values.put(COLUMN_QUALITY_OF_WORK, qualityOfWork);
        values.put(COLUMN_COMMUNICATION, communication);
        values.put(COLUMN_PUNCTUALITY, punctuality);
        values.put(COLUMN_COMMENTS, comments);

        long result = db.insert(TABLE_REVIEWS, null, values);
        db.close();
        return result;
    }

    // Method to get reviews by complaint ID
    public ArrayList<ReviewModel> getReviewsByComplaintID(int complaintID) {
        ArrayList<ReviewModel> reviewsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] columns = {COLUMN_REVIEW_ID, COLUMN_COMPLAINT_ID, COLUMN_PROFESSIONALISM, COLUMN_QUALITY_OF_WORK, COLUMN_COMMUNICATION, COLUMN_PUNCTUALITY, COLUMN_COMMENTS};
            String selection = COLUMN_COMPLAINT_ID + " = ?";
            String[] selectionArgs = {String.valueOf(complaintID)};

            cursor = db.query(TABLE_REVIEWS, columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int reviewID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
                    float professionalism = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROFESSIONALISM));
                    float qualityOfWork = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUALITY_OF_WORK));
                    float communication = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COMMUNICATION));
                    float punctuality = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PUNCTUALITY));
                    String comments = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENTS));

                    ReviewModel review = new ReviewModel(reviewID, complaintID, professionalism, qualityOfWork, communication, punctuality, comments);
                    reviewsList.add(review);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return reviewsList;
    }


    @SuppressLint("Range")
    public ArrayList<ComplaintModel> getComplaintsByUserID(int userID) {
        ArrayList<ComplaintModel> complaintList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {COMPLAINT_ID, KEY_COMPLAINT_ISSUER_ID, COMPLAINT_STATUS, ISSUE_DESCRIPTION, DATE_OF_REPORT, ISSUE_TYPE};

        // Specify the condition (user ID matches)
        String selection = KEY_COMPLAINT_ISSUER_ID + "=?";
        String[] selectionArgs = {String.valueOf(userID)};

        // Execute the query
        Cursor cursor = db.query(TABLE_COMPLAINTS, columns, selection, selectionArgs, null, null, null);

        // Iterate through the result set and add complaints to the list
        if (cursor.moveToFirst()) {
            do {
                int complaintID = cursor.getInt(cursor.getColumnIndex(COMPLAINT_ID));
                int userId = cursor.getInt(cursor.getColumnIndex(KEY_COMPLAINT_ISSUER_ID));
                String status = cursor.getString(cursor.getColumnIndex(COMPLAINT_STATUS));
                String description = cursor.getString(cursor.getColumnIndex(ISSUE_DESCRIPTION));
                String date = cursor.getString(cursor.getColumnIndex(DATE_OF_REPORT));
                String issueType = cursor.getString(cursor.getColumnIndex(ISSUE_TYPE));
                // Create ComplaintModel object and add to the list
                ComplaintModel complaint = new ComplaintModel();
                complaint.setStatus(status);
                complaint.setIssuerID(userId);
                complaint.setDateOfReport(date);
                complaint.setIssueDescription(description);
                complaint.setComplaintID(complaintID);
                complaint.setIssueType(issueType);
                complaintList.add(complaint);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return complaintList;
    }

    @SuppressLint("Range")
    public ArrayList<ArrayList<String>> getAllUsersDetailsWithBlockName() {
        ArrayList<ArrayList<String>> userDetailsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the columns you want to retrieve
        String[] userColumns = {"users.username", "users.phoneNumber", "Blocks.block"};

        // Specify the join condition
        String joinCondition = "users.blockID = Blocks.blockID";

        // Execute the query for user details with blockName
        Cursor userCursor = db.query(TABLE_USERS + " INNER JOIN " + TABLE_BLOCKS + " ON " + joinCondition,
                userColumns, "users.designation=?", new String[]{"User"}, null, null, null);

        // Iterate through the result set and add user details to the list
        if (userCursor.moveToFirst()) {
            do {
                String username = userCursor.getString(userCursor.getColumnIndex("username"));
                String mobileNumber = userCursor.getString(userCursor.getColumnIndex("phoneNumber"));
                String blockName = userCursor.getString(userCursor.getColumnIndex("block"));

                // Create UserDetailsModel object and add to the list
                ArrayList<String> userDetails = new ArrayList<>();
                userDetails.add(username);
                userDetails.add(mobileNumber);
                userDetails.add(blockName);
                userDetailsList.add(userDetails);
            } while (userCursor.moveToNext());
        }

        // Close the user cursor and database
        userCursor.close();
        db.close();

        return userDetailsList;
    }

    @SuppressLint("Range")
    public String getUsernameFromUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"username"};
        String selection = "userID" + "=?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        String username = "";

        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex("username"));
        }

        cursor.close();
        db.close();

        return username;
    }


    @SuppressLint("Range")
    public ArrayList<ComplaintModel> getComplaintsByBlockID(int blockID) {
        ArrayList<ComplaintModel> complaintsList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_COMPLAINTS +" WHERE blockID = " + blockID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ComplaintModel complaint = new ComplaintModel();
                complaint.setComplaintID(cursor.getInt(cursor.getColumnIndex(COMPLAINT_ID)));
                complaint.setBlockID(cursor.getLong(cursor.getColumnIndex(BLOCK)));
                complaint.setFloor(cursor.getString(cursor.getColumnIndex(FLOOR)));
                complaint.setDoorNo(cursor.getString(cursor.getColumnIndex(DOOR_NO)));
                complaint.setIssueType(cursor.getString(cursor.getColumnIndex(ISSUE_TYPE)));
                complaint.setRepeatedIssue(cursor.getString(cursor.getColumnIndex(REPEATED_ISSUE)));
                complaint.setWorkRequirement(cursor.getString(cursor.getColumnIndex(WORK_REQUIREMENT)));
                complaint.setPhoneNumber(cursor.getString(cursor.getColumnIndex(PHONE_NUMBER)));
                complaint.setIssueDescription(cursor.getString(cursor.getColumnIndex(ISSUE_DESCRIPTION)));
                complaint.setDateOfReport(cursor.getString(cursor.getColumnIndex(DATE_OF_REPORT)));
                complaint.setIssuerID(cursor.getInt(cursor.getColumnIndex(KEY_COMPLAINT_ISSUER_ID)));
                complaint.setStatus(cursor.getString(cursor.getColumnIndex(COMPLAINT_STATUS)));
                // Adding complaint to list
                complaintsList.add(complaint);
            } while (cursor.moveToNext());
        }

        // Closing cursor and database connection
        cursor.close();
        db.close();

        // Return complaints list
        return complaintsList;
    }

    @SuppressLint("Range")
    public float getOverallAverageRating(int complaintID) {
        float overallAverageRating = 0.0f;

        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get overall average rating for a specific complaint
        String query = "SELECT (AVG(professionalism) + AVG(qualityOfWork) + AVG(communication) + AVG(punctuality)) / 4 AS overallAverage " +
                "FROM Reviews WHERE complaintID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(complaintID)});

        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve overall average rating
            overallAverageRating = cursor.getFloat(cursor.getColumnIndex("overallAverage"));

            cursor.close();
        }

        return overallAverageRating;
    }
}