package ru.stan.hotel3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.stan.hotel3.R
import ru.stan.hotel3.data.Room
import ru.stan.hotel3.databinding.NumberItemBinding
import ru.stan.hotel3.fragmentsNumber.NumberFragment

class NumberAdapter(private val fragment: NumberFragment) :
    ListAdapter<Room, NumberAdapter.Holder>(Comparator()) {
    class Holder(view: View, private val fragment: NumberFragment) : RecyclerView.ViewHolder(view) {
        private val binding = NumberItemBinding.bind(view)

        fun bind(room: Room) = with(binding) {
            nameNumer.text = room.name
            priceNumber.text = "От " + room.price.toString() + " ₽ "
            pricePerNumber.text = room.price_per
            val peculiatiesList = room.peculiarities.toString().split(", ")
            if (peculiatiesList.size >= 1) {
                peculiaritiesTextViewNumber.text =
                    peculiatiesList[0].replace("]", "").replace("[", "")
            }
            if (peculiatiesList.size >= 2) {
                peculiaritiesTextView2Number.text =
                    peculiatiesList[1].replace("]", "").replace("[", "")
            }
            if (room.image_urls.isNotEmpty()) {
                val imageAdapter = ImageAdapter(room.image_urls)
                imageRecyclerView.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = imageAdapter
                }
            }
            bockingBtn.setOnClickListener {
                fragment.findNavController().navigate(R.id.action_numberFragment_to_bookingFragment)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<Room>() {
        override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.number_item, parent, false)
        return Holder(view, fragment)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}