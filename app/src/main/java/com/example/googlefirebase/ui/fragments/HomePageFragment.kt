package com.example.googlefirebase.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.convertTo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.googlefirebase.R
import com.example.googlefirebase.databinding.AddSkateSpotDialogBinding
import com.example.googlefirebase.databinding.FragmentHomePageBinding
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot
import com.example.googlefirebase.signin_registration_feature.viewmodel.HomePageViewModel
import com.example.googlefirebase.signin_registration_feature.viewmodel.HomePageViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [HomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePageFragment : Fragment(), OnMapReadyCallback{

    /** Class Properties **/
    private lateinit var homePageViewModelFactory: HomePageViewModelFactory
    private lateinit var homePageViewModel: HomePageViewModel
    private lateinit var googleMap : GoogleMap
    private lateinit var fragmentHomePageBinding: FragmentHomePageBinding
    private lateinit var placesClient: PlacesClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var addSpotFloatingActionButton: FloatingActionButton
    private lateinit var skateSpotNameDialogEditText: EditText
    private lateinit var skateSpotCityDialogEditText: EditText
    private lateinit var skateSpotStateDialogEditText: EditText


    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private var lastKnownLocation: Location? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Create an instance of HomePageViewModelFactory
        homePageViewModelFactory = HomePageViewModelFactory(requireContext())

        // Create an instance of HomePageViewModel
        homePageViewModel = ViewModelProvider(this, homePageViewModelFactory).get(HomePageViewModel::class.java)

        // Inflate the layout for this fragment
        fragmentHomePageBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home_page, container, false)

        addSpotFloatingActionButton = fragmentHomePageBinding.floatingActionButton

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        // Construct a PlacesClient
        Places.initialize(context, getString(R.string.api_key))
        placesClient = Places.createClient(context)

        // Construct a FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        addSpotFloatingActionButton.setOnClickListener {
            openAddSkateSpotDialogBox()

        }


        return fragmentHomePageBinding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap  = googleMap

        homePageViewModel.mutableLiveSpotsAccessor.observe(viewLifecycleOwner, Observer {
                it ->
            it.forEach(){
                val latLng = LatLng(it.spotLatLng!!.latitude, it.spotLatLng!!.longitude)
                googleMap.addMarker(MarkerOptions().position(latLng).title(it.name))
            }

        })

        // Prompt the user for permission.
        getLocationPermission()

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI()

        // Get the current location of the device and set the position of the map.
        getDeviceLocation()

    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            googleMap?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        googleMap?.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                        googleMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (googleMap == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                googleMap?.isMyLocationEnabled = true
                googleMap?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                googleMap?.isMyLocationEnabled = false
                googleMap?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    companion object {
        private val TAG = "HomePageFragment"
        private const val DEFAULT_ZOOM = 5
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        // Keys for storing activity state.
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

        // Used for selecting the current place.
        private const val M_MAX_ENTRIES = 5
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }


    // Function that will build the Add Skate Spot Dialog Box
    fun openAddSkateSpotDialogBox(){
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.add_skate_spot_dialog,null)

        builder!!.setTitle("Add Skate Spot")
        builder!!.setView(view).setPositiveButton("Submit", DialogInterface.OnClickListener { dialogInterface, i ->

            // Get Device Location
            getDeviceLocation()
            val latitude = lastKnownLocation!!.latitude
            val longitude = lastKnownLocation!!.longitude


            skateSpotNameDialogEditText = view.findViewById(R.id.spot_name)
            val spotName = skateSpotNameDialogEditText.text.toString()

            skateSpotCityDialogEditText = view.findViewById(R.id.spot_city)
            val spotCity = skateSpotCityDialogEditText.text.toString()

            skateSpotStateDialogEditText = view.findViewById(R.id.spot_state)
            val spotState = skateSpotStateDialogEditText.text.toString()

            homePageViewModel.insertSpotIntoGoogleFireStore(latitude,longitude,spotName,spotCity,spotState)

        }).setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                builder.create().hide()
        })

        val dialog: AlertDialog? = builder?.create()
        dialog!!.show()
    }

}