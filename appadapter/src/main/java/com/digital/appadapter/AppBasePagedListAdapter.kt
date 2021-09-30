package com.digital.appadapter

import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class AppBasePagedListAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, RecyclerView.ViewHolder>(diffCallback), AppRecyclerAdapter<T> {

    override var callBack: OnItemClick<T>? = null
    val stateLD get()  = loadStateFlow.asLiveData()

    abstract fun onCreateBasicVH(parent: ViewGroup, viewType: Int): VH
    abstract fun onBindBasicVH(holder: VH, position: Int)

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return onCreateBasicVH(parent, viewType)
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindBasicVH(holder as VH, position)
    }

    final override fun getItemViewType(position: Int): Int {
        return getLayoutType(position)
    }

    final override fun getItemModel(pos: Int): T? = getItem(pos)

    /**
     * return your custom layout Type int.
     * */
    open fun getLayoutType(pos: Int): Int = -1

    fun setCallback(callback: OnItemClick<T>): AppBasePagedListAdapter<T, VH> {
        this.callBack = callback
        return this
    }
}