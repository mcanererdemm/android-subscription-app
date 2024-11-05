package com.example.odev2.ui.main.category

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.odev2.R
import com.example.odev2.data.adapter.CategoryAdapter
import com.example.odev2.data.model.Category
import com.example.odev2.util.getSubscriptionList

class CategoryFragment : Fragment() {
    private var recievedCategory: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recievedCategory = CategoryFragmentArgs.fromBundle(requireArguments()).category
        println(recievedCategory.toString().repeat(10))

        val view = inflater.inflate(R.layout.fragment_category, container, false)

        val rv = view?.findViewById<RecyclerView>(R.id.rv_categoryFragment)
        val sublist = getSubscriptionList()

        val adapter = CategoryAdapter(sublist) {
            val actionToAddFragment =
                CategoryFragmentDirections.actionCategoryFragmentToAddFragment()
            actionToAddFragment.choosenSub = it
            findNavController().navigate(actionToAddFragment)
        }
        rv?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv?.adapter = adapter

        rv?.addItemDecoration(object:ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
                super.getItemOffsets(outRect, itemPosition, parent)
            }
        })

        return view
    }
}