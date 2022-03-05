package com.example.networkmodule.repository

import com.example.networkmodule.api.EmployeeAPi
import com.example.networkmodule.model.ResponseItem
import retrofit2.Response
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val api:EmployeeAPi
) :EmployeeRepository{
    override suspend fun getEmployeeData(): List<ResponseItem> =api.getEmployee()

}