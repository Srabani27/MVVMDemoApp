package com.example.mvvmdemoapp.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmdemoapp.R
import com.example.mvvmdemoapp.model.LocationViewModel
import com.example.mvvmdemoapp.response.LocationRepository
import com.example.mvvmdemoapp.response.LocationViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import org.json.JSONException
import org.json.JSONObject

class LocationMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var viewModel: LocationViewModel
    private lateinit var routeButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_map, container, false)

        viewModel = ViewModelProvider(this, LocationViewModelFactory(LocationRepository()))
            .get(LocationViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        routeButton = view.findViewById(R.id.buttonShowRoute)
        routeButton.setOnClickListener {
            if (isNetworkAvailable()) {
                fetchAndDisplayRoute()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

    }

    private fun fetchAndDisplayRoute() {
        val jsonData = """
        {
          "locations": [
            {
              "latitude": 37.7749,
              "longitude": -122.4194
            },
            {
              "latitude": 34.0522,
              "longitude": -118.2437
            },
            {
              "latitude": 41.8781,
              "longitude": -87.6298
            },
            {
              "latitude": 40.7128,
              "longitude": -74.0060
            },
            {
              "latitude": 25.7617,
              "longitude": -80.1918
            },
            {
              "latitude": 39.9526,
              "longitude": -75.1652
            },
            {
              "latitude": 47.6062,
              "longitude": -122.3321
            },
            {
              "latitude": 36.7783,
              "longitude": -119.4179
            }
            
          ]
        }
    """
        val points = parseLocationPoints(jsonData)

        if (points.isNotEmpty()) {
            val polylineOptions = PolylineOptions()
            polylineOptions.addAll(points)
            map.addPolyline(polylineOptions)

            val builder = LatLngBounds.builder()
            points.forEach { point ->
                builder.include(point)
            }
            val bounds = builder.build()
            val padding = 100 // Adjust padding as needed
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            map.moveCamera(cameraUpdate)
        } else {
            Toast.makeText(
                requireContext(),
                "No location points found.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun parseLocationPoints(jsonData: String): List<LatLng> {
        val points = mutableListOf<LatLng>()
        try {
            val jsonObject = JSONObject(jsonData)
            val locationsArray = jsonObject.getJSONArray("locations")
            for (i in 0 until locationsArray.length()) {
                val locationObject = locationsArray.getJSONObject(i)
                val latitude = locationObject.getDouble("latitude")
                val longitude = locationObject.getDouble("longitude")
                val latLng = LatLng(latitude, longitude)
                points.add(latLng)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return points
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
