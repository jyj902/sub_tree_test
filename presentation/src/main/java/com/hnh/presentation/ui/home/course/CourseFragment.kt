package com.hnh.presentation.ui.home.course

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hnh.presentation.R
import com.hnh.presentation.databinding.FragmentHomeCourseBinding
import com.hnh.presentation.ui.exercise.ExerciseListActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by hyerim on 2021/09/24...
 */

@AndroidEntryPoint
class CourseFragment : Fragment(R.layout.fragment_home_course) {
    private var _binding: FragmentHomeCourseBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var mAdapter: CourseAdapter

    val viewModel: CourseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeCourseBinding.bind(view)

        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init() {
        binding.recyclerViewCourse.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCourse.adapter = mAdapter

        viewModel.getExerciseBodyCourseList()

        subscribeToLiveData()

        mAdapter.setOnItemClickListener { item ->
            val intent = Intent(requireActivity(), ExerciseListActivity::class.java)
            intent.putExtra("EXERCISE_UID", item.id)
            intent.putExtra("EXERCISE_NAME", item.bodyPartName)
            startActivity(intent)
        }
    }

    private fun subscribeToLiveData() {
        viewModel.exerciseBodyCourse.observe(this) {
            binding.recyclerViewCourse.isVisible = it.isNotEmpty()
            binding.recyclerViewEmpty.isVisible = it.isEmpty()
            (binding.recyclerViewCourse.adapter as CourseAdapter).setItems(it)
            Log.d("testtest2", it.toString())
        }

    }
}