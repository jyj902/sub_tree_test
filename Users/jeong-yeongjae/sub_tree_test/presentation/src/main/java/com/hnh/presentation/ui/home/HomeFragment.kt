package com.hnh.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hnh.presentation.R
import com.hnh.presentation.databinding.FragmentHomeBinding
import com.hnh.presentation.ui.home.course.CourseAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!
    private var isFirst = false

    @Inject
    lateinit var mAdapter: CourseAdapter

    val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isFirst = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}