package com.apis.recipefood.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.apis.recipefood.activites.DetailMealActivity
import com.apis.recipefood.databinding.FragmentBottomSheetBinding
import com.apis.recipefood.util.Constants
import com.apis.recipefood.viewmodel.MealViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


private const val MEAL_ID = "param1"
@Suppress("UNREACHABLE_CODE")
@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding

    private var mealId: String? = null
    private val viewModel: MealViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let { viewModel.getMealById(it) }

    observeBottomSheet()

    bottomSheetClick()


}
    private fun bottomSheetClick() {
        binding.bottomSheet.setOnClickListener {
            if (mealName !=null && mealThumb != null){
                val intent = Intent(activity,DetailMealActivity::class.java)
                intent.apply {
                    putExtra(Constants.MEAL_NAME,mealName)
                    putExtra(Constants.MEAL_ID,mealId)
                    putExtra(Constants.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private var mealThumb:String?= null
    private var mealName:String?=null

    private fun observeBottomSheet() {
        viewModel.observerBottomSheetMeal().observe(viewLifecycleOwner) { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.ivBottomSheet)
            binding.tvMealNameBs.text = meal.strMeal
            binding.tvBottomSheetCategory.text = meal.strCategory
            binding.tvBottomSheetArea.text = meal.strArea

            mealName=meal.strMeal
            mealThumb=meal.strMealThumb

        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}