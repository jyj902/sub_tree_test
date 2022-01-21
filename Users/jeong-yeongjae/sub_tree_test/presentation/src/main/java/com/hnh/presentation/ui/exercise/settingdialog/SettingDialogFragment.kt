package com.hnh.presentation.ui.exercise.settingdialog

import android.R.attr
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.hnh.presentation.R
import com.hnh.presentation.databinding.FragmentSettingDialogBinding
import com.hnh.presentation.util.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import android.R.attr.enabled
import android.content.Intent
import com.hnh.presentation.util.EXERCISE_INFORMATION
import com.hnh.presentation.util.EXERCISE_LIST


@AndroidEntryPoint
class SettingDialogFragment : DialogFragment(R.layout.fragment_setting_dialog) {
    private var _binding: FragmentSettingDialogBinding? = null
    private val binding
        get() = _binding!!

    var mode = -1
    var sets = 0
    var reps = 0
    var restTime = 0
    var exerciseId = ""

    //TODO:운동별 적용 필요
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false

        exerciseId = requireArguments().getString("exerciseId", "")

        mode = PreferenceUtil.preferenceInstance(requireContext())
            .getExerciseSetting(exerciseId, EXERCISE_INFORMATION.SETTING_MODE)
        sets = PreferenceUtil.preferenceInstance(requireContext())
            .getExerciseSetting(exerciseId, EXERCISE_INFORMATION.SETTING_SET)
        reps = PreferenceUtil.preferenceInstance(requireContext())
            .getExerciseSetting(exerciseId, EXERCISE_INFORMATION.SETTING_REPS)
        restTime = PreferenceUtil.preferenceInstance(requireContext())
            .getExerciseSetting(exerciseId, EXERCISE_INFORMATION.SETTING_REST_TIME)

        Log.d("test", "exerciseId : $exerciseId")

        initData()
        initView()
    }

    private fun initData() {
        binding.radioButtonFree.isChecked = (mode == FREE_MODE)
        binding.radioButtonSets.isChecked = (mode == 0 || mode == SETS_MODE)
        changeSetsLayoutEnable(mode != FREE_MODE)

        val setList: List<Int> = (1..10).toList()
        val setAdapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, setList)
        binding.spinnerSets.adapter = setAdapter
        binding.spinnerSets.setSelection(sets - 1)

        val repsList: List<Int> = (5..40).toList()
        val repsAdapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, repsList)
        binding.spinnerReps.adapter = repsAdapter
        binding.spinnerReps.setSelection(reps - 5)

        //TODO:휴식시간 코드 수정 필요
        val restTimeList = resources.getStringArray(R.array.spinner_rest_time)
        val restTimeAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            restTimeList
        )
        binding.spinnerRestTime.adapter = restTimeAdapter
        binding.spinnerRestTime.setSelection((restTime / REST_TIME_UNIT) - 1)

    }

    private fun changeSetsLayoutEnable(isEnabled: Boolean) {
        val childCount: Int = binding.layoutSetMode.childCount
        for (i in 0 until childCount) {
            val view: View = binding.layoutSetMode.getChildAt(i)
            view.isEnabled = isEnabled
        }
    }

    private fun initView() {
        binding.buttonClose.setOnClickListener {
            dismiss()
        }

        binding.radioGroupMode.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_button_sets -> {
                    mode = SETS_MODE
                    changeSetsLayoutEnable(true)
                }
                R.id.radio_button_free -> {
                    mode = FREE_MODE
                    changeSetsLayoutEnable(false)
                }
            }
        }

        binding.buttonReset.setOnClickListener {
            mode = 1
            sets = 3
            reps = 20
            restTime = 40000

            changeSetsLayoutEnable(true)
            binding.radioButtonSets.isChecked = true
            binding.spinnerSets.setSelection(sets - 1)
            binding.spinnerReps.setSelection(reps - 5)
            binding.spinnerRestTime.setSelection((restTime / REST_TIME_UNIT) - 1)
        }

        binding.buttonApply.setOnClickListener {
            if (mode != FREE_MODE) {
                PreferenceUtil.preferenceInstance(requireContext())
                    .setExerciseSetting(exerciseId, EXERCISE_INFORMATION.SETTING_SET, binding.spinnerSets.selectedItem.toString().toInt() )
               PreferenceUtil.preferenceInstance(requireContext())
                    .setExerciseSetting(exerciseId, EXERCISE_INFORMATION.SETTING_REPS, binding.spinnerReps.selectedItem.toString().toInt())
                PreferenceUtil.preferenceInstance(requireContext())
                    .setExerciseSetting(exerciseId, EXERCISE_INFORMATION.SETTING_REST_TIME,(binding.spinnerRestTime.selectedItemPosition + 1) * REST_TIME_UNIT)
            }

            PreferenceUtil.preferenceInstance(requireContext())
                .setExerciseSetting(exerciseId, EXERCISE_INFORMATION.SETTING_MODE, mode)
            dismiss()
        }

    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 0.9f, 0.7f)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun Context.dialogFragmentResize(
        dialogFragment: DialogFragment,
        width: Float,
        height: Float
    ) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30) {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val window = dialogFragment.dialog?.window
            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()
            window?.setLayout(x, y)
        } else {
            val rect = windowManager.currentWindowMetrics.bounds
            val window = dialogFragment.dialog?.window
            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()
            window?.setLayout(x, y)
            window?.setBackgroundDrawableResource(R.drawable.bg_dialog_rounded)
        }
    }

    companion object {
        const val SETS_MODE = 1
        const val FREE_MODE = 2
        const val REST_TIME_UNIT = 5000
    }

}
