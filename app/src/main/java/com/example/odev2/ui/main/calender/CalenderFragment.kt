package com.example.odev2.ui.main.calender

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.odev2.R
import com.example.odev2.data.adapter.SquareItemAdapter
import com.example.odev2.data.database.db.SubscriptionDatabase
import com.example.odev2.data.database.db.UserDataBase
import com.example.odev2.data.model.Category
import com.example.odev2.data.model.Subscription
import com.example.odev2.data.model.SubscriptionCategory
import com.example.odev2.repository.RepositoryImp
import com.example.odev2.ui.main.viewmodel.MainViewModel
import com.example.odev2.ui.main.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch
import java.util.Calendar

class CalenderFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var rView: RecyclerView
    private lateinit var adapter: SquareItemAdapter
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
        rootView = inflater.inflate(R.layout.fragment_calender, container, false)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)

        val dayNames = arrayOf("Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat")
        val monthNames = arrayOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
        var selectedButton: TextView? = null


        fun updateCalendarView(weekOffset: Int = 0) {
            calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
            calendar.add(Calendar.WEEK_OF_YEAR, weekOffset)

            val tempCalendar = calendar.clone() as Calendar
            val today = Calendar.getInstance()

            for (i in 0..6) {
                val button = rootView.findViewById<TextView>(
                    resources.getIdentifier(
                        "day${i + 1}Button", "id", context?.packageName
                    )
                )
                val dayOfMonth = tempCalendar.get(Calendar.DAY_OF_MONTH)
                val dayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK)
                button.text = "$dayOfMonth\n${dayNames[dayOfWeek - 1]}"

                val currentMonthIndex = calendar.get(Calendar.MONTH)
                rootView.findViewById<TextView>(R.id.tv_month_calenderFragment).text =
                    monthNames.get(currentMonthIndex)

                button.setBackgroundColor(android.graphics.Color.TRANSPARENT)

                if (tempCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && tempCalendar.get(
                        Calendar.DAY_OF_YEAR
                    ) == today.get(Calendar.DAY_OF_YEAR)
                ) {
                    button.setBackgroundColor(resources.getColor(R.color.grey))
                    button.tag = "today"
                } else {
                    button.tag = null
                }

                val currentDate = tempCalendar.time



                button.setOnClickListener {
                    if (button.tag != "today") {
                        selectedButton?.let {
                            it.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                        }
                        button.setBackgroundColor(android.graphics.Color.DKGRAY)
                        selectedButton = button
                    } else {
                        selectedButton?.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                    }
                    Toast.makeText(
                        requireContext(),
                        "${calendar.timeInMillis}",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.getSubscriptionsLimitedWithDate(currentDate.time)
                }
                tempCalendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            calendar.add(Calendar.WEEK_OF_YEAR, -weekOffset)
        }
        updateCalendarView()

        var currentWeekOffset = 0

        rootView.findViewById<ImageButton>(R.id.previousWeekButton).setOnClickListener {
            currentWeekOffset--
            updateCalendarView(currentWeekOffset)
        }

        rootView.findViewById<ImageButton>(R.id.nextWeekButton).setOnClickListener {
            currentWeekOffset++
            updateCalendarView(currentWeekOffset)
        }

        val squareItemList = emptyList<Subscription>()

        rView = rootView.findViewById(R.id.rView_calenderFragment)
        rView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = SquareItemAdapter(squareItemList)
        rView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.subList.collect {
                adapter.updateSubscriptions(it.subscriptionList)
                rootView.findViewById<TextView>(R.id.tv_price_calenderFragment).text =
                    "₺${it.sumOfSubscription}"
            }
        }


        val spacing = resources.getDimensionPixelSize(R.dimen.recycler_view_item_spacing)
        rView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.set(spacing, spacing, spacing, spacing)
            }
        })

        return rootView
    }

    private fun creteSquareItemList(): List<Subscription> {
        return listOf(
            Subscription(
                name = "Netfelix",
                price = "₺10",
                image = R.drawable.netflix,
                category = Category(SubscriptionCategory.Entertainment)
            ), Subscription(
                name = "Campfy",
                price = "₺20",
                image = R.drawable.cambly,
                category = Category(SubscriptionCategory.Education)
            ), Subscription(
                name = "Hubus",
                price = "₺30",
                image = R.drawable.hbo,
                category = Category(SubscriptionCategory.Entertainment)
            ), Subscription(
                name = "Spotify",
                price = "₺40",
                image = R.drawable.spotify_logo,
                category = Category(SubscriptionCategory.Health)
            ), Subscription(
                name = "Netflix",
                price = "₺50",
                image = R.drawable.netflix,
                category = Category(SubscriptionCategory.Education)
            )
        )
    }
}