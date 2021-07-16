package com.filippoengidashet.petsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.filippoengidashet.petsapp.R
import com.filippoengidashet.petsapp.data.Breed

/**
 * @author Filippo 15/07/2021
 */
class BreedListAdapter(
    private val inflater: LayoutInflater, private val onClick: (breed: Breed) -> Unit
) : RecyclerView.Adapter<BreedListAdapter.BreedViewHolder>() {

    private val breedList = mutableListOf<Breed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        return BreedViewHolder(
            inflater.inflate(R.layout.item_breed_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(breedList[position])
    }

    override fun getItemCount() = breedList.size

    fun setBreeds(breeds: List<Breed>) {
        breedList.clear()
        breedList.addAll(breeds)
        notifyDataSetChanged()
    }

    inner class BreedViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val imageBreed = view.findViewById<ImageView>(R.id.image_breed)
        private val textBreedName = view.findViewById<TextView>(R.id.text_breed_name)
        private val textBreedTemperament = view.findViewById<TextView>(R.id.text_breed_temperament)
        private val textBreedWikiLink = view.findViewById<TextView>(R.id.text_breed_wiki_link)

        init {
            view.setOnClickListener {
                onClick(breedList[adapterPosition])
            }
        }

        fun bind(breed: Breed) {

            val ctx = view.context

            textBreedName.text = breed.name
            textBreedTemperament.text = breed.temperament
            textBreedWikiLink.text = breed.wikipedia_url

            Glide.with(ctx)
                .load(breed.image?.url)
                .into(imageBreed)
        }
    }
}