package com.example.preservenaturetogether.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.preservenaturetogether.data.District
import com.example.preservenaturetogether.data.DistrictRepository
import com.example.preservenaturetogether.data.Site
import com.example.preservenaturetogether.data.SiteRepository

class DistrictListViewModel(
    districtRepository: DistrictRepository,
    val siteRepository: SiteRepository,
) : ViewModel() {
    val districtList: List<District> = districtRepository.getDistrictList()
}

class DistrictListItemViewModel(
    private val district: District,
) : ViewModel() {
    val districtName: String get() = district.name
}

class SiteListItemViewModel(
    private val site: Site,
) : ViewModel() {
    val siteName: String get() = site.name
}

class DistrictListViewModelFactory(
    private val districtRepository: DistrictRepository,
    private val siteRepository: SiteRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        DistrictListViewModel(
            districtRepository,
            siteRepository,
        ) as T
}