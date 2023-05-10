package com.bib.metoapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bib.metoapplication.R
import com.bib.metoapplication.activities.MealActivity
import com.bib.metoapplication.adapters.MostPopularAdapter
import com.bib.metoapplication.databinding.FragmentHomeBinding
import com.bib.metoapplication.pojo.CategoryList
import com.bib.metoapplication.pojo.CategoryMeals
import com.bib.metoapplication.pojo.Meal
import com.bib.metoapplication.pojo.MealList
import com.bib.metoapplication.retrofit.RetrofitInstance
import com.bib.metoapplication.viewModel.HomeViewModel
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter

    companion object {
        const val MEAL_ID = "com.bib.metoapplication.fragments.mealId"
        const val MEAL_NAME = "com.bib.metoapplication.fragments.mealName"
        const val MEAL_THUMB = "com.bib.metoapplication.fragments.mealThumb"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)
        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePopularItemsRecyclerView()
        homeMvvm.getRundomMeal()
        observeRandomMeal()
        onRandomMealClick()
        homeMvvm.getPopularMeals()

        observePopularItems()
        onPopularItemClick()
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {categoryMeals ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,categoryMeals.idMeal)
            intent.putExtra(MEAL_NAME,categoryMeals.strMeal)
            intent.putExtra(MEAL_THUMB,categoryMeals.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observePopularItems(){
        homeMvvm.observePopularMealLiveData().observe(viewLifecycleOwner,object :Observer<List<CategoryMeals>>{
            override fun onChanged(t: List<CategoryMeals>?) {

                popularItemsAdapter.setMeals( t as ArrayList<CategoryMeals>)
            }
        })
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner,object :Observer<Meal>{
            override fun onChanged(t: Meal?) {
                Glide.with(this@HomeFragment).load(t!!.strMealThumb).into(binding.imgRandom)
                randomMeal = t
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

    }


}