package com.example.preservenaturetogether.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.preservenaturetogether.data.Category
import com.example.preservenaturetogether.repository.CategoryRepository
import com.example.preservenaturetogether.data.District
import com.example.preservenaturetogether.repository.DistrictRepository
import com.example.preservenaturetogether.data.Site
import com.example.preservenaturetogether.repository.SiteRepository

class DistrictListViewModel(
    districtRepository: DistrictRepository,
    categoryRepository: CategoryRepository,
    private val siteRepository: SiteRepository,
) : ViewModel() {
    val districtList: List<District> = districtRepository.getDistrictList()
    val categoryList: List<Category> = categoryRepository.getCategoryList()
    val siteList: List<List<Site>>
        get() {
            val result: MutableList<List<Site>> = mutableListOf()
            districtList.forEach { district: District ->
                result.add(siteRepository.getSiteListByDistrict(districtId = district.id))
            }
            return result
        }
}

class DistrictListGroupItemViewModel(
    district: District,
) : ViewModel() {
    val districtName: String = district.name
}

class CategoryListChildItemViewModel(
    category: Category,
) : ViewModel() {
    val categoryName: String = category.name
}

class SiteListChildItemViewModel(
    site: Site,
) : ViewModel() {
    val siteName: String = site.name
}

class DistrictListViewModelFactory(
    private val districtRepository: DistrictRepository,
    private val categoryRepository: CategoryRepository,
    private val siteRepository: SiteRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DistrictListViewModel(
            districtRepository = districtRepository,
            categoryRepository = categoryRepository,
            siteRepository = siteRepository,
        ) as T
}