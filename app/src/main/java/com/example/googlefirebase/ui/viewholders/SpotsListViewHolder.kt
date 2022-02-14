package com.example.googlefirebase.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.googlefirebase.R
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot
import com.example.googlefirebase.util.onSpotClickedListener

class SpotsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val spotNameTextView: TextView = itemView.findViewById(R.id.spot_name_text_view)
    val spotCityTextView: TextView = itemView.findViewById(R.id.spot_city_text_view)

    fun bind(spot: Spot, onSpotClickedListener: onSpotClickedListener) {
        spotNameTextView.text = spot.name
        spotCityTextView.text = spot.city

        itemView.setOnClickListener {
            onSpotClickedListener.onItemClicked(spot)
        }
    }
}
