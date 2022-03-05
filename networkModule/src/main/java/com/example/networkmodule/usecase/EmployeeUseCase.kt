package com.example.networkmodule.usecase

import android.util.Log
import com.example.networkmodule.model.ResponseItem
import com.example.networkmodule.network.Resources
import com.example.networkmodule.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject

class EmployeeUseCase  @Inject constructor(
    private val repository : EmployeeRepository
){
    operator fun invoke() : Flow<Resources<List<ResponseItem>>> = flow{
        try{
            emit(Resources.Loading())
            val result = repository.getEmployeeData()
            emit(Resources.Success(result))

        }catch (e: HttpException){
            emit(Resources.Error(e.localizedMessage?:"Something went wrong"))
        }catch (e: IOException){
            emit(Resources.Error("Couldn't connect to server"))
        }
    }
}