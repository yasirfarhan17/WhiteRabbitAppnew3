package com.example.whiterabbitapp.ui.mainActivity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkmodule.database.EmployeeDao
import com.example.networkmodule.database.EmployeeDataBase
import com.example.networkmodule.database.EmployeeTable
import com.example.networkmodule.model.ResponseItem
import com.example.networkmodule.network.Resources
import com.example.networkmodule.usecase.EmployeeUseCase
import com.example.whiterabbitapp.base.ViewState
import com.example.whiterabbitapp.util.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getEmployee: EmployeeUseCase,
    private val employeeDao:EmployeeDao
) : ViewModel() {
    private val _viewState = MutableLiveData<ViewState>(ViewState.Idle)
    val viewState = _viewState.toLiveData()

    private val _employeList = MutableLiveData<List<EmployeeTable>>()
    val employeeList = _employeList.toLiveData()
    private val _setError = MutableLiveData<String>()
    val setError = _setError.toLiveData()


    fun getEmployeeList() {
        getEmployee().onEach {
            _viewState.postValue(ViewState.Loading)
            when (it) {
                is Resources.Success -> {
//                    _employeList.postValue(it.data)
//                    _viewState.postValue(ViewState.Success())
                    it.data?.let { it1 -> insertItemToDB(it1) }
                }
                is Resources.Error -> {
                    _setError.postValue(it.message)
                    _viewState.postValue(ViewState.Error())

                }
                is Resources.Loading -> {
                    _viewState.postValue(ViewState.Loading)
                }
            }
        }.launchIn(viewModelScope+ exceptionHandler)
    }

    private  val exceptionHandler= CoroutineExceptionHandler{_,exception ->
        handleFailure(throwable =exception)

    }

    private fun handleFailure(throwable: Throwable ?) {

        _viewState.postValue(ViewState.Error(throwable))
        Log.e("NETWORK_ERROR",throwable.toString())

    }

    fun insertItemToDB(responseItem : List<ResponseItem>){

        viewModelScope.launch(Dispatchers.IO) {

            employeeDao.clear()
            val list=responseItem.map { it.toEmployeeTable() }
            val dao=employeeDao.insertEmployee(list)
            val result=employeeDao.getEmployee()
            _employeList.postValue(result)
            _viewState.postValue(ViewState.Success())
        }

    }

}

