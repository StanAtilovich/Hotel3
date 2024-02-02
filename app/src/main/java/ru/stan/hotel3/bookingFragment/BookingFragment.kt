package ru.stan.hotel3.bookingFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.stan.hotel3.R
import ru.stan.hotel3.adapter.NumberAdapter
import ru.stan.hotel3.api.HotelApi
import ru.stan.hotel3.databinding.FragmentBookingBinding
import ru.stan.hotel3.databinding.FragmentNumberBinding

class BookingFragment : Fragment() {
    private lateinit var binding: FragmentBookingBinding
    private lateinit var hotelApi: HotelApi
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

        return binding.root
    }

}