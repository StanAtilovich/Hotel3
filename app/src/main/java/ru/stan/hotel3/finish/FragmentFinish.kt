package ru.stan.hotel3.finish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.stan.hotel3.R
import ru.stan.hotel3.databinding.FinishBinding

class FragmentFinish : Fragment() {
    private lateinit var binding: FinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FinishBinding.inflate(inflater, container, false)

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFinish_to_bookingFragment)
        }
        return binding.root
    }
}