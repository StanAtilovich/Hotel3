package ru.stan.hotel3.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.stan.hotel3.R
import ru.stan.hotel3.adapter.ImagePagerAdapter
import ru.stan.hotel3.api.HotelApi
import ru.stan.hotel3.databinding.FragmentHotelBinding
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.stan.hotel3.data.HotelData


class HotelFragment : Fragment() {
    private val viewModel: HotelViewModel by activityViewModels()
    private lateinit var rootView: View
    private var _binding: FragmentHotelBinding? = null
    private val binding get() = _binding!!
    private lateinit var hotelApi: HotelApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelBinding.inflate(inflater, container, false)
        rootView = binding.root
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
        // Показать ProgressBar при начале загрузки
        binding.progressBar.visibility = View.VISIBLE
        loadDataAndPopulateUI()
    }

    private fun setupRetrofit() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        hotelApi = retrofit.create(HotelApi::class.java)
    }

    private fun loadDataAndPopulateUI() {
        CoroutineScope(Dispatchers.IO).launch {
            val hotel = hotelApi.getAllHotel()
            val peculiarities = hotel.about_the_hotel.peculiarities
            val fullAddress = hotel.adress

            //разделяем адрес на 2 строчки
            val firstPart = fullAddress.split(",")[0]  // Берем первую часть адреса


            //делим peculiarities на 4 части
            val peculiaritiesTextViews = listOf(
                rootView.findViewById<TextView>(R.id.peculiaritiesTextView),
                rootView.findViewById<TextView>(R.id.peculiaritiesTextView2),
                rootView.findViewById<TextView>(R.id.peculiaritiesTextView3),
                rootView.findViewById<TextView>(R.id.peculiaritiesTextView4)
            )

            withContext(Dispatchers.Main) {
                populateUI(hotel, firstPart, peculiarities, peculiaritiesTextViews)
                setupImageSlider(hotel.image_urls)

                // Скрыть ProgressBar после завершения загрузки
                binding.progressBar.visibility = View.GONE



            }
        }
    }

    private fun populateUI(
        hotel: HotelData,
        firstPart: String,
        peculiarities: List<String>,
        peculiaritiesTextViews: List<TextView>
    ) {
        binding.apply {
            idRating.text = hotel.rating.toString()
            idAdress.text = firstPart
            idAdressLine1View.text = hotel.adress
            idRatingName.text = hotel.rating_name
            idminimalPrice.text = "От " + hotel.minimal_price.toString() + " ₽ "
            descriptionTextView.text = hotel.about_the_hotel.description
            idpriceForIt.text = hotel.price_for_it
            //размер текста idminimal prize
            idminimalPrice.textSize = 24f
            idminimalPrice.setTypeface(null, Typeface.BOLD)


            //navigation
            btnRoom.setOnClickListener {
                findNavController().navigate(R.id.action_hotelFragment_to_numberFragment)
                viewModel.token.value = idAdress.text.toString()
            }


            //отображение peculiarities
            val chunkedPeculiarities =
                peculiarities.chunked(peculiarities.size / peculiaritiesTextViews.size)
            for (i in 0 until minOf(
                chunkedPeculiarities.size,
                peculiaritiesTextViews.size
            )) {
                peculiaritiesTextViews[i].text = chunkedPeculiarities[i].joinToString(", ")
            }
        }
    }

    // //отображение картинок
    private fun setupImageSlider(imageUrls: List<String>) {
        val viewPager: ViewPager = rootView.findViewById(R.id.imageViewPager)
        val adapter = ImagePagerAdapter(imageUrls)
        viewPager.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): HotelFragment {
            return HotelFragment()
        }
    }
}

