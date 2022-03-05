package com.example.whiterabbitapp.ui.mainActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.networkmodule.database.EmployeeTable
import com.example.whiterabbitapp.R
import com.example.whiterabbitapp.databinding.ActivityMainBinding
import com.example.whiterabbitapp.ui.employeDetail.EmployeeDetailActivity
import com.example.whiterabbitapp.util.UiUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() ,EmployeeAdapterCallBack{
    private lateinit var binding: ActivityMainBinding
    private val _viewModel: MainViewModel by viewModels()

    private lateinit var uiUtil: UiUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        uiUtil = UiUtil(this)
        initUi()
        addListener()
        addObserver()


    }

    private fun initUi() {
        with(binding) {
            rvHome.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            rvHome.adapter = EmployeeAdapter(this@MainActivity)
            appBarLayout.tittle.text = "White Rabbit"


            _viewModel.getEmployeeList()


        }
    }

    fun restoreAdapter(){
        (binding.rvHome.adapter as EmployeeAdapter).restoreAllList()
        binding.appBarLayout.clSearch.visibility = View.GONE
        binding.appBarLayout.clNormal.visibility = View.VISIBLE
    }

    private fun addListener() {

        binding.appBarLayout.imgSearch.setOnClickListener {
            showAlertDialog()

        }
        binding.appBarLayout.imgCut.setOnClickListener {
            restoreAdapter()
        }

        binding.appBarLayout.imgNormal.setOnClickListener {
            restoreAdapter()
        }

        binding.appBarLayout.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                (binding.rvHome.adapter as EmployeeAdapter).filter.filter(p0.toString());
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

    }

    private fun addObserver() {
        observeList()
        observeError()
    }


    private fun observeError() {
        _viewModel.setError.observe(this) {
            uiUtil.showToast(it.toString())
        }
    }

    private fun observeList() {
        _viewModel.employeeList.observe(this) {

            (binding.rvHome.adapter as EmployeeAdapter).submitList(list = it as ArrayList<EmployeeTable>)
        }
    }

    private fun showAlertDialog() {
        var alertDialog = AlertDialog.Builder(this);
        alertDialog.setTitle("AlertDialog");
        var items = arrayOf("Email", "Name")
        var checkedItem = 1;
        alertDialog.setSingleChoiceItems(
            items, checkedItem
        ) { p0, position ->
            (binding.rvHome.adapter as EmployeeAdapter).setSearch(items[position] == "Name")
            binding.appBarLayout.clSearch.visibility = View.VISIBLE
            binding.appBarLayout.clNormal.visibility = View.GONE

            p0.dismiss()
        }

        val alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    override fun onItemClick(item: EmployeeTable) {
        val intent= Intent(this,EmployeeDetailActivity::class.java)
        intent.putExtra("obj",item)
        startActivity(intent)
    }

}