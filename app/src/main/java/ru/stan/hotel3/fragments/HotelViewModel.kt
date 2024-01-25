package ru.stan.hotel3.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.stan.hotel3.api.HotelApi
import ru.stan.hotel3.data.HotelData

class HotelViewModel() : ViewModel() {
    val token = MutableLiveData<String>()

    private val _currentIndex = MutableLiveData<Int>()
    val currentIndex: LiveData<Int> get() = _currentIndex

    private val _hotelData = MutableLiveData<HotelData>()
    val hotelData: LiveData<HotelData> get() = _hotelData


    fun loadDataAndPopulateUI(hotelApi: HotelApi) {
        viewModelScope.launch {
            val hotel = hotelApi.getAllHotel()
            _hotelData.value = hotel

        }
    }
    fun setCurrentIndex(index: Int) {
        _currentIndex.value = index
    }
}

