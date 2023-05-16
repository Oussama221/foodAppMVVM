package com.bib.metoapplication.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.bib.metoapplication.R
import com.bib.metoapplication.db.MealDataBase
import com.bib.metoapplication.viewModel.HomeViewModel
import com.bib.metoapplication.viewModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val viewModel: HomeViewModel by lazy {
        val database = MealDataBase.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(database)
        ViewModelProvider(this,homeViewModelFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = Navigation.findNavController(this, R.id.parentfragment)

        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}