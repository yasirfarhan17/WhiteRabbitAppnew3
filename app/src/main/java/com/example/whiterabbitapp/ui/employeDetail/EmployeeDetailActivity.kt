package com.example.whiterabbitapp.ui.employeDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import coil.load
import coil.transform.CircleCropTransformation
import com.example.networkmodule.database.EmployeeTable
import com.example.whiterabbitapp.R
import com.example.whiterabbitapp.databinding.ActivityEmployeeDetailBinding
import com.example.whiterabbitapp.databinding.ActivityMainBinding

class EmployeeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeDetailBinding
    var obj :EmployeeTable?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding= DataBindingUtil.setContentView(this, R.layout.activity_employee_detail)
        obj=intent.getParcelableExtra("obj")

        with(binding){
            imgProfile.load(obj?.profile_image?:R.drawable.ic_baseline_account_circle_24){
                crossfade(true)
                placeholder(R.drawable.ic_baseline_account_circle_24)
            }
            tvName.text=obj?.name.toString()
            tvEmail.text=obj?.email.toString()
            tvPhoneNo.text=obj?.phone.toString()
            tvCompanyName.text=obj?.company_name.toString()
            tvAddress.text=obj?.street.toString()+" "+obj?.city+" "+obj?.zipcode
            imgBack.setOnClickListener{
                onBackPressed()
            }
        }

    }
}