package com.example.whiterabbitapp.ui.employeDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.networkmodule.model.ResponseItem
import com.example.networkmodule.network.Resources
import com.example.networkmodule.usecase.EmployeeUseCase
import com.example.whiterabbitapp.base.ViewState
import com.example.whiterabbitapp.util.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val getEmployee: EmployeeUseCase
) : ViewModel() {
    private val _viewState = MutableLiveData<ViewState>(ViewState.Idle)
    val viewState = _viewState.toLiveData()

    private val _employeList = MutableLiveData<List<ResponseItem>>()
    val employeeList = _employeList.toLiveData()
    private val _setError = MutableLiveData<String>()
    val setError = _setError.toLiveData()


    fun getEmployeeList() {
        getEmployee().onEach {
            _viewState.postValue(ViewState.Loading)
            when (it) {
                is Resources.Success -> {
                    _employeList.postValue(it.data)
                    _viewState.postValue(ViewState.Success())
                }
                is Resources.Error -> {
                    _setError.postValue(it.message)
                    _viewState.postValue(ViewState.Error())

                }
                is Resources.Loading -> {
                    _viewState.postValue(ViewState.Loading)
                }
            }
        }
    }

}

