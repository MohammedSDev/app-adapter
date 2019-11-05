package com.digital.appadapter

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class AppBasePagedListAdapter<T, VH : RecyclerView.ViewHolder, LVH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>
) :
    PagedListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    val LOODER_TYPE = 2
    val BASIC_VIEW_TYPE = 3
    var mEnableFooter =
        false //this in init list, to determine increase `itemCount` size by `1` or not
        private set
    var mShowFooter = false//this to show/hide loadMore Progress
    var messageRetryLoadMore = ""
    var mLoadMoreErrorOccur = false

    var callBack: OnItemClick<T>? = null


    abstract fun onCreateBasicVH(parent: ViewGroup, viewType: Int): VH
    abstract fun onBindBasicVH(holder: VH, position: Int)

    abstract fun onCreateLVH(parent: ViewGroup, viewType: Int): LVH
    abstract fun onBindLVH(holder: LVH, position: Int)

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            LOODER_TYPE -> {
                onCreateLVH(parent, viewType)
            }
//            BASIC_VIEW_TYPE -> {
//                onCreateBasicVH(parent, viewType)
//            }
            else -> {
                onCreateBasicVH(parent, viewType)
            }
        }
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            LOODER_TYPE -> {
                if (!mShowFooter) {
                    //hide item
                    holder.itemView.visibility = View.GONE
                } else {
                    if (holder.itemView.visibility != View.VISIBLE)
                        holder.itemView.visibility = View.VISIBLE
                    onBindLVH(holder as LVH, position)
                }
            }
//            BASIC_VIEW_TYPE -> {
//                onBindBasicVH(holder as VH, position)
//            }
            else -> {
                onBindBasicVH(holder as VH, position)
            }
        }
    }


    final override fun getItemCount(): Int {
        var size = super.getItemCount()

        return if (mEnableFooter)
            ++size
        else
            size
    }

    final override fun getItemViewType(position: Int): Int {
        return if (position >= currentList?.size ?: 0)
            LOODER_TYPE
        else
            getLayoutType(position)
    }


    fun enableFooter() {
        mEnableFooter = true
    }



    /**
     * return your custom layout Type int.
     * */
    open fun getLayoutType(pos:Int):Int = BASIC_VIEW_TYPE


}