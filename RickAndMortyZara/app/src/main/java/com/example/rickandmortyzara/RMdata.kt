package com.example.rickandmortyzara

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyzara.databinding.ActivityRmdataBinding
import okhttp3.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RMdata : AppCompatActivity() {
    lateinit var binding: ActivityRmdataBinding
    val characterList: MutableList<ModelDataRM> = mutableListOf()
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_rmdata)
        binding = ActivityRmdataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearchView()
        requestDataFromApi()
    }

    private fun setupSearchView() {
        val searchView = findViewById<SearchView>(R.id.txt_buscar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    recyclerViewAdapter.filterByName(newText)
                }
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter(characterList)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_char)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun requestDataFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            val gson = Gson()
            var countcharacter = 1

            withContext(Dispatchers.Main) {
                Glide.with(this@RMdata)
                    .asGif()
                    .load(R.raw.loading)
                    .into(binding.imgLoading)
            }

            while (countcharacter <= 826) {
                val request = Request.Builder()
                    .url("https://rickandmortyapi.com/api/character/$countcharacter")
                    .build()

                try {
                    val response = client.newCall(request).execute()
                    val responseData = response.body?.string()


                    val data = gson.fromJson(responseData, ModelDataRM::class.java)

                    withContext(Dispatchers.Main) {
                        characterList.add(data)
                        isDataLoaded = true
                        if (::binding.isInitialized && isDataLoaded) {
                            setupRecyclerView()
                        }
                        if (countcharacter == 826) {
                            binding.imgLoading.visibility = View.GONE
                            binding.extdata.visibility = View.GONE
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@RMdata,
                            "An error occurred while trying to connect to the API",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                countcharacter++
            }
        }
    }
}