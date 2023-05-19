package com.bib.metoapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bib.metoapplication.R
import com.bib.metoapplication.activities.CategoryMealsActivity
import com.bib.metoapplication.activities.MainActivity
import com.bib.metoapplication.activities.MealActivity
import com.bib.metoapplication.adapters.CategoryAdapter
import com.bib.metoapplication.adapters.MostPopularAdapter
import com.bib.metoapplication.databinding.FragmentHomeBinding
import com.bib.metoapplication.pojo.*
import com.bib.metoapplication.viewModel.HomeViewModel
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    companion object {
        const val MEAL_ID = "com.bib.metoapplication.fragments.mealId"
        const val MEAL_NAME = "com.bib.metoapplication.fragments.mealName"
        const val MEAL_THUMB = "com.bib.metoapplication.fragments.mealThumb"
        const val CATEGORY_NAME = "com.bib.metoapplication.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel = (activity as MainActivity).viewModel
        popularItemsAdapter = MostPopularAdapter()
        categoryAdapter = CategoryAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePopularItemsRecyclerView()
        viewModel.getRundomMeal()
        observeRandomMeal()
        onRandomMealClick()
        viewModel.getPopularMeals()

        observePopularItems()
        onPopularItemClick()

        viewModel.getAllCategories()
        observeAllCategories()
        prepareCategoriesRecyclerView()

        onCategoryClick()
        onLongItemClick()

        onSearchIconClicked()
    }

    private fun onSearchIconClicked() {
        binding.searchImg.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }


    private fun onLongItemClick() {
        popularItemsAdapter.onLongItemClick= {
            val bottomSheetMealFragment = BottomSheetMealFragment.newInstance(it.idMeal)
            bottomSheetMealFragment.show(childFragmentManager,"Meal info")
        }
    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick ={
            category ->  val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
            binding.rvCategory.apply {
                layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
                adapter = categoryAdapter
            }
    }

    private fun observeAllCategories() {
       viewModel.observeAllCategories().observe(viewLifecycleOwner,object :Observer<List<Category>>{
           override fun onChanged(t: List<Category>?) {
               categoryAdapter.setCategories(t as ArrayList<Category> /* = java.util.ArrayList<com.bib.metoapplication.pojo.Category> */)
                t!!.forEach { category ->
                    Log.d("category name",""+category.strCategory)
                }
           }
       })
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
        viewModel.observePopularMealLiveData().observe(viewLifecycleOwner,object :Observer<List<CategoryMeals>>{
            override fun onChanged(t: List<CategoryMeals>?) {
                popularItemsAdapter.setMeals( t as ArrayList<CategoryMeals>)
            }
        })
    }

    private fun observeRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,object :Observer<Meal>{
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