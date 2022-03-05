package com.example.networkmodule.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [EmployeeTable::class], version = EmployeeDataBase.VERSION)
abstract class EmployeeDataBase : RoomDatabase() {

    abstract val employeeDao: EmployeeDao

    companion object {
        const val NAME = "employee_date_base"
        const val VERSION = 2

        @Volatile
        private var INSTANCE: EmployeeDataBase? = null

        fun getInstance(context: Context): EmployeeDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    EmployeeDataBase::class.java,
                    NAME
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}