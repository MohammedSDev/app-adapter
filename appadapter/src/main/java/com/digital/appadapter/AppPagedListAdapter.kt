package com.digital.appadapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class AppPagedListAdapter<T, VH : AppViewHolder, LVH : AppViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>
) : AppBasePagedListAdapter<T, VH, LVH>(diffCallback) {


    constructor(diffCallback: DiffUtil.ItemCallback<T>,onBind:()->Unit):this(diffCallback)





}