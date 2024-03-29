package ru.stan.hotel3.api

import retrofit2.http.GET
import ru.stan.hotel3.data.BookingData
import ru.stan.hotel3.data.HotelData
import ru.stan.hotel3.data.NumberData

interface HotelApi {
    @GET("v3/d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getAllHotel(): HotelData

    @GET("v3/8b532701-709e-4194-a41c-1a903af00195")
    suspend fun getRoom(): NumberData

    @GET("v3/63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun getBooking(): BookingData
}