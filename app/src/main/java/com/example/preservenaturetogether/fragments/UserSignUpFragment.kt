package com.example.preservenaturetogether.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.preservenaturetogether.data.User
import com.example.preservenaturetogether.databinding.FragmentUserSignUpBinding
import com.example.preservenaturetogether.utilities.InjectorUtils
import com.example.preservenaturetogether.viewmodels.UserSignUpViewModel

class UserSignUpFragment : Fragment() {
    private val viewModel: UserSignUpViewModel by viewModels {
        InjectorUtils.provideUserSignUpViewModelFactory(context = requireContext())
    }

    private lateinit var binding: FragmentUserSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userSignUpButton.setOnClickListener {
            val userLogin: String = binding.userInputLogin.text.toString()
            val userPassword: String = binding.userInputPassword.text.toString()
            val userPasswordRepeat: String = binding.userInputPasswordRepeat.text.toString()
            if (userLogin.isBlank() || userPassword.isBlank() || userPasswordRepeat.isBlank()) {
                Toast.makeText(
                    requireContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT
                ).show()
            } else if (userPassword != userPasswordRepeat) {
                Toast.makeText(
                    requireContext(), "Пароли не совпадают", Toast.LENGTH_SHORT
                ).show()
            } else {
                val user: User? = viewModel.getUser(userLogin = userLogin)
                user?.let {
                    Toast.makeText(
                        requireContext(), "Такой логин уже существует", Toast.LENGTH_SHORT
                    ).show()
                } ?: run {
                    viewModel.addUser(userLogin = userLogin, userPassword = userPassword)
                    Toast.makeText(
                        requireContext(), "Вы успешно зарегистрированы", Toast.LENGTH_SHORT
                    ).show()
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }
}