package com.bib.metoapplication.fragments

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bib.metoapplication.R
import com.bib.metoapplication.activities.MainActivity
import com.bib.metoapplication.adapters.FavoriteMealsAdapter
import com.bib.metoapplication.databinding.FragmentFavoriteBinding
import com.bib.metoapplication.pojo.Meal
import com.bib.metoapplication.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteMealsAdapter: FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
        prepareRV()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT

        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position =viewHolder.adapterPosition
                viewModel.deleteMeal(favoriteMealsAdapter.differ.currentList[position])

                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_SHORT).show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorite)
    }

    private fun prepareRV() {
        favoriteMealsAdapter = FavoriteMealsAdapter()
        binding.rvFavorite.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = favoriteMealsAdapter
        }
    }

    private fun observeLiveData() {
        viewModel.observeFavoriteMeal().observe(viewLifecycleOwner, object : Observer<List<Meal>> {
            override fun onChanged(t: List<Meal>?) {
                favoriteMealsAdapter.differ.submitList(t)
                t!!.forEach {
                    Log.d("all the meals saved ", "" + it.idMeal)
                }
            }
        })
    }
}
