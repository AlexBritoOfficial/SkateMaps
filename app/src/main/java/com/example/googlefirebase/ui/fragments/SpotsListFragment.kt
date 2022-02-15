package com.example.googlefirebase.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlefirebase.R
import com.example.googlefirebase.databinding.FragmentSpotsBinding
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot
import com.example.googlefirebase.signin_registration_feature.viewmodel.SpotsViewModel
import com.example.googlefirebase.signin_registration_feature.viewmodel.SpotsViewModelFactory
import com.example.googlefirebase.ui.adapters.SpotsListAdapter
import com.example.googlefirebase.util.onSpotClickedListener
import com.google.android.material.divider.MaterialDividerItemDecoration

class SpotsListFragment() : Fragment(), onSpotClickedListener {

    /** Class Properties **/
    private val TAG = "SpotsListFragment"
    private val SPOT_PARCELABLE_TAG = "SpotParcelable"
    private lateinit var fragmentSpotsBinding: FragmentSpotsBinding
    private lateinit var spotsViewModelFactory: SpotsViewModelFactory
    private lateinit var spotsViewModel: SpotsViewModel
    private lateinit var spotsRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var dividerItemDecoration: MaterialDividerItemDecoration
    private lateinit var spotsListAdapter: SpotsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Set AppBarConfiguration to match fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle("Spots")

        // Create an instance of SpotsViewModelFactory
        spotsViewModelFactory = SpotsViewModelFactory(requireContext())
        spotsViewModel =
            ViewModelProvider(this, spotsViewModelFactory).get(SpotsViewModel::class.java)

        //Initialize FragmentSpotsBinding object
        fragmentSpotsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_spots, container, false)

        // Initialize the Spots RecyclerView object
        spotsRecyclerView = fragmentSpotsBinding.spotsRecyclerView

        // Initialize the LinearLayoutManager object
        linearLayoutManager = LinearLayoutManager(requireContext())

        // Set the Spots RecyclerView object LayoutManager
        spotsRecyclerView.layoutManager = linearLayoutManager

        // Initialize SpotsListAdapter
        spotsListAdapter = SpotsListAdapter(requireContext(), this)

        // Set adapter for SpotsRecyclerView
        spotsRecyclerView.adapter = spotsListAdapter

        // Set an observer to observe mutableLiveSpotsAccessor
        spotsViewModel.mutableLiveSpotsAccessor.observe(viewLifecycleOwner, Observer { t ->
            // Initialize the SpotsListAdapter object
            spotsListAdapter.setData(t)
        })


        return fragmentSpotsBinding.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onItemClicked(spot: Spot?) {

        println("You clicked ${spot!!.name}")

        // Create a Bundle object to pass information to Spot Description Fragment
        val bundle = Bundle()

        // Place the Parcelable into the bundle.
        bundle.putParcelable(SPOT_PARCELABLE_TAG, spot)

        // Navigate to the Spots Description Fragment
        findNavController().navigate(
            R.id.action_recycler_view_spots_list_to_spots_description_fragment,
            bundle
        )
    }

}