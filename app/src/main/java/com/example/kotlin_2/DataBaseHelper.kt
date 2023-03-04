/*import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.kotlin_2.data.model.GoalItem
import com.example.kotlin_2.data.model.HistoryItem
import java.time.LocalDateTime

val DATABASENAME = "Steptracker"
val TABLENAME = "Goals"
val TABLENAME_HISTORY = "History"
val COL_ID = "ID"
val COL_GOAL_NAME = "name"
val COL_GOAL_ACTIVE = "active"
val COL_GOAL_STEPS = "steps"
val COL_HISTORY_NAME = "name"
val COL_HISTORY_STEPS = "steps"
val COL_HISTORY_DATE = "date"

//Database from https://www.tutorialspoint.com/how-to-use-a-simple-sqlite-database-in-kotlin-android
class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_GOAL_NAME + " VARCHAR(256)," + COL_GOAL_ACTIVE + " INTEGER," + COL_GOAL_STEPS + " INTEGER)"
        db.execSQL(createTable)
        val createTableHistory = "CREATE TABLE " + TABLENAME_HISTORY + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_HISTORY_DATE + " VARCHAR(256)," + COL_HISTORY_NAME + " VARCHAR(256)," + COL_HISTORY_STEPS + " INTEGER)"
        db.execSQL(createTableHistory)
        print("successfully created tables")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLENAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLENAME_HISTORY);
        onCreate(db);
    }

    fun insertGoal(goalItem: GoalItem) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_GOAL_NAME, goalItem.name)
        contentValues.put(COL_GOAL_ACTIVE, goalItem.active)
        contentValues.put(COL_GOAL_STEPS, goalItem.steps)
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun insertDayStatus(historyItem: HistoryItem) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_HISTORY_NAME, historyItem.name)
        contentValues.put(COL_HISTORY_DATE, historyItem.date.toString())
        contentValues.put(COL_HISTORY_STEPS, historyItem.steps)
        val result = database.insert(TABLENAME_HISTORY, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateGoal(goalItem: GoalItem){
        deleteGoal(goalItem)
        insertGoal(goalItem)
    }

    fun deleteGoal(goalItem: GoalItem){
        val database = this.writableDatabase
        database.delete(TABLENAME, "name=?", arrayOf(goalItem.name))
    }

    @SuppressLint("Range")
    fun readGoals(): MutableList<GoalItem> {
        val list: MutableList<GoalItem> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val goalName = result.getString(result.getColumnIndex(COL_GOAL_NAME))
                val goalSteps = result.getString(result.getColumnIndex(COL_GOAL_STEPS)).toInt()
                val goalActive = result.getString(result.getColumnIndex(COL_GOAL_ACTIVE)).toInt()
                val goalActiveBool = goalActive == 1
                val goalItem = GoalItem(goalName, goalSteps, goalActiveBool)
                list.add(goalItem)
            }
            while (result.moveToNext())
        }
        return list
    }

    @SuppressLint("Range")
    fun readHistory(): MutableList<HistoryItem> {
        val list: MutableList<HistoryItem> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME_HISTORY"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val historyName = result.getString(result.getColumnIndex(COL_HISTORY_NAME))
                val historySteps = result.getString(result.getColumnIndex(COL_HISTORY_STEPS)).toInt()
                val historyDate = LocalDateTime.parse(result.getString(result.getColumnIndex(COL_HISTORY_DATE)))
                val historyItem = HistoryItem(historyDate, historyName, historySteps)

                list.add(historyItem)
            }
            while (result.moveToNext())
        }
        return list
    }

    @SuppressLint("Range")
    fun getActiveGoal(): GoalItem {
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME Where $COL_GOAL_ACTIVE = 1"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {

                val goalName = result.getString(result.getColumnIndex(COL_GOAL_NAME))
                val goalSteps = result.getString(result.getColumnIndex(COL_GOAL_STEPS)).toInt()
                val goalActive = result.getString(result.getColumnIndex(COL_GOAL_ACTIVE)).toInt()
                val goalActiveBool = goalActive == 1
                val goalItem = GoalItem(goalName, goalSteps, goalActiveBool)
                return(goalItem)

        }
        //Toast.makeText(context, "Failed, no result for getting active goal", Toast.LENGTH_SHORT).show()

        return(GoalItem("default", 5000, true))
    }

    fun updateGoals(goalItem: GoalItem) {
        val goals = readGoals()
        for (goal in goals){
            if (goalItem.name == goal.name){
                goal.active = true
                updateGoal(goal)
            } else {
                goal.active = false
                updateGoal(goal)
            }
        }

    }

    fun clearGoals(){
        val goals = readGoals()
        for (goal in goals){
            deleteGoal(goal)
        }
    }

    fun clearDatabase(){
        val db = this.readableDatabase
        var query = "DROP TABLE $TABLENAME"
        db.execSQL(query)
        query = "DROP TABLE $TABLENAME_HISTORY"
        db.execSQL(query)
    }
}*/