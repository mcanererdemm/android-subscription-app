package com.example.odev2.main

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.odev2.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //makeFullScreen()
        window.statusBarColor = resources.getColor(R.color.dark_grey)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        bottomNavigationView = findViewById(R.id.bot_navbar)
        bottomAppBar = findViewById(R.id.bot_appbar)
        fab = findViewById(R.id.fab)

        navPrepare()

        val isLoggedIn = checkLogin()
        if (isLoggedIn) {
            navController.setGraph(R.navigation.my_nav_graph)
        } else {
            navController.setGraph(R.navigation.signin_graph)
        }
    }

    private fun makeFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            @Suppress("DEPRECATION") window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    private fun navPrepare() {
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.menu.getItem(2).isEnabled = false

        fab.setOnClickListener {
            val navOptions =
                NavOptions.Builder().setLaunchSingleTop(true).setPopUpTo(R.id.homeFragment, false)
                    .build()

            navController.navigate(R.id.action_global_toAddFragment, null, navOptions)
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.categoryFragment -> {
                    navController.navigate(R.id.categoryFragment)
                    true
                }

                R.id.calenderFragment -> {
                    navController.navigate(R.id.calenderFragment)
                    true
                }

                R.id.settingsFragment -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }

                else -> throw Exception("Navigasyon hatası".repeat(10))
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signInFragment, R.id.signUpFragment -> {
                    bottomNavigationView.visibility = View.GONE
                    bottomAppBar.visibility = View.GONE
                    fab.visibility = View.GONE
                }

                else -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    bottomAppBar.visibility = View.VISIBLE
                    fab.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun checkLogin(): Boolean {
        val preference = getSharedPreferences("login", MODE_PRIVATE).getBoolean("success", false)
        return preference
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}