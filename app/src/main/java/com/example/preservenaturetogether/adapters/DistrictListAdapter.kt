package com.example.preservenaturetogether.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ArrayAdapter
import com.example.preservenaturetogether.data.District
import com.example.preservenaturetogether.data.SiteRepository
import com.example.preservenaturetogether.databinding.ListItemDistrictBinding
import com.example.preservenaturetogether.viewmodels.DistrictListItemViewModel

class DistrictListAdapter(
    private val districtList: List<District>,
    private val context: Context,
    private val siteRepository: SiteRepository,
) : ArrayAdapter<District>(context, 0, districtList) {
    @SuppressLint("ViewHolder", "DiscouragedApi")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ListItemDistrictBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        binding.viewModel = DistrictListItemViewModel(district = districtList[position])
        binding.districtPhoto.setImageResource(
            context.resources.getIdentifier(
                districtList[position].photo,
                "drawable",
                context.packageName
            )
        )
        binding.siteList.adapter = SiteListAdapter(
            siteList = siteRepository.getSiteListByDistrict(
                districtId = districtList[position].id
            ),
            context = context,
        )
        val photoParams = binding.districtPhoto.layoutParams as MarginLayoutParams
        val nameParams = binding.districtName.layoutParams as MarginLayoutParams
        if (position % 2 == 0) {
            photoParams.rightMargin = 100
            nameParams.rightMargin = 100
        } else {
            photoParams.leftMargin = 100
            nameParams.leftMargin = 100
        }
        return binding.root
    }

    override fun getCount(): Int = districtList.size
}