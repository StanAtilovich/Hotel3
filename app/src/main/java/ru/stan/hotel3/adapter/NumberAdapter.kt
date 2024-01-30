package ru.stan.hotel3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.stan.hotel3.R
import ru.stan.hotel3.data.Room
import ru.stan.hotel3.databinding.NumberItemBinding

class NumberAdapter : ListAdapter<Room, NumberAdapter.Holder>(Comparator()) {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NumberItemBinding.bind(view)

        fun bind(room: Room) = with(binding) {
            nameNumer.text = room.name
            priceNumber.text = room.price.toString()
            pricePerNumber.text = room.price_per
            peculiaritiesTextViewNumber.text = room.peculiarities.toString()
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
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}