package com.example.preservenaturetogether.utilities

import android.content.Context
import com.example.preservenaturetogether.data.CategoryRepository
import com.example.preservenaturetogether.data.DistrictRepository
import com.example.preservenaturetogether.data.EcoConditionRepository
import com.example.preservenaturetogether.data.SiteRepository
import com.example.preservenaturetogether.viewmodels.DistrictListViewModelFactory

object InjectorUtils {
    private fun getDistrictRepository(context: Context) =
        DistrictRepository.getInstance(context = context)

    private fun getCategoryRepository(context: Context) =
        CategoryRepository.getInstance(context = context)

    private fun getEcoConditionRepository(context: Context) =
        EcoConditionRepository.getInstance(context = context)

    private fun getSiteRepository(context: Context) =
        SiteRepository.getInstance(context = context)

    fun provideDistrictViewModelFactory(context: Context) =
        DistrictListViewModelFactory(
            districtRepository = getDistrictRepository(context = context),
            siteRepository = getSiteRepository(context = context),
        )
}