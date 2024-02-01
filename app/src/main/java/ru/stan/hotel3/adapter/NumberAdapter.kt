package ru.stan.hotel3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.stan.hotel3.R
import ru.stan.hotel3.data.Room
import ru.stan.hotel3.databinding.NumberItemBinding

class NumberAdapter : ListAdapter<Room, NumberAdapter.Holder>(Comparator()) {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
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
                Glide.with(imageNumber)
                    .load(room.image_urls[0]) // Предполагается, что вы используете Glide для загрузки изображений
                    .placeholder(R.drawable.right_blue) // Изображение-заглушка во время загрузки
                    .error(R.drawable.arrow_back) // Изображение-ошибка, если URL недействителен
                    .into(imageNumber)
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
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}