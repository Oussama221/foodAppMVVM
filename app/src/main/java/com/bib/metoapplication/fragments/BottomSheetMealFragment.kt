package com.bib.metoapplication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bib.metoapplication.R
import com.bib.metoapplication.activities.MainActivity
import com.bib.metoapplication.activities.MealActivity
import com.bib.metoapplication.databinding.FragmentBottomSheetMealBinding
import com.bib.metoapplication.pojo.Meal
import com.bib.metoapplication.viewModel.HomeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEAL_ID = "param1"

class BottomSheetMealFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    private lateinit var binding: FragmentBottomSheetMealBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetMealBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let { viewModel.getMealById(it) }
        observeBottomSheetMeal()
        onClickInBottomSheet()
    }

    private fun onClickInBottomSheet() {
        binding.parentIdBS.setOnClickListener {
            if (mealName!=null && mealThumb != null){
                val i = Intent(activity,MealActivity::class.java)
                i.apply {
                    i.putExtra(HomeFragment.MEAL_ID,mealId)
                    i.putExtra(HomeFragment.MEAL_NAME,mealName)
                    i.putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(i)

            }
        }
    }

    private var mealName: String ?=null
    private var mealThumb: String ?=null
    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetMealById().observe(viewLifecycleOwner,object :Observer<Meal>{
            override fun onChanged(t: Meal?) {
                binding.mealArea.text = t!!.strArea
                binding.mealCategory.text = t!!.strCategory
                binding.meealName.text = t!!.strMeal
                Glide.with(this@BottomSheetMealFragment).load(t.strMealThumb).into(binding.mealImg)

                mealName = t.strMeal
                mealThumb = t.strMealThumb
            }

        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BottomSheetMealFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}