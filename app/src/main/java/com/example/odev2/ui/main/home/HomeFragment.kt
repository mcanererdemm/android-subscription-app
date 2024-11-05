package com.example.odev2.ui.main.home

import SubscriptionAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.odev2.R
import com.example.odev2.data.database.db.SubscriptionDatabase
import com.example.odev2.data.database.db.UserDataBase
import com.example.odev2.repository.RepositoryImp
import com.example.odev2.ui.main.viewmodel.MainViewModel
import com.example.odev2.ui.main.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels {
        val repository = RepositoryImp(
            SubscriptionDatabase.getInstance(requireContext()),
            UserDataBase.getInstance(requireContext())
        )
        MainViewModelFactory(repository)
    }
    private lateinit var adapter: SubscriptionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_subscription_homeFragment)

        adapter = SubscriptionAdapter(emptyList()) { item ->
//            val action = HomeFragmentDirections.actionHomeFragmentToCategoryFragment()
//                .setCategory(item.category)
//            findNavController().navigate(action)
            Toast.makeText(requireContext(), "${item.id}", Toast.LENGTH_SHORT).show()
            viewModel.deleteSubscription(item.id)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        viewModel.getSubsListFromRepository()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.subList.collect { state ->
                adapter.updateSubscriptions(state.subscriptionList)
                view.findViewById<TextView>(R.id.tv_Budget_homeFragment).text =
                    "₺${state.sumOfSubscription}"
            }
        }

        return view
    }
}