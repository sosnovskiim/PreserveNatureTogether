package com.example.preservenaturetogether.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.preservenaturetogether.R
import com.example.preservenaturetogether.adapters.DistrictListAdapter
import com.example.preservenaturetogether.databinding.FragmentDistrictListBinding
import com.example.preservenaturetogether.utilities.IS_DIALOG_NOT_SHOWN_ON_START
import com.example.preservenaturetogether.utilities.InjectorUtils
import com.example.preservenaturetogether.viewmodels.DistrictListViewModel

class DistrictListFragment : Fragment() {
    private val viewModel: DistrictListViewModel by viewModels {
        InjectorUtils.provideDistrictViewModelFactory(context = requireContext())
    }

    private lateinit var binding: FragmentDistrictListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = requireActivity().getSharedPreferences(
            resources.getString(R.string.app_name), MODE_PRIVATE
        )
        if (sharedPreferences.getBoolean(IS_DIALOG_NOT_SHOWN_ON_START, true)) {
            EcoConditionDialogFragment(sharedPreferencesKey = IS_DIALOG_NOT_SHOWN_ON_START)
                .show(childFragmentManager, EcoConditionDialogFragment.TAG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDistrictListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.districtList.addHeaderView(
            layoutInflater.inflate(
                R.layout.header_district_list,
                binding.districtList,
                false
            ),
            R.string.app_name,
            false
        )
        binding.districtList.setAdapter(
            DistrictListAdapter(
                fragment = this,
                districtList = viewModel.districtList,
                categoryList = viewModel.categoryList,
                siteList = viewModel.siteList,
            )
        )
    }
}