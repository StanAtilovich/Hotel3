package ru.stan.hotel3.bookingFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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

        // Показывать запись о клиенте
        binding.buttonAddTourist.setOnClickListener {
            // Обработка нажатия на кнопку "Добавить туриста"
            binding.touristDetailsLayout.visibility = View.VISIBLE
        }

        setupRetrofit()
        phonefocusListener()
        emailfocusListener()
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

    private fun submitForm() {

        binding.phoneContainer.helperText = validPhone()
        binding.textInputLayoutEmail.helperText = validEmail()


        val validEmail = binding.textInputLayoutEmail.helperText == null

        val validPhone = binding.phoneContainer.helperText == null

        if (validEmail &&  validPhone){
            resetForm()
        } else {
            invalidForm()
        }
    }

    private fun invalidForm() {
        var message = ""
        if (binding.phoneContainer.helperText != null)
            message += "\n\nPhone: " + binding.phoneContainer.helperText
        if (binding.textInputLayoutEmail.helperText != null)
            message += "\n\nEmail: " + binding.textInputLayoutEmail.helperText

        AlertDialog.Builder(requireActivity())
            .setTitle("Invalid Form")
            .setMessage(message)

            .setPositiveButton("okey") {_,_ ->
                //сдесь переход либо еще чтото
            }.show()
    }

    private fun resetForm() {
        var message = " Email: " + binding.editTextEmail.text
        message = "\n Phone: " + binding.phoneText.text
        AlertDialog.Builder(requireActivity())
            .setTitle("Form Submited")
            .setMessage(message)

            .setPositiveButton("okey") {_,_ ->
                binding.editTextEmail.text = null
                binding.phoneText.text = null

                binding.phoneContainer.helperText =("сделал")
                binding.textInputLayoutEmail.helperText =("сделал")
            }.show()
    }

    private fun emailfocusListener() {
        binding.editTextEmail.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputLayoutEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.editTextEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email"
        }
        return null
    }

    private fun phonefocusListener() {
        binding.phoneText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.phoneContainer.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String? {
        val phoneText = binding.phoneText.text.toString()

        if (phoneText.length == 10) {
            return null // Возвращаем null, если номер содержит ровно 10 цифр
        }

        if (!phoneText.matches(Regex("[0-9]+"))) {
            return "Must be all digits"
        }

        return "Must be 10 digits"
    }

    private fun setupPhoneMask() {
        binding.phoneText.addTextChangedListener(object : TextWatcher {
            var isFormatting: Boolean = false
            var deletedDigit: Boolean = false

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return

                val phoneText = s.toString()
                val formattedPhone = StringBuilder()

                if (phoneText.startsWith("+7")) {
                    formattedPhone.append("+7 ")
                    if (phoneText.length > 2) {
                        formattedPhone.append("(")
                    }
                    if (phoneText.length > 5) {
                        formattedPhone.append(phoneText.substring(2, 5))
                        formattedPhone.append(") ")
                    }
                    if (phoneText.length > 8) {
                        formattedPhone.append(phoneText.substring(5, 8))
                        formattedPhone.append("-")
                    }
                    if (phoneText.length > 10) {
                        formattedPhone.append(phoneText.substring(8, 10))
                        formattedPhone.append("--")
                    }
                    if (phoneText.length > 12) {
                        formattedPhone.append(phoneText.substring(10, 12))
                    }
                } else {
                    // Handle other cases here
                }

                isFormatting = true
                s?.replace(0, s.length, formattedPhone)
                isFormatting = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                deletedDigit = count > after
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }
        })
    }

}