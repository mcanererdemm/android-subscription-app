package com.example.odev2.ui.main

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.odev2.R
import com.example.odev2.data.database.db.SubscriptionDatabase
import com.example.odev2.data.database.db.UserDataBase
import com.example.odev2.repository.RepositoryImp
import com.example.odev2.ui.main.viewmodel.MainViewModel
import com.google.android.material.button.MaterialButton

class SettingsFragment : Fragment() {
    private val viewModel: MainViewModel by lazy {
        val repository = RepositoryImp(
            SubscriptionDatabase.getInstance(requireContext()),
            UserDataBase.getInstance(requireContext())
        )
        MainViewModel(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        view.findViewById<MaterialButton>(R.id.btn_setting_deleteAllSubs).setOnClickListener {
            viewModel.deleteAllSubs()
        }

        view.findViewById<MaterialButton>(R.id.btn_logout_deleteAllSubs).setOnClickListener {
            val editor = requireActivity().getSharedPreferences("login", MODE_PRIVATE).edit()
            editor.putBoolean("success", false)
            editor.apply()
            requireActivity().finish()
        }

        return view
    }
}