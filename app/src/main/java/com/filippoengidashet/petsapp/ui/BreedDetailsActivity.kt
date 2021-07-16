package com.filippoengidashet.petsapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.filippoengidashet.petsapp.R
import com.filippoengidashet.petsapp.ui.adapters.ImagesPagerAdapter
import com.filippoengidashet.petsapp.vm.BreedSearchViewModel
import com.rd.PageIndicatorView


class BreedDetailsActivity : BaseActivity() {

    companion object {

        const val EXTRA_BREED_ID = "extra.breed.id"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breed_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pager: ViewPager = findViewById(R.id.itemsPager)
        val indicator: PageIndicatorView = findViewById(R.id.indicator)

        val textBreedName = findViewById<TextView>(R.id.text_breed_name)
        val textBreedOrigin = findViewById<TextView>(R.id.text_breed_origin)
        val textBreedDescription = findViewById<TextView>(R.id.text_breed_description)
        val textBreedTemperament = findViewById<TextView>(R.id.text_breed_temperament)

        val rateView1 = findViewById<RatingBar>(R.id.rate_affection_level)
        val rateView2 = findViewById<RatingBar>(R.id.rate_adaptability)
        val rateView3 = findViewById<RatingBar>(R.id.rate_child_friendly)
        val rateView4 = findViewById<RatingBar>(R.id.rate_dog_friendly)
        val rateView5 = findViewById<RatingBar>(R.id.rate_energy_level)
        val rateView6 = findViewById<RatingBar>(R.id.rate_grooming)
        val rateView7 = findViewById<RatingBar>(R.id.rate_health_issues)
        val rateView8 = findViewById<RatingBar>(R.id.rate_intelligence)
        val rateView9 = findViewById<RatingBar>(R.id.rate_shedding_level)
        val rateView10 = findViewById<RatingBar>(R.id.rate_social_needs)
        val rateView11 = findViewById<RatingBar>(R.id.rate_stranger_friendly)
        val rateView12 = findViewById<RatingBar>(R.id.rate_vocalisation)

        val buttonWikiLink = findViewById<View>(R.id.button_wiki_link)

        val itemsPagerAdapter = ImagesPagerAdapter()

        pager.adapter = itemsPagerAdapter
        pager.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                println("Current position -> $position")
            }
        })

        indicator.setViewPager(pager)

        val viewModel = ViewModelProvider(this).get(BreedSearchViewModel::class.java)
        viewModel.getBreedSearchLiveData().observe(this) { data ->
            itemsPagerAdapter.setItems(data)
            data.takeIf {
                it.isNotEmpty()
            }?.get(0)?.breeds?.takeIf {
                it.isNotEmpty()
            }?.get(0)?.let { breed ->

                textBreedName.text = breed.name
                textBreedOrigin.text = breed.origin
                textBreedDescription.text = breed.description
                textBreedTemperament.text = breed.temperament

                rateView1.rating = breed.affection_level ?: 0f
                rateView2.rating = breed.adaptability ?: 0f
                rateView3.rating = breed.child_friendly ?: 0f
                rateView4.rating = breed.dog_friendly ?: 0f
                rateView5.rating = breed.energy_level ?: 0f
                rateView6.rating = breed.grooming ?: 0f
                rateView7.rating = breed.health_issues ?: 0f
                rateView8.rating = breed.intelligence ?: 0f
                rateView9.rating = breed.shedding_level ?: 0f
                rateView10.rating = breed.social_needs ?: 0f
                rateView11.rating = breed.stranger_friendly ?: 0f
                rateView12.rating = breed.vocalisation ?: 0f

                buttonWikiLink.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(breed.wikipedia_url)))
                }
            }
        }

        if (savedInstanceState == null) {
            intent.getStringExtra(EXTRA_BREED_ID)?.let { id ->
                viewModel.searchBreed(id) { msg ->
                    showToast(msg)
                }
            }
        }
    }
}
