package com.example.preservenaturetogether.utilities

import android.content.Context
import com.example.preservenaturetogether.data.CategoryRepository
import com.example.preservenaturetogether.data.DistrictRepository
import com.example.preservenaturetogether.data.EcoConditionRepository
import com.example.preservenaturetogether.data.SiteRepository
import com.example.preservenaturetogether.viewmodels.DistrictListViewModelFactory

object InjectorUtils {
    private fun getDistrictsRepository(context: Context) =
        DistrictRepository.getInstance(context = context)

    private fun getCategoriesRepository(context: Context) =
        CategoryRepository.getInstance(context = context)

    private fun getEcoConditionsRepository(context: Context) =
        EcoConditionRepository.getInstance(context = context)

    private fun getSitesRepository(context: Context) =
        SiteRepository.getInstance(context = context)

    fun provideDistrictViewModelFactory(context: Context) =
        DistrictListViewModelFactory(
            districtRepository = getDistrictsRepository(context = context),
        )
}