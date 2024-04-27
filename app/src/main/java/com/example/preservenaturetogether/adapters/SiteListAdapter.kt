package com.example.preservenaturetogether.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.preservenaturetogether.R
import com.example.preservenaturetogether.data.Site
import com.example.preservenaturetogether.databinding.ListItemSiteBinding
import com.example.preservenaturetogether.utilities.BUNDLE_KEY_SITE_GALLERY_SITE_ID
import com.example.preservenaturetogether.utilities.REQUEST_KEY_SITE_GALLERY_SITE_ID
import com.example.preservenaturetogether.viewmodels.SiteListItemViewModel

class SiteListAdapter(
    private val fragment: Fragment,
    var siteList: List<Site>,
) : BaseAdapter() {
    override fun getCount(): Int {
        return siteList.size
    }

    override fun getItem(position: Int): Any {
        return siteList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ListItemSiteBinding.inflate(
            LayoutInflater.from(fragment.requireContext()), parent, false
        )
        binding.viewModel = SiteListItemViewModel(site = siteList[position])
        binding.siteItem.setOnClickListener {
            fragment.setFragmentResult(
                requestKey = REQUEST_KEY_SITE_GALLERY_SITE_ID,
                result = bundleOf(
                    Pair(
                        BUNDLE_KEY_SITE_GALLERY_SITE_ID,
                        siteList[position].id
                    )
                )
            )
            fragment.findNavController().navigate(R.id.action_site_gallery_to_site_article)
        }
        binding.sitePhoto1.setImageResource(
            fragment.resources.getIdentifier(
                siteList[position].photo1, "drawable", fragment.requireContext().packageName
            )
        )
        if (siteList[position].photo2.isNotEmpty()) {
            binding.sitePhoto2.visibility = View.VISIBLE
            binding.sitePhoto2.setImageResource(
                fragment.resources.getIdentifier(
                    siteList[position].photo2, "drawable", fragment.requireContext().packageName
                )
            )
        }
        return binding.root
    }
}