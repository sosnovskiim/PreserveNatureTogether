package com.example.preservenaturetogether.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.preservenaturetogether.data.Site
import com.example.preservenaturetogether.databinding.ListItemSiteBinding
import com.example.preservenaturetogether.viewmodels.SiteListItemViewModel

class SiteListAdapter(
    private val siteList: List<Site>,
    private val context: Context,
) : ArrayAdapter<Site>(context, 0, siteList) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ListItemSiteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        binding.viewModel = SiteListItemViewModel(site = siteList[position])
        return binding.root
    }

    override fun getCount(): Int = siteList.size
}