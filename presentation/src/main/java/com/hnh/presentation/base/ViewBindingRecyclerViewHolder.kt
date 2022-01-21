package com.hnh.presentation.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by hyerim on 2021/09/24...
 */
open class ViewBindingRecyclerViewHolder<T : ViewDataBinding>(
    val binding: T
) : RecyclerView.ViewHolder(
    binding.root
)