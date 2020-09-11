import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todoapp.User


class DBHelper (context: Context):SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VER){
    companion object{
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "MyDB.db"

        //table
        private val TABLE_NAME = "User"
        private val COL_ID  = "Id"
        private val COL_USERNAME = "UserName"
        private val COL_EMAIL = "Email"
        private val COL_PASSWORD = "Password"
    }

    override fun onCreate(db : SQLiteDatabase){
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY, $COL_USERNAME TEXT, $COL_EMAIL TEXT, $COL_PASSWORD TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
        System.out.println("Database and table created")
    }

    override fun onUpgrade(db : SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }



    fun addUser(user : User)
    {
        val db = this.writableDatabase
        val values = ContentValues()//CRUD
        values.put(COL_ID, user.userID)
        values.put(COL_USERNAME, user.UserName)
        values.put(COL_EMAIL, user.Email)
        values.put(COL_PASSWORD, user.Password)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    val allUser:List<User>
        get() {

                val listUsers = ArrayList<User>()
                val selectQuery = "SELECT * FROM $TABLE_NAME"
                    val db = this.writableDatabase
                val cursor = db.rawQuery(selectQuery, null)
                if (cursor.moveToFirst()) {
                    do {
                        val user = User()
                        user.userID = cursor.getInt(cursor.getColumnIndex(COL_ID)).toString()
                        user.UserName = cursor.getString(cursor.getColumnIndex(COL_USERNAME))
                        user.Email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                        user.Password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))

                        listUsers.add(user)

                    } while (cursor.moveToNext())
                }
                return listUsers

        }

    fun updateUser(user : User):Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, user.userID)
        values.put(COL_USERNAME, user.UserName)
        values.put(COL_EMAIL, user.Email)
        values.put(COL_PASSWORD, user.Password)

        return db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(user.userID))
    }

    fun deleteUser(user : User){
        val db = this.writableDatabase

        db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(user.userID))
        db.close()
    }

}