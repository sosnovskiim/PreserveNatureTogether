package com.example.preservenaturetogether.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.preservenaturetogether.data.Category
import com.example.preservenaturetogether.repository.CategoryRepository
import com.example.preservenaturetogether.data.District
import com.example.preservenaturetogether.repository.DistrictRepository
import com.example.preservenaturetogether.data.Site
import com.example.preservenaturetogether.data.User
import com.example.preservenaturetogether.repository.RoleRepository
import com.example.preservenaturetogether.repository.SiteRepository
import com.example.preservenaturetogether.repository.UserRepository

class DistrictListViewModel(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    districtRepository: DistrictRepository,
    categoryRepository: CategoryRepository,
    private val siteRepository: SiteRepository,
) : ViewModel() {
    private var user = User()
    val userLogin: String get() = user.login
    val userRole: String get() = roleRepository.getRole(roleId = user.roleId).name
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

    fun setUser(userId: Int) {
        user = userRepository.getUserById(userId = userId)
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
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val districtRepository: DistrictRepository,
    private val categoryRepository: CategoryRepository,
    private val siteRepository: SiteRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DistrictListViewModel(
            userRepository = userRepository,
            roleRepository = roleRepository,
            districtRepository = districtRepository,
            categoryRepository = categoryRepository,
            siteRepository = siteRepository,
        ) as T
}