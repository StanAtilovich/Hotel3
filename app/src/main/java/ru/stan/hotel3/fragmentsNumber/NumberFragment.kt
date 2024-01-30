package ru.stan.hotel3.fragmentsNumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
import ru.stan.hotel3.databinding.FragmentNumberBinding
import ru.stan.hotel3.fragments.HotelViewModel

class NumberFragment : Fragment() {
    private lateinit var binding: FragmentNumberBinding
    private val viewModel: HotelViewModel by activityViewModels()
    private lateinit var adapter: NumberAdapter
    private lateinit var hotelApi: HotelApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNumberBinding.inflate(inflater, container, false)
        adapter = NumberAdapter()
        binding.rcViewHotel.layoutManager = LinearLayoutManager(requireContext())
        binding.rcViewHotel.adapter = adapter
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.token.observe(viewLifecycleOwner) { token ->
            view.findViewById<TextView>(R.id.intent).text = token
        }
        setupRetrofit()
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
            val number = hotelApi.getRoom()
            withContext(Dispatchers.Main){
                binding.apply {
                    adapter.submitList(number.rooms)
                }
            }
        }
    }
}