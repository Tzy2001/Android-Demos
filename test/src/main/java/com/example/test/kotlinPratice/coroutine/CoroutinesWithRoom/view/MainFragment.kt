package com.example.test.kotlinPratice.coroutine.CoroutinesWithRoom.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.test.R
import com.example.test.databinding.FragmentMainBinding
import com.example.test.kotlinPratice.coroutine.CoroutinesWithRoom.model.LoginState
import com.example.test.kotlinPratice.coroutine.CoroutinesWithRoom.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.usernameTV.text = LoginState.user?.userName
        binding.signoutBtn.setOnClickListener { onSignout() }
        binding.deleteUserBtn.setOnClickListener { onDelete() }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.signout.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, "signed out", Toast.LENGTH_SHORT).show()
            goToSignupScreen()
        })
        viewModel.userDeleted.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity,"user deleted",Toast.LENGTH_SHORT).show()
            goToSignupScreen()
        })
    }

    private fun goToSignupScreen(){
        val action:NavDirections=MainFragmentDirections.actionGoToSignup()
        Navigation.findNavController(binding.usernameTV).navigate(action)
    }

    private fun onSignout() {
        viewModel.onSignout()
    }

    private fun onDelete(){
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Delete User")
                .setMessage("are you sure?")
                .setPositiveButton("yes"){p0,p1-> viewModel.onDeleteUSer()}
                .setNegativeButton("cancel",null)
                .create()
                .show()
        }
    }


}