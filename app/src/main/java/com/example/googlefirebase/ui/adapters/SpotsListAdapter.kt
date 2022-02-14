package com.example.googlefirebase.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.googlefirebase.R
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot
import com.example.googlefirebase.ui.viewholders.SpotsListViewHolder
import com.example.googlefirebase.util.onSpotClickedListener

class SpotsListAdapter(context: Context, onSpotClickedListener: onSpotClickedListener) :
    RecyclerView.Adapter<SpotsListViewHolder>() {

    private val context = context
    private var listOfSpots = ArrayList<Spot>()
    private val onSpotClickedListener = onSpotClickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotsListViewHolder {
        val spotsListViewHolder =
            LayoutInflater.from(context).inflate(R.layout.list_of_spots_view_item, parent, false)

        return SpotsListViewHolder(spotsListViewHolder)
    }

    override fun onBindViewHolder(holder: SpotsListViewHolder, position: Int) {

        // Get an instance of a spot
        val spot = listOfSpots.get(position)
        holder.bind(spot, onSpotClickedListener)
    }

    override fun getItemCount(): Int {
        return listOfSpots.size
    }

    fun setData(listOfSpots: ArrayList<Spot>) {

        if (listOfSpots != null) {

            this.listOfSpots.clear()
            this.listOfSpots.addAll(listOfSpots)
            notifyDataSetChanged()
        } else {
            this.listOfSpots = listOfSpots
        }
    }

}