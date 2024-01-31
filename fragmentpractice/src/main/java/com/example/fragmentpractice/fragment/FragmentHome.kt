package com.example.fragmentpractice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.fragmentpractice.R
import com.example.fragmentpractice.databinding.ItemFragment1Binding
import androidx.navigation.fragment.findNavController

class FragmentHome :Fragment(){
    private lateinit var binding  :ItemFragment1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=ItemFragment1Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle=Bundle()
        bundle.putString("content","How are you?")
        binding.btn1.setOnClickListener {
            val navController=view.findNavController()
            navController.navigate(R.id.action_afragment_to_bfragment)
        }
    }
}