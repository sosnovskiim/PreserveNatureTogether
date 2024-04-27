package com.example.preservenaturetogether.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.preservenaturetogether.R
import com.example.preservenaturetogether.adapters.SiteListAdapter
import com.example.preservenaturetogether.databinding.FragmentSiteGalleryBinding
import com.example.preservenaturetogether.utilities.BUNDLE_KEY_DISTRICT_LIST_CATEGORY_ID
import com.example.preservenaturetogether.utilities.InjectorUtils
import com.example.preservenaturetogether.utilities.REQUEST_KEY_DISTRICT_LIST_CATEGORY_ID
import com.example.preservenaturetogether.viewmodels.SiteGalleryViewModel

class SiteGalleryFragment : Fragment() {
    private val viewModel: SiteGalleryViewModel by viewModels {
        InjectorUtils.provideSiteGalleryViewModelFactory(context = requireContext())
    }

    private lateinit var binding: FragmentSiteGalleryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSiteGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.siteList.addHeaderView(
            layoutInflater.inflate(
                R.layout.header_site_gallery,
                binding.siteList,
                false
            ),
            R.string.site_gallery_label,
            false
        )
        val adapter = SiteListAdapter(
            fragment = this,
            siteList = viewModel.siteList,
        )
        binding.siteList.adapter = adapter
        setFragmentResultListener(
            requestKey = REQUEST_KEY_DISTRICT_LIST_CATEGORY_ID
        ) { _, bundle ->
            viewModel.setSiteList(categoryId = bundle.getInt(BUNDLE_KEY_DISTRICT_LIST_CATEGORY_ID))
            adapter.siteList = viewModel.siteList
        }
    }
}