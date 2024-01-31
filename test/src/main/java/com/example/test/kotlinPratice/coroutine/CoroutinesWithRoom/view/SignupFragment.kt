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
import com.example.test.databinding.FragmentSignupBinding
import com.example.test.kotlinPratice.coroutine.CoroutinesWithRoom.viewmodel.SignupViewModel

class SignupFragment : Fragment() {
    private lateinit var viewModel: SignupViewModel
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signupBtn.setOnClickListener { onSignup(it) }
        binding.gotoLoginBtn.setOnClickListener { onGotoLogin(it) }

        viewModel = ViewModelProvider(this)[SignupViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.signupComplete.observe(viewLifecycleOwner,Observer{isComplete->
            Toast.makeText(activity,"signup complete",Toast.LENGTH_SHORT).show()
            val action=SignupFragmentDirections.actionGoToMain()
            Navigation.findNavController(binding.signupUsername).navigate(action)
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {error->
            Toast.makeText(activity,"error: $error",Toast.LENGTH_SHORT).show()
        })
    }

    private fun onSignup(v:View){
        val userName:String=binding.signupUsername.text.toString()
        val password:String=binding.signupPassword.text.toString()
        val info:String=binding.otherInfo.text.toString()

        if (userName.isNullOrBlank() || password.isNullOrBlank() || info.isNullOrBlank()){
            Toast.makeText(activity,"fill all fields",Toast.LENGTH_SHORT).show()
        }else{
            viewModel.signup(userName,password,info)
        }
    }

    private fun onGotoLogin(v:View){
        val action=SignupFragmentDirections.actionGoToLogin()
        Navigation.findNavController(v).navigate(action)
    }

}