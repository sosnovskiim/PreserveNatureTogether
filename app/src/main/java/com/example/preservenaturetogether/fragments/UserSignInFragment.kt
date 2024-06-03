package com.example.preservenaturetogether.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.preservenaturetogether.R
import com.example.preservenaturetogether.data.User
import com.example.preservenaturetogether.databinding.FragmentUserSignInBinding
import com.example.preservenaturetogether.utilities.BUNDLE_KEY_USER_SIGN_IN_USER_ID
import com.example.preservenaturetogether.utilities.CURRENT_USER_ID
import com.example.preservenaturetogether.utilities.InjectorUtils
import com.example.preservenaturetogether.utilities.REQUEST_KEY_USER_SIGN_IN_USER_ID
import com.example.preservenaturetogether.viewmodels.UserSignInViewModel

class UserSignInFragment : Fragment() {
    private val viewModel: UserSignInViewModel by viewModels {
        InjectorUtils.provideUserSignInViewModelFactory(context = requireContext())
    }

    private lateinit var binding: FragmentUserSignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = requireActivity().getSharedPreferences(
            resources.getString(R.string.app_name), Context.MODE_PRIVATE
        )
        val currentUserId: Int = sharedPreferences.getInt(CURRENT_USER_ID, 0)
        if (currentUserId != 0) {
            setFragmentResult(
                requestKey = REQUEST_KEY_USER_SIGN_IN_USER_ID,
                result = bundleOf(BUNDLE_KEY_USER_SIGN_IN_USER_ID to currentUserId)
            )
            findNavController().navigate(R.id.action_user_sign_in_to_district_list)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireActivity().getSharedPreferences(
            resources.getString(R.string.app_name), Context.MODE_PRIVATE
        )
        binding.userSignInButton.setOnClickListener {
            val user: User? = viewModel.getUser(
                userLogin = binding.userInputLogin.text.toString(),
                userPassword = binding.userInputPassword.text.toString(),
            )
            user?.let {
                sharedPreferences.edit().putInt(CURRENT_USER_ID, user.id).apply()
                binding.userInputLogin.text.clear()
                binding.userInputPassword.text.clear()
                Toast.makeText(
                    requireContext(), R.string.user_sign_in_success, Toast.LENGTH_SHORT
                ).show()
                setFragmentResult(
                    requestKey = REQUEST_KEY_USER_SIGN_IN_USER_ID,
                    result = bundleOf(BUNDLE_KEY_USER_SIGN_IN_USER_ID to user.id)
                )
                findNavController().navigate(R.id.action_user_sign_in_to_district_list)
            } ?: run {
                Toast.makeText(
                    requireContext(), R.string.user_sign_in_error, Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.userSignUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_user_sign_in_to_user_sign_up)
        }
    }
}