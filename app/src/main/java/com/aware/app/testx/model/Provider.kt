package com.aware.app.testx.model

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.provider.BaseColumns
import com.aware.utils.DatabaseHelper
import java.util.HashMap

class Provider : ContentProvider() {

    // ContentProvider database version. Increment every time you modify the database structure
    private val DATABASE_VERSION = 1

    // Database stored in file: /Android/data/package.name/AWARE/testx.db
    private val DATABASE_NAME = "testx.db"

    companion object {
        // Authority of this content provider
        var AUTHORITY = "com.aware.app.testx.model.provider.testx"

        // Users table
        const val DB_TBL_USERS = "users"
        private val DB_TBL_USERS_FIELDS =
                AWAREColumns._ID + " integer primary key autoincrement," +
                        AWAREColumns.TIMESTAMP + " real default 0," +
                        AWAREColumns.DEVICE_ID + " text default ''," +
                        Users_Table.DATA + " longtext default ''"

//        val DB_TBL_GAME_BALL = "game_ball"
//        val DB_TBL_GAME_DRAWING_DATA = "game_drawing_data"
//        val DB_TBL_GAME_DRAWING_SCREENSHOT = "game_drawing_screenshot"
//        val DB_TBL_MEDICATION = "medication"
//        val DB_TBL_HEALTH_SURVEY = "health_survey"
//        val DB_TBL_FEEDBACK = "feedback"
//        val DB_TBL_NOTIFICATIONS = "notifications"

        // Database tables
        val DATABASE_TABLES = arrayOf(DB_TBL_USERS)

        // Share the fields with AWARE so we can replicate the table schema on the server
        val TABLES_FIELDS = arrayOf(DB_TBL_USERS_FIELDS)

        // Returns the provider authority that is dynamic
        fun getAuthority(context: Context): String {
            AUTHORITY = context.packageName + ".model.provider.testx"
            return AUTHORITY
        }
    }

    // ContentProvider query indexes
    private val TABLE_USERS_DIR = 1
    private val TABLE_USERS_ITEM = 2

    //These are columns that we need to sync data, don't change this!
    interface AWAREColumns : BaseColumns {
        companion object {
            const val _ID = "_id"
            const val TIMESTAMP = "timestamp"
            const val DEVICE_ID = "device_id"
        }
    }

    // Users table
    class Users_Table : AWAREColumns {
        companion object {
            val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$DB_TBL_USERS")
            val CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.aware.app.testx.model.provider.$DB_TBL_USERS"
            val CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.aware.app.testx.model.provider.$DB_TBL_USERS"

            const val DATA = "data"
        }
    }

    //Helper variables for ContentProvider - DO NOT CHANGE
    private var sUriMatcher: UriMatcher? = null
    private var dbHelper: DatabaseHelper? = null
    private var database: SQLiteDatabase? = null
    private fun initialiseDatabase() {
        if (dbHelper == null)
            dbHelper = DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION, DATABASE_TABLES, TABLES_FIELDS)
        if (database == null)
            database = dbHelper!!.writableDatabase
    }

    //For each table, create a hashmap needed for database queries
    private var tableUsersHash: HashMap<String, String> = HashMap()


    override fun onCreate(): Boolean {
        //This is a hack to allow providers to be reusable in any application/plugin
        // by making the authority dynamic using the package name of the parent app
        AUTHORITY = context!!.packageName + ".model.provider.testx"

        sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        //Game table indexes DIR and ITEM
        sUriMatcher!!.addURI(AUTHORITY, DATABASE_TABLES[0], TABLE_USERS_DIR)
        sUriMatcher!!.addURI(AUTHORITY, DATABASE_TABLES[0] + "/#", TABLE_USERS_ITEM)

        //Users table HasMap
        tableUsersHash = HashMap()
        tableUsersHash[AWAREColumns._ID] = AWAREColumns._ID
        tableUsersHash[AWAREColumns.TIMESTAMP] = AWAREColumns.TIMESTAMP
        tableUsersHash[AWAREColumns.DEVICE_ID] = AWAREColumns.DEVICE_ID
        tableUsersHash[Users_Table.DATA] = Users_Table.DATA

        return true
    }

    override fun insert(uri: Uri, initialValues: ContentValues?): Uri? {
        initialiseDatabase()
        val values = if (initialValues != null) ContentValues(initialValues) else ContentValues()
        database!!.beginTransaction()

        when (sUriMatcher!!.match(uri)) {

            TABLE_USERS_DIR -> {
                val game_id = database!!.insert(DATABASE_TABLES[0], AWAREColumns.DEVICE_ID, values)
                database!!.setTransactionSuccessful()
                database!!.endTransaction()
                if (game_id > 0) {
                    val dataUri = ContentUris.withAppendedId(Users_Table.CONTENT_URI, game_id)
                    context!!.contentResolver.notifyChange(dataUri, null, false)
                    return dataUri
                }
                database!!.endTransaction()
                throw SQLException("Failed to insert row into $uri")
            }

            else -> {
                database!!.endTransaction()
                throw IllegalArgumentException("Unknown URI $uri")
            }
        }
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        initialiseDatabase()
        val qb = SQLiteQueryBuilder()

        when (sUriMatcher!!.match(uri)) {

            TABLE_USERS_DIR -> {
                qb.tables = DATABASE_TABLES[0]
                qb.setProjectionMap(tableUsersHash) //the hashmap of the table
            }

            else -> throw IllegalArgumentException("Unknown URI $uri")
        }

        //Don't change me
        return try {
            val c = qb.query(database, projection, selection, selectionArgs, null, null, sortOrder)
            c.setNotificationUri(context!!.contentResolver, uri)
            c
        } catch (e: IllegalStateException) {
            null
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        initialiseDatabase()
        database!!.beginTransaction()

        val count: Int
        when (sUriMatcher!!.match(uri)) {

            TABLE_USERS_DIR -> count = database!!.update(DATABASE_TABLES[0], values, selection, selectionArgs)

            else -> {
                database!!.endTransaction()
                throw IllegalArgumentException("Unknown URI $uri")
            }
        }

        database!!.setTransactionSuccessful()
        database!!.endTransaction()
        context!!.contentResolver.notifyChange(uri, null, false)

        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        initialiseDatabase()
        database!!.beginTransaction()

        val count: Int
        when (sUriMatcher!!.match(uri)) {

            TABLE_USERS_DIR -> count = database!!.delete(DATABASE_TABLES[0], selection, selectionArgs)

            else -> {
                database!!.endTransaction()
                throw IllegalArgumentException("Unknown URI $uri")
            }
        }

        database!!.setTransactionSuccessful()
        database!!.endTransaction()
        context!!.contentResolver.notifyChange(uri, null, false)

        return count
    }

    override fun getType(uri: Uri): String? {
        return when (sUriMatcher!!.match(uri)) {
            TABLE_USERS_DIR -> Users_Table.CONTENT_TYPE
            TABLE_USERS_ITEM -> Users_Table.CONTENT_ITEM_TYPE

            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
    }

}