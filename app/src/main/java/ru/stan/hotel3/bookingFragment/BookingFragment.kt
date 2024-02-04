package ru.stan.hotel3.bookingFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.stan.hotel3.R
import ru.stan.hotel3.adapter.NumberAdapter
import ru.stan.hotel3.api.HotelApi
import ru.stan.hotel3.databinding.FragmentBookingBinding
import ru.stan.hotel3.databinding.FragmentNumberBinding
import ru.stan.hotel3.fragmentsNumber.NumberViewModel

class BookingFragment : Fragment() {
    private lateinit var binding: FragmentBookingBinding
    private lateinit var hotelApi: HotelApi
    private val bookingViewModel: BookingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingBinding.inflate(inflater, container, false)

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
        setupRetrofit()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRetrofit() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        hotelApi = retrofit.create(HotelApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val booking = hotelApi.getBooking()
            withContext(Dispatchers.Main) {
                binding.idAdressLine1View.text = booking.hotel_adress
                binding.idRating.text = booking.horating.toString()
                binding.idRatingName.text = booking.rating_name
                binding.departure.text = booking.departure
                binding.arrivalCountry.text = booking.arrival_country
                binding.numberOfNights.text = booking.number_of_nights.toString() + " ночей"
                binding.room.text = booking.room
                binding.nutrion.text = booking.nutrition
                binding.dataStartStop.text =
                    booking.tour_date_start + " - " + booking.tour_date_stop
                //разделение до запятой и отображение
                val parts = booking.hotel_adress.split(",")
                val addressPart = parts.firstOrNull()
                withContext(Dispatchers.Main) {
                    binding.idAdress.text = addressPart
                    binding.hotelAdress.text = addressPart
                }
            }
        }
    }

}