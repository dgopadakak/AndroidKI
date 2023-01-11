package com.example.androidki.forRecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidki.R

class CustomRecyclerAdapterForDirections(private val names: List<String>,
                                         private val numbers: List<String>):
    RecyclerView.Adapter<CustomRecyclerAdapterForDirections.MyViewHolder>()
{
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewName: TextView = itemView.findViewById(R.id.textViewDirectionNameItem)
        val textViewNum: TextView = itemView.findViewById(R.id.textViewStudentsNumItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_directions_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.textViewName.text = names[position]
        holder.textViewNum.text = numbers[position]
    }

    override fun getItemCount() = names.size
}