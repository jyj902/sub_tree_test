package com.hnh.presentation.ui.home.course

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hnh.domain.entity.ExerciseBody
import com.hnh.presentation.R
import com.hnh.presentation.databinding.ItemHomeCourseBinding
import javax.inject.Inject

/**
 * Created by hyerim on 9/16/21...
 */
class CourseAdapter
@Inject
constructor() :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    private val items = mutableListOf<ExerciseBody>()
    private var onItemClickListener: ((ExerciseBody) -> Unit)? = null
    fun setOnItemClickListener(listener: (ExerciseBody) -> Unit) {
        onItemClickListener = listener
    }

    fun setItems(items: List<ExerciseBody>) {
        this.items.clear()
        this.items.addAll(items)
        Log.d("testtest4", items.toString())
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return CourseViewHolder(
            ItemHomeCourseBinding.inflate(layoutInflater)
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
    }

    class CourseViewHolder(
        val binding: ItemHomeCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: ExerciseBody) {
            Log.d("testtest3", repo.toString())
            binding.textViewCourse.text = repo.bodyPartName

            Glide.with(binding.root.context)
                .load(repo.thumbnailPath)
                .error(R.drawable.ic_thumbnail_fail)
                .centerCrop()
                .into(binding.imageViewCourse)
        }
    }

}

