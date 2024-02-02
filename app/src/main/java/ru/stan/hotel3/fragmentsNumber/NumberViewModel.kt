package ru.stan.hotel3.fragmentsNumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.stan.hotel3.data.Room

class NumberViewModel : ViewModel() {
    private val _roomList = MutableLiveData<List<Room>>()
    val roomList: LiveData<List<Room>> get() = _roomList

    fun setRoomList(rooms: List<Room>) {
        _roomList.value = rooms
    }
}