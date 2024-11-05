package com.example.odev2.ui.main.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.odev2.R
import com.example.odev2.data.adapter.AddSubAdapter
import com.example.odev2.data.database.db.SubscriptionDatabase
import com.example.odev2.data.database.db.UserDataBase
import com.example.odev2.data.model.Subscription
import com.example.odev2.repository.RepositoryImp
import com.example.odev2.ui.main.viewmodel.MainViewModel
import com.example.odev2.ui.main.viewmodel.MainViewModelFactory
import com.example.odev2.util.getSubscriptionList

class AddFragment : Fragment() {

    private var price = 150
    private val viewModel: MainViewModel by activityViewModels {
        val repository = RepositoryImp(
            SubscriptionDatabase.getInstance(requireContext()),
            UserDataBase.getInstance(requireContext())
        )
        MainViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add, container, false)


        val rv = view.findViewById<RecyclerView>(R.id.rv_addFragment)
        rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val subscriptionList = getSubscriptionList()
        val choosenSub = AddFragmentArgs.fromBundle(requireArguments()).choosenSub
        val chosenSubIndex = subscriptionList.indexOf(choosenSub)

        val adapter = AddSubAdapter(subscriptionList)
        rv.adapter = adapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rv)

        if (chosenSubIndex != -1) {
            rv.smoothScrollToPosition(chosenSubIndex)
        }

        val etDescription = view.findViewById<EditText>(R.id.et_description_addFragment)
        val etPrice = view.findViewById<TextView>(R.id.tv_mothlyPrice_addFragment)
        val btnMinus = view.findViewById<TextView>(R.id.btn_minus_addFragment)
        val btnPlus = view.findViewById<TextView>(R.id.btn_plus_addFragment)

        etPrice.text = "₺$price"

        btnPlus.setOnClickListener {
            price = (price + 50).coerceAtMost(10000)
            etPrice.text = "₺$price"
        }

        btnMinus.setOnClickListener {
            price = (price - 50).coerceAtLeast(10)
            etPrice.text = "₺$price"
        }

        val btnAdd = view.findViewById<TextView>(R.id.btn_addSub_addFragment)

        btnAdd.setOnClickListener {
            val description = etDescription.text.toString()
            val priceText = price.toString()

            val imagePosition =
                (rv.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition()
                    ?: -1

            if (imagePosition != -1) {
                val selectedItem = subscriptionList[imagePosition]

                val subscription = Subscription(
                    name = selectedItem.name,
                    price = priceText,
                    image = selectedItem.image,
                    category = selectedItem.category,
                    description = description
                )
                viewModel.addSubscription(subscription)  // Veritabanına ekleme ve güncelleme
            }
            findNavController().navigate(R.id.homeFragment)
        }
        return view
    }
}