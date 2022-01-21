package com.hnh.presentation.ui.exercise

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hnh.domain.entity.Exercise
import com.hnh.presentation.R
import com.hnh.presentation.databinding.ItemExerciseListBinding
import com.hnh.presentation.util.DIFFICULTY
import javax.inject.Inject

/**
 * Created by hyerim on 9/16/21...
 */
class ExerciseAdapter
@Inject
constructor() :
    RecyclerView.Adapter<ExerciseAdapter.CourseViewHolder>() {

    private val items = mutableListOf<Exercise>()
    private var onItemClickListener: ((Exercise) -> Unit)? = null
    fun setOnItemClickListener(listener: (Exercise) -> Unit) {
        onItemClickListener = listener
    }

    private var onItemSettingClickListener: ((Exercise) -> Unit)? = null
    fun setOnItemSettingClickListener(listener: (Exercise) -> Unit) {
        onItemSettingClickListener = listener
    }

    fun setItems(items: List<Exercise>) {
        this.items.clear()
        this.items.addAll(items)
        Log.d("testtest4", items.toString())
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return CourseViewHolder(
            ItemExerciseListBinding.inflate(layoutInflater)
        )
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val item = items[position]
        holder.bind(items[position])
        holder.binding.root.setOnClickListener {
            onItemClickListener?.let { clickCourse ->
                clickCourse(item)
            }
        }
        holder.binding.buttonSetting.setOnClickListener {
            onItemSettingClickListener?.let { clickExercise ->
                clickExercise(item)
            }
        }
    }

    class CourseViewHolder(
        val binding: ItemExerciseListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Exercise) {
            Log.d("testtest3", repo.toString())
            binding.textViewTitle.text = repo.title
            binding.textViewDescription.text = repo.description
            if (repo.difficulty in 1..3) { //TODO : 스프린트 후 수정 필요
                binding.chipDifficulty.text = DIFFICULTY[repo.difficulty - 1]
            }
            binding.chipAi.isChecked = repo.isAi
            binding.chipCalorie.text = "${repo.calorie}kcal"

            Glide.with(binding.root.context)
                .load(repo.thumbnailPath)
                .error(R.drawable.ic_thumbnail_fail)
                .centerCrop()
                .into(binding.imageViewCourse)
        }

    }


}

