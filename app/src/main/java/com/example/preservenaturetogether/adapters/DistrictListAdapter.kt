package com.example.preservenaturetogether.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.BaseExpandableListAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.preservenaturetogether.R
import com.example.preservenaturetogether.data.Category
import com.example.preservenaturetogether.data.District
import com.example.preservenaturetogether.data.Site
import com.example.preservenaturetogether.databinding.ListChildItemCategoryBinding
import com.example.preservenaturetogether.databinding.ListChildItemSiteBinding
import com.example.preservenaturetogether.databinding.ListGroupItemDistrictBinding
import com.example.preservenaturetogether.utilities.BUNDLE_KEY_DISTRICT_LIST_CATEGORY_ID
import com.example.preservenaturetogether.utilities.BUNDLE_KEY_DISTRICT_LIST_SITE_ID
import com.example.preservenaturetogether.utilities.REQUEST_KEY_DISTRICT_LIST_CATEGORY_ID
import com.example.preservenaturetogether.utilities.REQUEST_KEY_DISTRICT_LIST_SITE_ID
import com.example.preservenaturetogether.viewmodels.CategoryListChildItemViewModel
import com.example.preservenaturetogether.viewmodels.DistrictListGroupItemViewModel
import com.example.preservenaturetogether.viewmodels.SiteListChildItemViewModel

class DistrictListAdapter(
    private val fragment: Fragment,
    private val districtList: List<District>,
    private val categoryList: List<Category>,
    private val siteList: List<List<Site>>,
) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return districtList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        if (groupPosition == 0) {
            return categoryList.size
        }
        return siteList[groupPosition].size
    }

    override fun getGroup(groupPosition: Int): Any {
        return districtList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        if (groupPosition == 0) {
            return categoryList[childPosition]
        }
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
        groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?
    ): View {
        val binding = ListGroupItemDistrictBinding.inflate(
            LayoutInflater.from(fragment.requireContext()), parent, false
        )
        binding.viewModel = DistrictListGroupItemViewModel(district = districtList[groupPosition])
        binding.districtPhoto.setImageResource(
            fragment.resources.getIdentifier(
                districtList[groupPosition].photo, "drawable", fragment.requireContext().packageName
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
        if (groupPosition == 0) {
            val binding = ListChildItemCategoryBinding.inflate(
                LayoutInflater.from(fragment.requireContext()), parent, false
            )
            binding.viewModel = CategoryListChildItemViewModel(
                category = categoryList[childPosition]
            )
            binding.categoryName.setOnClickListener {
                fragment.setFragmentResult(
                    requestKey = REQUEST_KEY_DISTRICT_LIST_CATEGORY_ID,
                    result = bundleOf(
                        Pair(
                            BUNDLE_KEY_DISTRICT_LIST_CATEGORY_ID,
                            categoryList[childPosition].id
                        )
                    )
                )
                fragment.findNavController().navigate(R.id.action_district_list_to_site_gallery)
            }
            return binding.root
        } else {
            val binding = ListChildItemSiteBinding.inflate(
                LayoutInflater.from(fragment.requireContext()), parent, false
            )
            binding.viewModel =
                SiteListChildItemViewModel(site = siteList[groupPosition][childPosition])
            binding.siteName.setOnClickListener {
                fragment.setFragmentResult(
                    requestKey = REQUEST_KEY_DISTRICT_LIST_SITE_ID,
                    result = bundleOf(
                        Pair(
                            BUNDLE_KEY_DISTRICT_LIST_SITE_ID,
                            siteList[groupPosition][childPosition].id
                        )
                    )
                )
                fragment.findNavController().navigate(R.id.action_district_list_to_site_article)
            }
            if (groupPosition % 2 != 0) {
                binding.siteName.gravity = Gravity.CENTER_VERTICAL or Gravity.END
            }
            return binding.root
        }
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}