package com.example.workmanagerassignment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagerassignment.databinding.ItemUserDataBinding
import com.example.workmanagerassignment.models.UserDataItem

class Adapter(private val list : ArrayList<UserDataItem>) : RecyclerView.Adapter<Adapter.ViewHolder>(){

    class ViewHolder(val binding : ItemUserDataBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(ItemUserDataBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = list[position]

       holder.binding.apply {
           userId.text = "user id : ${item.userId}"
           body.text   = "body : ${item.body}"
           title.text  = "title : ${item.title}"
       }

    }

    override fun getItemCount() = list.size
}