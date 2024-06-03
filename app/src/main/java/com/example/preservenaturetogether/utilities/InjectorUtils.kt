package com.example.preservenaturetogether.utilities

import android.content.Context
import com.example.preservenaturetogether.repository.CategoryRepository
import com.example.preservenaturetogether.repository.DistrictRepository
import com.example.preservenaturetogether.repository.EcoConditionRepository
import com.example.preservenaturetogether.repository.RoleRepository
import com.example.preservenaturetogether.repository.SiteRepository
import com.example.preservenaturetogether.repository.UserRepository
import com.example.preservenaturetogether.viewmodels.DistrictListViewModelFactory
import com.example.preservenaturetogether.viewmodels.SiteArticleViewModelFactory
import com.example.preservenaturetogether.viewmodels.SiteGalleryViewModelFactory
import com.example.preservenaturetogether.viewmodels.UserSignInViewModelFactory
import com.example.preservenaturetogether.viewmodels.UserSignUpViewModelFactory

object InjectorUtils {
    private fun getUserRepository(context: Context) =
        UserRepository.getInstance(context = context)

    private fun getRoleRepository(context: Context) =
        RoleRepository.getInstance(context = context)

    private fun getDistrictRepository(context: Context) =
        DistrictRepository.getInstance(context = context)

    private fun getCategoryRepository(context: Context) =
        CategoryRepository.getInstance(context = context)

    private fun getEcoConditionRepository(context: Context) =
        EcoConditionRepository.getInstance(context = context)

    private fun getSiteRepository(context: Context) =
        SiteRepository.getInstance(context = context)

    fun provideUserSignInViewModelFactory(context: Context) =
        UserSignInViewModelFactory(
            userRepository = getUserRepository(context = context),
        )

    fun provideUserSignUpViewModelFactory(context: Context) =
        UserSignUpViewModelFactory(
            userRepository = getUserRepository(context = context),
        )

    fun provideDistrictViewModelFactory(context: Context) =
        DistrictListViewModelFactory(
            userRepository = getUserRepository(context = context),
            roleRepository = getRoleRepository(context = context),
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