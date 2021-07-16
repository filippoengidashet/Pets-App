package com.filippoengidashet.petsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.filippoengidashet.petsapp.R
import com.filippoengidashet.petsapp.data.BreedSearch


/**
 * @author Filippo 15/07/2021
 */
class ImagesPagerAdapter : PagerAdapter() {

    private val items = mutableListOf<BreedSearch>()

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val li = LayoutInflater.from(container.context)
        val itemView: View = li.inflate(R.layout.item_image_layout, null)
        val breedImage: ImageView = itemView.findViewById(R.id.image_breed)
        val item = items[position]
        Glide.with(itemView.context.applicationContext)
            .load(item.url)
            .into(breedImage)
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount() = items.size

    fun setItems(_items: List<BreedSearch>) {
        items.clear()
        items.addAll(_items)
        notifyDataSetChanged()
    }
}
