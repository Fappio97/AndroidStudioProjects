package it.unical.demacs.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Login private constructor() {

    inner class User(var username: String, var country: String, var details: String)

    private val dbHelper = Database(MainActivity.applicationContext())

    fun getSize() = DatabaseUtils.queryNumEntries(dbHelper.readableDatabase, Database.UserTable.TABLE_NAME).toInt()

    fun checkUserExist(username: String, password: String): Boolean {
        val db = dbHelper.readableDatabase

/*
        db.query(
                String table,
                String[] columns,
                String selection,
                String[] selectionArgs,
                String groupBy,
                String having,
                String orderBy)
*/

        var cursor: Cursor? = db.query(
            Database.UserTable.TABLE_NAME,
            arrayOf(
                Database.UserTable.COLUMN_USER,
                Database.UserTable.COLUMN_PASSWORD
            ),
            "${Database.UserTable.COLUMN_USER}=?",
            arrayOf(username),
            "",
            "",
            ""
        )
        val sql = "SELECT " + Database.UserTable.COLUMN_USER + " ," +
                Database.UserTable.COLUMN_PASSWORD + " " +
                "FROM " + Database.UserTable.TABLE_NAME + " " +
                "WHERE " + Database.UserTable.COLUMN_USER + " =? " +
                "AND " + Database.UserTable.COLUMN_PASSWORD + "=?"
        cursor = db.rawQuery(sql, arrayOf(username, password))
        if(cursor.count > 0) {
            Log.d(
                "DEBUG",
                "qui"
            )
            cursor.moveToFirst()
            Log.d(
                "DEBUG",
                "${cursor.getString(0)} + ${cursor.getString(1)} + ${cursor.count}"
            )

        }
        val cond = cursor.count != 0

        cursor.close()


        return cond
    }

    fun getUserElement(position:Int) : User {
        val db = dbHelper.readableDatabase

        /*
        val cursor = db.query(
                CoursesDBHelper.CourseTable.TABLE_NAME,
                arrayOf(CoursesDBHelper.CourseTable.COLUMN_NAME,
                        CoursesDBHelper.CourseTable.COLUMN_CODICE),
                "",
                null,
                "",
                "",
                CoursesDBHelper.CourseTable.COLUMN_NAME,
                ""+position+",1" );
        */

        val sql = "SELECT " + Database.UserDetail.COLUMN_USER + " ," +
                Database.UserDetail.COLUMN_COUNTRY + " " +
                "FROM " + Database.UserDetail.TABLE_NAME + " " +
                "ORDER BY " + Database.UserDetail.COLUMN_USER + " ASC " +
                "LIMIT " + position + ",1"
        val cursor = db.rawQuery(
            sql,
            null
        )

        val course = User("Undefined","empty","")
        if (cursor.moveToNext()) {
            course.username =cursor.getString(0)
            course.country=cursor.getString(1)
        }
        cursor.close()

        return course
    }

    fun getDetail(username: String) : User {
        val db = dbHelper.readableDatabase

        val sql = "SELECT " + Database.UserDetail.COLUMN_USER + " ," +
                Database.UserDetail.COLUMN_COUNTRY + " ," +
                Database.UserDetail.COLUMN_DETAIL + " " +
                "FROM " + Database.UserDetail.TABLE_NAME + " " +
                "WHERE " + Database.UserDetail.COLUMN_USER + " =?";
        val cursor = db.rawQuery(sql, arrayOf(username))

        val user = User( username, "empty", "empty")

        if(cursor.moveToNext()) {
            user.username = cursor.getString(0)
            user.country = cursor.getString(1)
            user.details = cursor.getString(2)
        }
        cursor.close()

        return user

    }

    companion object {
        @JvmStatic val instance = Login()
    }

}

class Database (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,
    null, DATABASE_VERSION) {

    object UserTable {
        const val TABLE_NAME = "login"

        const val COLUMN_USER = "username"
        const val COLUMN_PASSWORD = "password"

        internal const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_USER TEXT NOT NULL, " +
                "$COLUMN_PASSWORD TEXT NOT NULL, " +
                "CONSTRAINT ${TABLE_NAME}_pk PRIMARY KEY ($COLUMN_USER) )"

        internal const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object UserDetail {
        const val TABLE_NAME = "user_detail"

        const val COLUMN_USER = "username"
        const val COLUMN_DETAIL = "detail"
        const val COLUMN_COUNTRY = "country"

        internal const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_USER TEXT NOT NULL, " +
                "$COLUMN_DETAIL TEXT NOT NULL, " +
                "$COLUMN_COUNTRY TEXT NOT NULL, " +
                "CONSTRAINT ${TABLE_NAME}_pk PRIMARY KEY ($COLUMN_USER) )"

        internal const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    companion object {
        private const val DATABASE_NAME = "Didattica"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(p0: SQLiteDatabase) {

        p0.execSQL(UserTable.SQL_CREATE_TABLE)
        p0.execSQL(UserDetail.SQL_CREATE_TABLE)

        val row = ContentValues()
        row.clear()

        row.put(UserTable.COLUMN_USER, "fabio")
        row.put(UserTable.COLUMN_PASSWORD, "a")
        p0.insert(UserTable.TABLE_NAME, null, row)

        row.remove(UserTable.COLUMN_PASSWORD)
        row.put(UserDetail.COLUMN_DETAIL, "Student")
        row.put(UserDetail.COLUMN_COUNTRY, "IT")
        p0.insert(UserDetail.TABLE_NAME, null, row)

        row.clear()

        row.put(UserTable.COLUMN_USER, "marco")
        row.put(UserTable.COLUMN_PASSWORD, "b")
        p0.insert(UserTable.TABLE_NAME, null, row)

        row.remove(UserTable.COLUMN_PASSWORD)
        row.put(UserDetail.COLUMN_DETAIL, "Worker")
        row.put(UserDetail.COLUMN_COUNTRY, "US")
        p0.insert(UserDetail.TABLE_NAME, null, row)

        row.clear()

    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        if(p2 > p1) {
            p0.execSQL(UserTable.SQL_DROP_TABLE)
            onCreate(p0)
        }
    }
}