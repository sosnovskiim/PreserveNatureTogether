package com.example.preservenaturetogether.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.preservenaturetogether.R
import com.example.preservenaturetogether.databinding.DialogFragmentEcoConditionBinding
import com.example.preservenaturetogether.utilities.IS_DIALOG_NOT_SHOWN_IN_ARTICLE
import com.example.preservenaturetogether.utilities.IS_DIALOG_NOT_SHOWN_ON_START

class EcoConditionDialogFragment(
    private val sharedPreferencesKey: String
) : DialogFragment() {
    private lateinit var binding: DialogFragmentEcoConditionBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentEcoConditionBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireContext()).setView(binding.root).create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setCanceledOnTouchOutside(false)
        val sharedPreferences = requireActivity().getSharedPreferences(
            resources.getString(R.string.app_name), Context.MODE_PRIVATE
        )
        binding.confirmButton.setOnClickListener {
            when (sharedPreferencesKey) {
                IS_DIALOG_NOT_SHOWN_ON_START -> if (
                    sharedPreferences.getBoolean(IS_DIALOG_NOT_SHOWN_ON_START, true)
                ) {
                    sharedPreferences
                        .edit()
                        .putBoolean(IS_DIALOG_NOT_SHOWN_ON_START, false)
                        .apply()
                }

                IS_DIALOG_NOT_SHOWN_IN_ARTICLE -> if (
                    sharedPreferences.getBoolean(IS_DIALOG_NOT_SHOWN_IN_ARTICLE, true)
                ) {
                    sharedPreferences
                        .edit()
                        .putBoolean(IS_DIALOG_NOT_SHOWN_IN_ARTICLE, false)
                        .apply()
                }
            }
            dismiss()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        const val TAG = "EcoConditionDialog"
    }
}