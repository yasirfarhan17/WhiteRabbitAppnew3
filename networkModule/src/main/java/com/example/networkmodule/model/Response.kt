package com.example.networkmodule.model

import android.os.Parcelable
import com.example.networkmodule.database.EmployeeTable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Address(
	val zipcode: String? = null,
	val geo: Geo? = null,
	val suite: String? = null,
	val city: String? = null,
	val street: String? = null
) : Parcelable

@Parcelize
data class ResponseItem(
	val profile_image: String? = null,
	val website: String? = null,
	val address: Address? = null,
	val phone: String? = null,
	val name: String? = null,
	val company: Company? = null,
	val id: Int? = null,
	val email: String? = null,
	val username: String? = null
) : Parcelable
{
	fun toEmployeeTable(): EmployeeTable {
		val obj = EmployeeTable(
			id = this.id.toString(),
			profile_image = this.profile_image,
			website = this.website,
			phone = this.phone,
			name = this.name,
			email = this.email, username = this.username,
			company_name = this.company?.name,
			zipcode = this.address?.zipcode,
			suite = this.address?.suite, city = this.address?.city, street = this.address?.street
		)
		return obj
	}
}

@Parcelize
data class Geo(
	val lng: String? = null,
	val lat: String? = null
) : Parcelable

@Parcelize
data class Company(
	val bs: String? = null,
	val catchPhrase: String? = null,
	val name: String? = null
) : Parcelable
