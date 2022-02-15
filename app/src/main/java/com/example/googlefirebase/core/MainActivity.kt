package com.example.googlefirebase.core

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.googlefirebase.R
import com.example.googlefirebase.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    /** Class Members **/

    private val TAG = "Main Activity"
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var materialToolbar: MaterialToolbar
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get ActivityMainBinding
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Drawer Layout
        drawerLayout = activityMainBinding.drawerLayout

        // Material Toolbar
        materialToolbar = activityMainBinding.topAppBar
        setSupportActionBar(materialToolbar)

        // Set up NavHostFragment
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // Set Navigation View
        navigationView = activityMainBinding.navView
        // Set up NavController
        val navController = navHostFragment.navController

        // AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(
            navController.graph, activityMainBinding.drawerLayout
        )

        // Set up Navigation View with NavController
        navigationView.setupWithNavController(navController)


        // Set an OnClickListener on the Material Toolbar
        materialToolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener(this)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val fragments = navHost.childFragmentManager.fragments

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.spots_fragment -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_profile_navigation_menu_item_to_spots_fragment)
            }

            R.id.sign_out_menu_item -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.signInFragment)
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}