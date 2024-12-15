package com.example.voluntariadocomunitario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OpportunitiesAdapter(private var opportunities: List<VoluntaryAct>) :
    RecyclerView.Adapter<OpportunitiesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_opportunity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = opportunities[position].title
    }

    override fun getItemCount() = opportunities.size

    fun updateData(newOpportunities: List<VoluntaryAct>) {
        opportunities = newOpportunities
        notifyDataSetChanged()
    }
}