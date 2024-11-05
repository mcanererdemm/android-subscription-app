package com.example.odev2.ui.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.odev2.R
import com.example.odev2.data.database.db.SubscriptionDatabase
import com.example.odev2.data.database.db.UserDataBase
import com.example.odev2.repository.RepositoryImp
import com.example.odev2.ui.login.viewmodel.SignUpViewModel
import com.example.odev2.ui.login.viewmodel.SignUpViewModelFactory
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        val repository = RepositoryImp(
            SubscriptionDatabase.getInstance(requireContext()),
            UserDataBase.getInstance(requireContext())
        )
        viewModel =
            ViewModelProvider(this, SignUpViewModelFactory(repository))[SignUpViewModel::class.java]

        observeViewModel()

        view.findViewById<Button>(R.id.btn_signUp_toSignIn).setOnClickListener {
            getEmailPasswordAndRePassword(view)
            viewModel.signUp()
        }
        return view
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signUpState.collect { state ->
                if (state.isSignUpSuccessful) {
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
                } else if (state.signUpError.isNotEmpty()) {
                    Toast.makeText(requireContext(), state.signUpError, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getEmailPasswordAndRePassword(view: View) {
        val email = view.findViewById<EditText>(R.id.et_email)?.text.toString()
        val password = view.findViewById<EditText>(R.id.et_password)?.text.toString()
        val rePassword = view.findViewById<EditText>(R.id.et_rePassword)?.text.toString()
        viewModel.setEmailAndPasswordAndRePassword(email, password, rePassword)
    }
}