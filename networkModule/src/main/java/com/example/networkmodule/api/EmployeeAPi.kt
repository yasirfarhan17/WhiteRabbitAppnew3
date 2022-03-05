package com.example.networkmodule.api

import com.example.networkmodule.model.ResponseItem
import retrofit2.http.GET

interface EmployeeAPi {

    @GET(EndPoint.GET_Employee)
    suspend fun getEmployee():List<ResponseItem>
}