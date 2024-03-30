package com.example.preservenaturetogether.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.preservenaturetogether.data.District
import com.example.preservenaturetogether.data.DistrictRepository

class DistrictListViewModel(
    districtRepository: DistrictRepository,
) : ViewModel() {
    val districtList: List<District> = districtRepository.getDistrictList()
}

class DistrictListItemViewModel(
    private val district: District,
) : ViewModel() {
    val districtName: String get() = district.name
}

class DistrictListViewModelFactory(
    private val districtRepository: DistrictRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        DistrictListViewModel(districtRepository) as T
}