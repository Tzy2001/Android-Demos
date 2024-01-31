package com.example.test.kotlinPratice.coroutine.coroutinesWithRetrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.databinding.ActivityMain5Binding
import com.example.test.kotlinPratice.coroutine.coroutinesWithRetrofit.viewModel.ListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel:ListViewModel
    private val countriesAdapter=CountryListAdapter(arrayListOf())
    private val binding by lazy { ActivityMain5Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel=ViewModelProvider(this)[ListViewModel::class.java]
        viewModel.refresh()

        binding.countriesList.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=countriesAdapter
        }
        observeViewModel()
    }


    fun observeViewModel(){
        viewModel.countries.observe(this,Observer{countries ->
            countries?.let {
                binding.countriesList.visibility= View.VISIBLE
                countriesAdapter.updateCountries(it)
            }
        })

        viewModel.countryLoadError.observe(this, Observer { isError->
            binding.listError.visibility=if (isError==null) View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading->
            isLoading?.let {
                binding.loadingView.visibility=if (it) View.VISIBLE else View.GONE
                if (it){
                    binding.listError.visibility=View.GONE
                    binding.countriesList.visibility=View.GONE
                }
            }
        })

    }
}