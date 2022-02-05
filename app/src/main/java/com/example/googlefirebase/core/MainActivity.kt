package com.example.googlefirebase.core

import android.icu.lang.UCharacter.IndicPositionalCategory.LEFT
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.googlefirebase.R
import com.example.googlefirebase.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

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
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}