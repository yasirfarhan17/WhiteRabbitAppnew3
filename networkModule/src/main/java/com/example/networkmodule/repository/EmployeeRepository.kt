package com.example.networkmodule.repository

import com.example.networkmodule.model.ResponseItem
import retrofit2.Response

interface EmployeeRepository {

    suspend fun getEmployeeData() : List<ResponseItem>
}