package com.bib.metoapplication.fragments

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bib.metoapplication.R
import com.bib.metoapplication.activities.CategoryMealsActivity
import com.bib.metoapplication.activities.MainActivity
import com.bib.metoapplication.adapters.CategoryAdapter
import com.bib.metoapplication.databinding.FragmentCategoryBinding
import com.bib.metoapplication.pojo.Category
import com.bib.metoapplication.viewModel.HomeViewModel

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        categoryAdapter = CategoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRvCategories()
        observeAllCategories()
        onCategoryClick()
    }
    private fun onCategoryClick() {
        categoryAdapter.onItemClick ={
                category ->  val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun observeAllCategories() {
        viewModel.observeAllCategories().observe(viewLifecycleOwner,object : Observer<List<Category>>{
            override fun onChanged(t: List<Category>?) {
                categoryAdapter.setCategories(t as ArrayList<Category>)
            }

        })
    }

    private fun prepareRvCategories() {
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(context,3, GridLayoutManager.VERTICAL,false)
            adapter = categoryAdapter
        }
    }

}