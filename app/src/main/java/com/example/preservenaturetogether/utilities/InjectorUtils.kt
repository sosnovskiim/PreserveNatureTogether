package com.example.preservenaturetogether.utilities

import android.content.Context
import com.example.preservenaturetogether.repository.CategoryRepository
import com.example.preservenaturetogether.repository.DistrictRepository
import com.example.preservenaturetogether.repository.EcoConditionRepository
import com.example.preservenaturetogether.repository.SiteRepository
import com.example.preservenaturetogether.viewmodels.DistrictListViewModelFactory
import com.example.preservenaturetogether.viewmodels.SiteArticleViewModelFactory
import com.example.preservenaturetogether.viewmodels.SiteGalleryViewModelFactory

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
            categoryRepository = getCategoryRepository(context = context),
            siteRepository = getSiteRepository(context = context),
        )

    fun provideSiteGalleryViewModelFactory(context: Context) =
        SiteGalleryViewModelFactory(
            siteRepository = getSiteRepository(context = context),
        )

    fun provideSiteArticleViewModelFactory(context: Context) =
        SiteArticleViewModelFactory(
            categoryRepository = getCategoryRepository(context = context),
            ecoConditionRepository = getEcoConditionRepository(context = context),
            siteRepository = getSiteRepository(context = context),
        )
}