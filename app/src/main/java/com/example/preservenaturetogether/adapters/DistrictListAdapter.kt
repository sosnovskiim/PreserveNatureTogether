package com.example.preservenaturetogether.adapters

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.BaseExpandableListAdapter
import com.example.preservenaturetogether.data.District
import com.example.preservenaturetogether.data.Site
import com.example.preservenaturetogether.databinding.ListItemDistrictBinding
import com.example.preservenaturetogether.databinding.ListItemSiteBinding
import com.example.preservenaturetogether.viewmodels.DistrictListItemViewModel
import com.example.preservenaturetogether.viewmodels.SiteListItemViewModel

class DistrictListAdapter(
    private val context: Context,
    private val districtList: List<District>,
    private val siteList: List<List<Site>>,
) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return districtList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return siteList[groupPosition].size
    }

    override fun getGroup(groupPosition: Int): Any {
        return districtList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return siteList[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding = ListItemDistrictBinding.inflate(
            LayoutInflater.from(parent!!.context), parent, false
        )
        binding.viewModel = DistrictListItemViewModel(district = districtList[groupPosition])
        binding.districtPhoto.setImageResource(
            context.resources.getIdentifier(
                districtList[groupPosition].photo,
                "drawable",
                context.packageName
            )
        )
        val photoParams = binding.districtPhoto.layoutParams as MarginLayoutParams
        val nameParams = binding.districtName.layoutParams as MarginLayoutParams
        if (groupPosition % 2 != 0) {
            photoParams.leftMargin = 100
            nameParams.leftMargin = 100
        } else {
            photoParams.rightMargin = 100
            nameParams.rightMargin = 100
        }
        return binding.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding = ListItemSiteBinding.inflate(
            LayoutInflater.from(parent!!.context), parent, false
        )
        binding.viewModel = SiteListItemViewModel(site = siteList[groupPosition][childPosition])
        if (groupPosition % 2 != 0) {
            binding.siteName.gravity = Gravity.CENTER_VERTICAL or Gravity.END
        }
        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}