package com.bib.metoapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bib.metoapplication.R
import com.bib.metoapplication.activities.MainActivity
import com.bib.metoapplication.adapters.FavoriteMealsAdapter
import com.bib.metoapplication.adapters.MealsByCategoryAdapter
import com.bib.metoapplication.databinding.FragmentCategoryBinding
import com.bib.metoapplication.databinding.FragmentSearchBinding
import com.bib.metoapplication.pojo.Meal
import com.bib.metoapplication.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match



class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var mealAdapter: FavoriteMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRv()
        binding.imgSearchArrow.setOnClickListener {
            searchMeals()
        }
        observeSearchMeals()

        var searchJob : Job? =null
        binding.searchEdit.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeal(it.toString())
            }
        }
    }

    private fun observeSearchMeals() {
        viewModel.observeSearchMealLiveData().observe(viewLifecycleOwner,object :Observer<List<Meal>>{
            override fun onChanged(t: List<Meal>?) {
                mealAdapter.differ.submitList(t)
            }

        })
    }

    private fun searchMeals() {
        val searchQuery = binding.searchEdit.text.toString()
        if ( searchQuery.isNotEmpty()){
            viewModel.searchMeal(searchQuery)
        }

    }

    private fun prepareRv() {
        mealAdapter = FavoriteMealsAdapter()
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = mealAdapter
        }
    }
}