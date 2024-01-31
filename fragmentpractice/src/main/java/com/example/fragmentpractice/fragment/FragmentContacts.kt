package com.example.fragmentpractice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fragmentpractice.R

class FragmentContacts : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val content = getArguments()!!.getString("content")
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.item_fragment2, container, false)

    }
}