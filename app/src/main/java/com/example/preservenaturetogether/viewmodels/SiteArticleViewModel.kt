package com.example.preservenaturetogether.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.preservenaturetogether.data.Category.Companion.HISTORICAL
import com.example.preservenaturetogether.data.Category.Companion.NATURAL
import com.example.preservenaturetogether.data.Category.Companion.RECREATIONAL
import com.example.preservenaturetogether.data.EcoCondition.Companion.FAIR
import com.example.preservenaturetogether.data.EcoCondition.Companion.GOOD
import com.example.preservenaturetogether.data.EcoCondition.Companion.POOR
import com.example.preservenaturetogether.data.Site
import com.example.preservenaturetogether.repository.CategoryRepository
import com.example.preservenaturetogether.repository.EcoConditionRepository
import com.example.preservenaturetogether.repository.SiteRepository

class SiteArticleViewModel(
    private val categoryRepository: CategoryRepository,
    private val ecoConditionRepository: EcoConditionRepository,
    private val siteRepository: SiteRepository,
) : ViewModel() {
    private var site = Site()
    private val siteCategoryName: String
        get() = when (
            categoryRepository.getCategory(categoryId = site.categoryId).name
        ) {
            HISTORICAL -> "historical"
            NATURAL -> "natural"
            RECREATIONAL -> "recreational"
            else -> ""
        }
    private val siteEcoConditionName: String
        get() = when (
            ecoConditionRepository.getEcoCondition(ecoConditionId = site.ecoConditionId).name
        ) {
            POOR -> "poor"
            FAIR -> "fair"
            GOOD -> "good"
            else -> ""
        }

    val siteIconName: String
        get() = if (siteCategoryName.isNotEmpty() && siteEcoConditionName.isNotEmpty()) {
            "${siteCategoryName}_${siteEcoConditionName}"
        } else {
            ""
        }
    val siteName: String get() = site.name
    val siteDescription: String get() = site.description
    val siteSuggestion: String get() = site.suggestion
    val sitePhoto1: String get() = site.photo1
    val sitePhoto2: String get() = site.photo2
    val siteCoordinates: String
        get() = "${site.latitude}, ${site.longitude}"
    val siteLatitude: Double get() = site.latitude
    val siteLongitude: Double get() = site.longitude

    fun setSite(siteId: Int) {
        site = siteRepository.getSite(siteId = siteId)
    }
}

class SiteArticleViewModelFactory(
    private val categoryRepository: CategoryRepository,
    private val ecoConditionRepository: EcoConditionRepository,
    private val siteRepository: SiteRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        SiteArticleViewModel(
            categoryRepository = categoryRepository,
            ecoConditionRepository = ecoConditionRepository,
            siteRepository = siteRepository,
        ) as T
}