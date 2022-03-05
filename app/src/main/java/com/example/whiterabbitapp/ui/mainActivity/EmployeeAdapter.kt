package com.example.whiterabbitapp.ui.mainActivity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.networkmodule.database.EmployeeTable
import com.example.networkmodule.model.ResponseItem
import com.example.whiterabbitapp.R
import com.example.whiterabbitapp.databinding.IndiviewBinding
import java.util.*
import kotlin.collections.ArrayList

class EmployeeAdapter(
    private val callBack: EmployeeAdapterCallBack
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>(), Filterable {

    private val item = ArrayList<EmployeeTable>()
    private val itemFilter = ArrayList<EmployeeTable>()

    private var searchName : Boolean=true

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<EmployeeTable>) {
        item.clear()
        itemFilter.clear()
        itemFilter.addAll(list)
        item.addAll(list)
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun restoreAllList() {
        itemFilter.addAll(item)
        notifyDataSetChanged()
    }


    inner class EmployeeViewHolder(private val binding: IndiviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EmployeeTable) {
            with(binding){
                imgProfile.load(item.profile_image?:R.drawable.ic_baseline_account_circle_24){
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_account_circle_24)
                    transformations(CircleCropTransformation())
                }
                tvEmployeeName.text=item.name
                tvEmployeeCompany.text=item.company_name.toString()

                cvRoot.setOnClickListener{
                    callBack.onItemClick(item)
                }
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding =
            IndiviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(itemFilter[position])
    }

    override fun getItemCount(): Int = itemFilter.size

    val filterName = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val localList = ArrayList<EmployeeTable>()
            itemFilter.clear()
            itemFilter.addAll(item)

            if (p0 == null || p0.isEmpty()) {
                localList.addAll(itemFilter)
            } else {
                for (item in itemFilter) {
                    if (item.name != null && item.name?.lowercase(Locale.ENGLISH) != null) {
                        if (item.name!!.lowercase(Locale.ENGLISH)
                                .contains(p0.toString().lowercase(Locale.ENGLISH))
                        ) {
                            localList.add(item)
                        }
                    }
                }
            }

            val filterResults = FilterResults()
            filterResults.values = localList
            return filterResults
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            itemFilter.clear()
            if (p1 != null) {
                itemFilter.addAll(p1.values as Collection<EmployeeTable>)
            }
            notifyDataSetChanged()
        }

    }

    val filterEmail = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val localList = ArrayList<EmployeeTable>()
            itemFilter.clear()
            itemFilter.addAll(item)
            if (p0 == null || p0.isEmpty()) {
                localList.addAll(itemFilter)
            } else {
                for (item in itemFilter) {
                    if (item.email != null && item.email?.lowercase(Locale.ENGLISH) != null) {
                        if (item.email!!.lowercase(Locale.ENGLISH)
                                .contains(p0.toString().lowercase(Locale.ENGLISH))
                        ) {
                            localList.add(item)
                        }
                    }
                }
            }

            val filterResults = FilterResults()
            filterResults.values = localList
            return filterResults
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            itemFilter.clear()
            if (p1 != null) {
                itemFilter.addAll(p1.values as Collection<EmployeeTable>)
            }
            notifyDataSetChanged()
        }

    }

    override fun getFilter(): Filter {
        return if (searchName) filterName else filterEmail
    }

    fun setSearch(bol:Boolean){
        searchName=bol

    }


}

interface EmployeeAdapterCallBack{
    fun onItemClick(item: EmployeeTable)
}