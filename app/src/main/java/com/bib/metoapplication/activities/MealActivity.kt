package com.bib.metoapplication.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bib.metoapplication.R
import com.bib.metoapplication.databinding.ActivityMealBinding
import com.bib.metoapplication.db.MealDataBase
import com.bib.metoapplication.fragments.HomeFragment
import com.bib.metoapplication.pojo.Meal
import com.bib.metoapplication.viewModel.MealViewModel
import com.bib.metoapplication.viewModel.MealViewModelFactory
import com.bumptech.glide.Glide

class MealActivity : AppCompatActivity() {

private lateinit var mealId:String
private lateinit var mealName:String
private lateinit var mealThumb:String
private lateinit var binding: ActivityMealBinding
private lateinit var mealMVVM : MealViewModel
private lateinit var youtubeLink : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDataBase = MealDataBase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDataBase)
       mealMVVM = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]
        loadingCase()
        getMealInformationFromIntent()
        bindingView()
        mealMVVM.getMealDetails(mealId)
        ObserveMealDetailsLiveData()
        OnYoutubeImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
            binding.btnAddFavorite.setOnClickListener {
               mealToSave?.let {
                   mealMVVM.insertMeal(it)
                   Toast.makeText(this,"Meal Saved",Toast.LENGTH_SHORT)
                   Log.d("meal Saved",""+it.idMeal)
               }
            }
    }

    private fun OnYoutubeImageClick() {
        binding.youtubeIcon.setOnClickListener {
            val intent =Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun bindingView() {
       // TODO("Not yet implemented")
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))


    }
    private var mealToSave:Meal ?= null
    fun ObserveMealDetailsLiveData() {
        mealMVVM.observeMealDetailsLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(t: Meal?) {
               // TODO("Not yet implemented")
                onResponseCase()
                mealToSave = t
                binding.tvCategory.text = "Category : ${t!!.strCategory}"
                binding.tvArea.text = "Area : ${t!!.strArea}"
                binding.contentIns.text = t!!.strInstructions
                youtubeLink = t.strYoutube!!
            }

        })
    }
    private fun getMealInformationFromIntent() {
     //   TODO("GET MEAL INFORMATION FROM INTENT")
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
        Log.i("MEAL_ID",mealId+" : "+mealName)
    }

    private fun loadingCase(){
        binding.contentIns.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.youtubeIcon.visibility = View.INVISIBLE
        binding.btnAddFavorite.visibility = View.INVISIBLE
        binding.indicatorProgressBar.visibility = View.VISIBLE
    }

    private fun onResponseCase(){
        binding.contentIns.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.youtubeIcon.visibility = View.VISIBLE
        binding.btnAddFavorite.visibility = View.VISIBLE
        binding.indicatorProgressBar.visibility = View.INVISIBLE
    }
}


