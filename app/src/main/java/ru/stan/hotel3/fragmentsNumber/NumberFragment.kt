package ru.stan.hotel3.fragmentsNumber

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import ru.stan.hotel3.R
import ru.stan.hotel3.databinding.FragmentNumberBinding
import ru.stan.hotel3.fragments.HotelViewModel

class NumberFragment : Fragment() {
    private lateinit var binding: FragmentNumberBinding
    private val viewModel: HotelViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.token.observe(viewLifecycleOwner , { token ->
            view.findViewById<TextView>(R.id.token). text = token
        })
    }

    companion object {
        fun newInstance() = NumberFragment()
    }
}