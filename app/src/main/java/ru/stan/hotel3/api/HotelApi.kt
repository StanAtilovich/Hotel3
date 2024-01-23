package ru.stan.hotel3.api

import retrofit2.http.GET
import ru.stan.hotel3.data.HotelData

interface HotelApi {
    @GET("v3/d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getAllHotel(): HotelData
}