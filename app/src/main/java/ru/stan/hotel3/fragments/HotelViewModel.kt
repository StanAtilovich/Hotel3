package ru.stan.hotel3.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HotelViewModel : ViewModel() {
    val token = MutableLiveData<String>()
}