package com.example.networkmodule.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "employee")
data class EmployeeTable(
    @PrimaryKey
    val id: String,
    val profile_image: String? = null,
    val website: String? = null,
    val phone: String? = null,
    val name: String? = null,
    val email: String? = null,
    val username: String? = null,
    val company_name: String? = null,
    val zipcode: String? = null,

    val suite: String? = null,
    val city: String? = null,
    val street: String? = null
) : Parcelable
/*EmployeeTable(id=1, profile_image=https://randomuser.me/api/portraits/men/1.jpg,
website=hildegard.org,
 phone=null, name=Leanne Graham,
 email=Sincere@april.biz,
 username=Bret,
 company_name=Romaguera-Crona,
  zipcode=92998-3874,
  suite=Apt. 556,
   city=Gwenborough,
   street=Kulas Light)*/
/*
* (Profile Image, Name, User Name, Email address, Address, Phone,
Website, Company Details)
* */