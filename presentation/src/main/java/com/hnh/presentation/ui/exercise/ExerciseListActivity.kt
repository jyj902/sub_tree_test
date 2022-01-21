package com.hnh.presentation.ui.exercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hnh.presentation.R
import com.hnh.presentation.databinding.ActivityExerciseListBinding
import com.hnh.presentation.ui.exercise.settingdialog.SettingDialogFragment
import com.hnh.presentation.ui.posedetector.PoseDetectorActivity
import com.hnh.presentation.util.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExerciseListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseListBinding
    private var courseUid = 0L

    val viewModel: ExerciseListViewModel by viewModels()

    @Inject
    lateinit var mAdapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        courseUid = intent.getLongExtra("EXERCISE_UID", 1)
        val name = intent.getStringExtra("EXERCISE_NAME")
        binding.topAppBar.title = name

        initView()
        initViewModel()
        subscribeToLiveData()

    }

    private fun initViewModel() {
        viewModel.getExerciseBodyCourse(courseUid)
        viewModel.getExerciseList(courseUid)
    }

    private fun subscribeToLiveData() {
        viewModel.exerciseBodyCourse.observe(this) {
            Log.d("testtest3", it.toString())

            Glide.with(this)
                .load(it.thumbnailPath)
                .error(R.color.design_default_color_surface)
                .centerCrop()
                .into(binding.imageMain)
        }

        viewModel.exerciseList.observe(this) {
            Log.d("exerciseList", it.toString())
            (binding.recyclerViewCourse.adapter as ExerciseAdapter).setItems(it)

            if (it.isEmpty()) {
                binding.recyclerViewCourse.visibility = View.GONE
                binding.recyclerViewEmpty.visibility = View.VISIBLE
            } else {
                binding.recyclerViewCourse.visibility = View.VISIBLE
                binding.recyclerViewEmpty.visibility = View.GONE
            }
        }
    }

    private fun initView() {
        binding.recyclerViewCourse.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewCourse.adapter = mAdapter

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

//        PreferenceUtil.preferenceInstance(this).squatModeFree = 0
//        PreferenceUtil.preferenceInstance(this).squatSet = 2
//        PreferenceUtil.preferenceInstance(this).squatRestTime = 10000
//        PreferenceUtil.preferenceInstance(this).squatExerciseReps = 3

        mAdapter.setOnItemClickListener { item ->
            val intent = Intent(this, PoseDetectorActivity::class.java)
            intent.putExtra("poseId", item.exerciseName)
            intent.putExtra("videoPath", item.videoPath)
            startActivity(intent)
        }


        mAdapter.setOnItemSettingClickListener {
            Log.d("ExerciseList",it.exerciseName)
            showDialog(it.exerciseName)
        }
    }

    private fun showDialog(exerciseName: String) {
        SettingDialogFragment().apply {
            arguments = Bundle().apply {
                putString("exerciseId", exerciseName)
            } }
            .show(supportFragmentManager, "exercise setting")
    }
}