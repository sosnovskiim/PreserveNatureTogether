package com.example.preservenaturetogether.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.preservenaturetogether.R
import com.example.preservenaturetogether.adapters.DistrictListAdapter
import com.example.preservenaturetogether.databinding.FragmentDistrictListBinding
import com.example.preservenaturetogether.utilities.BUNDLE_KEY_USER_SIGN_IN_USER_ID
import com.example.preservenaturetogether.utilities.CURRENT_USER_ID
import com.example.preservenaturetogether.utilities.InjectorUtils
import com.example.preservenaturetogether.utilities.REQUEST_KEY_USER_SIGN_IN_USER_ID
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
        if (viewModel.userLogin.isNotEmpty()) {
            binding.userInfoLogin.text = viewModel.userLogin
            binding.userInfoRole.text = viewModel.userRole
        }
        setFragmentResultListener(
            requestKey = REQUEST_KEY_USER_SIGN_IN_USER_ID
        ) { _, bundle ->
            viewModel.setUser(userId = bundle.getInt(BUNDLE_KEY_USER_SIGN_IN_USER_ID))
            binding.userInfoLogin.text = viewModel.userLogin
            binding.userInfoRole.text = viewModel.userRole
        }
        binding.userLogOutButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Выход из аккаунта")
                .setMessage("Вы действительно хотите выйти из аккаунта?")
                .setPositiveButton("Да") { _, _ ->
                    val sharedPreferences = requireActivity().getSharedPreferences(
                        resources.getString(R.string.app_name), Context.MODE_PRIVATE
                    )
                    sharedPreferences.edit().putInt(CURRENT_USER_ID, 0).apply()
                    findNavController().navigate(R.id.action_district_list_to_user_sign_in)
                }
                .setNegativeButton("Нет") { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
            builder.show()
        }
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