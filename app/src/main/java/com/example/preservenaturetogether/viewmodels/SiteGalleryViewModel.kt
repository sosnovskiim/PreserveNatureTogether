package com.example.preservenaturetogether.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.preservenaturetogether.data.Site
import com.example.preservenaturetogether.repository.SiteRepository

class SiteGalleryViewModel(
    private val siteRepository: SiteRepository,
) : ViewModel() {
    var siteList: List<Site> = listOf()

    fun setSiteList(categoryId: Int) {
        siteList = siteRepository.getSiteListByCategory(categoryId = categoryId)
    }
}

class SiteListItemViewModel(
    site: Site,
) : ViewModel() {
    val siteName: String = site.name
}

class SiteGalleryViewModelFactory(
    private val siteRepository: SiteRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        SiteGalleryViewModel(
            siteRepository = siteRepository,
        ) as T
}