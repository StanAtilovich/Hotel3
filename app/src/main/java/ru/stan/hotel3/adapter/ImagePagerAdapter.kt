package ru.stan.hotel3.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import ru.stan.hotel3.fragments.HotelViewModel

class ImagePagerAdapter(private val imageUrls: List<String>, private val viewModel: HotelViewModel) : PagerAdapter() {
    override fun getCount(): Int {
        return imageUrls.size
    }
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context)
        Glide.with(container)
            .load(imageUrls[position])
            .transform(
                RoundedCornersTransformation(100, 10)
            )
            .into(imageView)
        container.addView(imageView)
        viewModel.setCurrentIndex(position)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}

