package com.hnh.presentation.ui.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hnh.presentation.R
import com.hnh.presentation.databinding.FragmentMyPageBinding
import com.hnh.presentation.util.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : Fragment(R.layout.fragment_my_page) {
    private var _binding: FragmentMyPageBinding? = null
    private val binding
        get() = _binding!!

    val viewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMyPageBinding.bind(view)

        initViewModel()
        subscribeToLiveData()
        initView()
    }

    private fun initViewModel() {
        val token = PreferenceUtil.preferenceInstance(requireActivity()).authToken
        if (token!!.length > 1) viewModel.getUserInfo(token)
    }

    private fun subscribeToLiveData() {
        viewModel.user.observe(this) {
            Log.d("testtest3", it.toString())
            binding.textviewName.text = it.name
            binding.textviewEmail.text = it.email
        }
    }


    private fun initView() {
        binding.containerNotice.setOnClickListener {
            makeSampleDialog()
        }

        binding.containerSetting.setOnClickListener {
            makeSampleDialog()
        }

        binding.containerFaq.setOnClickListener {
            makeSampleDialog()
        }
    }

    private fun makeSampleDialog() {
        MaterialAlertDialogBuilder(requireActivity())
            .setMessage("제공 예정인 기능입니다.")
            .setPositiveButton(resources.getString(R.string.all_ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}