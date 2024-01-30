package ru.stan.hotel3.data

data class NumberData(
    val rooms: List<Room>
)

data class Room(
    val id: Int,
    val image_urls: List<String>,
    val name: String,
    val peculiarities: List<String>,
    val price: Int,
    val price_per: String
)