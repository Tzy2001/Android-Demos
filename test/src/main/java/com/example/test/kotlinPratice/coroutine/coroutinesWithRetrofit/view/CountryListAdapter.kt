package com.example.test.kotlinPratice.coroutine.coroutinesWithRetrofit.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.ItemCountryBinding
import com.example.test.kotlinPratice.coroutine.coroutinesWithRetrofit.model.Country

class CountryListAdapter (var countries:ArrayList<Country>):RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>(){


     class CountryViewHolder(private val binding:ItemCountryBinding):RecyclerView.ViewHolder(binding.root){
        private val imageView=binding.imageView
        private val countryName=binding.name
        private val countryCapital=binding.capital

        fun bind(country:Country){
            countryName.text=country.countryName
            countryCapital.text=country.capital
            imageView.loadImage(country.flag)
        }

        companion object{
            fun from(parent:ViewGroup):CountryViewHolder{
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=ItemCountryBinding.inflate(layoutInflater,parent,false)
                return CountryViewHolder(binding)
            }
        }
    }

    fun updateCountries(newCountry:List<Country>){
        countries.clear()
        countries.addAll(newCountry)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }
}