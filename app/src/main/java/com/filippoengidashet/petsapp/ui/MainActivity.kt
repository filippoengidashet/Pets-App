package com.filippoengidashet.petsapp.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.filippoengidashet.petsapp.R
import com.filippoengidashet.petsapp.ui.adapters.BreedListAdapter
import com.filippoengidashet.petsapp.vm.BreedsViewModel

class MainActivity : BaseActivity() {

    private lateinit var breedsAdapter: BreedListAdapter

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchMenu = menu.findItem(R.id.actionSearch)
        val searchView = searchMenu.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                searchMenu.collapseActionView()
                searchQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchQuery(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchQuery(query: String) {

        val breeds = ViewModelProvider(this).get(
            BreedsViewModel::class.java
        ).getBreedsLiveData().value

        if (!TextUtils.isEmpty(query)) {
            breeds?.filter {
                it.name?.lowercase()?.contains(query) == true
            }?.let {
                breedsAdapter.setBreeds(it)
            }
        } else {
            breeds?.let {
                breedsAdapter.setBreeds(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionFilter) {
            AlertDialog.Builder(this)
                .setView(R.layout.breeds_filter_layout)
                .setPositiveButton("Filter") { d, _ ->
                    val dialog = d as Dialog
                    val breedsSpinner = dialog.findViewById<Spinner>(R.id.spinner_breeds)
                    val origin = breedsSpinner.selectedItem.toString()

                    val breeds = ViewModelProvider(this).get(
                        BreedsViewModel::class.java
                    ).getBreedsLiveData().value

                    if (TextUtils.equals(origin, "All")) {
                        breeds?.let {
                            breedsAdapter.setBreeds(it)
                        }
                    } else {
                        breeds?.filter {
                            it.origin == origin
                        }?.let {
                            breedsAdapter.setBreeds(it)
                        }
                    }
                }
                .create()
                .also {
                    it.show()
                }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        breedsAdapter = BreedListAdapter(layoutInflater) { breed ->
            Intent(this, BreedDetailsActivity::class.java).apply {
                putExtra(BreedDetailsActivity.EXTRA_BREED_ID, breed.id)
            }.also {
                startActivity(it)
            }
        }

        findViewById<RecyclerView>(R.id.list_breed).apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = breedsAdapter
        }
        val progressView = findViewById<View>(R.id.progress_view)

        val breedsViewModel = ViewModelProvider(this).get(BreedsViewModel::class.java)
        breedsViewModel.getBreedsLiveData().observe(this) { breeds ->
            breedsAdapter.setBreeds(breeds)
        }
        if (savedInstanceState == null) {
            breedsViewModel.loadBreeds({ progress ->
                progressView.visibility = if (progress == BreedsViewModel.ProgressState.Started) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }) { errorMessage ->
                showToast(errorMessage)
            }
        }
    }
}