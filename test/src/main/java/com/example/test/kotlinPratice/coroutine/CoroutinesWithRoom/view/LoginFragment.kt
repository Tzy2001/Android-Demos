package com.example.test.kotlinPratice.coroutine.CoroutinesWithRoom.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.NavDirections
import com.example.test.databinding.FragmentLoginBinding
import com.example.test.kotlinPratice.coroutine.CoroutinesWithRoom.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.loginBtn.setOnClickListener { onLogin(it) }
        binding!!.gotoSignupBtn.setOnClickListener { onGotoSignUp(it) }
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginComplete.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, "login complete", Toast.LENGTH_SHORT).show()
            val action = LoginFragmentDirections.actionGoToMain()
            Navigation.findNavController(binding!!.loginUsername).navigate(action)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(activity, "error: $error", Toast.LENGTH_SHORT).show()
        })
    }

    private fun onLogin(v: View) {
        val userName = binding!!.loginUsername.text.toString()
        val password = binding!!.loginPassword.text.toString()
        if (userName.isNullOrBlank() || password.isNullOrBlank()) {
            Toast.makeText(activity, "fill all field", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.login(userName, password)
        }
    }

    private fun onGotoSignUp(v: View) {
        val action = LoginFragmentDirections.actionGoToSignup()
        Navigation.findNavController(v).navigate(action)
    }

}