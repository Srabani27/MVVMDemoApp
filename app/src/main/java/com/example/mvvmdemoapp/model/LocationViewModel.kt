package com.example.mvvmdemoapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmdemoapp.response.LocationPoint
import com.example.mvvmdemoapp.response.LocationRepository

class LocationViewModel(private val repository: LocationRepository) : ViewModel() {

    private val _locationPoints = MutableLiveData<List<LocationPoint>>()
    val locationPoints: LiveData<List<LocationPoint>>
        get() = _locationPoints

    fun getLocationPoints() {
        repository.getLocationPoints { points ->
            _locationPoints.postValue(points)
        }
    }
}