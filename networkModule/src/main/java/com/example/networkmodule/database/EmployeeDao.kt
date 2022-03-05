package com.example.networkmodule.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface EmployeeDao {

    @Insert
    suspend fun insertEmployee(employee: List<EmployeeTable>)

    @Query("delete from employee where 1")
    suspend fun clear()

    @Query("select * from employee")
    suspend fun getEmployee(): List<EmployeeTable>
}