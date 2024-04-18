package com.example.preservenaturetogether.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.preservenaturetogether.adapters.DistrictListAdapter
import com.example.preservenaturetogether.databinding.FragmentDistrictListBinding
import com.example.preservenaturetogether.utilities.InjectorUtils
import com.example.preservenaturetogether.viewmodels.DistrictListViewModel

class DistrictListFragment : Fragment() {
    private val viewModel: DistrictListViewModel by viewModels {
        InjectorUtils.provideDistrictViewModelFactory(context = requireContext())
    }

    private lateinit var binding: FragmentDistrictListBinding

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
        binding.districtList.adapter = DistrictListAdapter(
            districtList = viewModel.districtList,
            context = requireContext(),
            siteRepository = viewModel.siteRepository,
        )
    }
}