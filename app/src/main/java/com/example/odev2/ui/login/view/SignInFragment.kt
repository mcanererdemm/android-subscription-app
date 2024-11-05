package com.example.odev2.ui.login.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.odev2.R
import com.example.odev2.data.database.db.SubscriptionDatabase
import com.example.odev2.data.database.db.UserDataBase
import com.example.odev2.repository.RepositoryImp
import com.example.odev2.ui.login.viewmodel.SignInViewModel
import com.example.odev2.ui.login.viewmodel.SignInViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        val repository = RepositoryImp(
            SubscriptionDatabase.getInstance(requireContext()),
            UserDataBase.getInstance(requireContext())
        )
        signInViewModel =
            ViewModelProvider(this, SignInViewModelFactory(repository))[SignInViewModel::class.java]

        view.findViewById<AppCompatButton>(R.id.btn_signIn_toHome).setOnClickListener {
            getEmailAndPassword()
            observeSignInState()
        }

        view.findViewById<AppCompatButton>(R.id.btn_signIn_toSignUp).setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_toSignUpFragment)
        }

        view.findViewById<SwitchMaterial>(R.id.switch_signInFragment)
            .setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    setPreferences()
                }
            }
        return view
    }

    private fun getEmailAndPassword() {
        val email = view?.findViewById<EditText>(R.id.et_email)?.text.toString()
        val password = view?.findViewById<EditText>(R.id.et_password)?.text.toString()
        signInViewModel.setEmailAndPassword(email, password)
        signInViewModel.signIn()
    }

    private fun observeSignInState() {
        viewLifecycleOwner.lifecycleScope.launch {
            signInViewModel.signInState.collect { state ->
                if (state.isSignInSuccessful) {
                    Toast.makeText(requireContext(), "Sign In Successful", Toast.LENGTH_LONG).show()
                    this@SignInFragment.view?.findNavController()
                        ?.setGraph(R.navigation.my_nav_graph)
                } else if (state.signInError.isNotEmpty()) {
                    Toast.makeText(requireContext(), state.signInError, Toast.LENGTH_LONG).show()
                    state.signInError = ""
                }
            }
        }
    }

    private fun setPreferences() {
        val editor = requireActivity().getSharedPreferences("login", MODE_PRIVATE).edit()
        editor.putBoolean("success", true)
        editor.apply()
    }
}