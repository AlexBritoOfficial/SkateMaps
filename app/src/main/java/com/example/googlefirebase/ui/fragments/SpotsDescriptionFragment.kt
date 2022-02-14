package com.example.googlefirebase.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.googlefirebase.R
import com.example.googlefirebase.databinding.FragmentSpotDescriptionRecyclerViewItemBinding
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot

class SpotsDescriptionFragment() : Fragment() {


    /** Class Members **/
    private val TAG = "SpotsDescriptionFragment"
    private val SPOT_PARCELABLE_TAG = "SpotParcelable"
    private lateinit var fragmentSpotsDescriptionRecyclerView: FragmentSpotDescriptionRecyclerViewItemBinding
    private lateinit var spotDescriptionNameTextView: TextView
    private lateinit var spotDescriptionCityTextView: TextView
    private lateinit var spotDescriptionStateTextView: TextView
    private lateinit var spotDescriptionTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val bundle = requireArguments()
       val spot: Spot? = bundle.getParcelable(SPOT_PARCELABLE_TAG)

        // Load FragmentSpotDescriptionRecyclerViewItemBinding object
        fragmentSpotsDescriptionRecyclerView = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_spot_description_recycler_view_item,
            container,
            false
        )

        // Load spotDescriptionNameTextView object
        spotDescriptionNameTextView = fragmentSpotsDescriptionRecyclerView.spotDescriptionName

        // Apply Data Binding to spotDescriptionNameTextView
        spotDescriptionNameTextView.apply {
            text = spot!!.name
        }

        // Load spotDescriptionCityTextView object
        spotDescriptionCityTextView = fragmentSpotsDescriptionRecyclerView.spotDescriptionCity

        // Apply Data Binding to spotDescriptionCityTextView
        spotDescriptionCityTextView.apply {
            text = spot!!.city
        }

        // Load spotDescriptionStateTextView object
        spotDescriptionStateTextView = fragmentSpotsDescriptionRecyclerView.spotDescriptionState

        // Apply Data Binding to spotDescriptionStateTextView
        spotDescriptionStateTextView.apply {
            text = spot!!.state
        }

        // Load spotDescriptionTextView
        spotDescriptionTextView = fragmentSpotsDescriptionRecyclerView.spotDescriptionDetails


        return fragmentSpotsDescriptionRecyclerView.root
    }
}