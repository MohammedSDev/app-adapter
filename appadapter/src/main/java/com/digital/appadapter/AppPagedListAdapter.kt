package com.digital.appadapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import java.lang.IllegalArgumentException

// VH : AppViewHolder, LVH : AppViewHolder
open class AppPagedListAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>
) : AppBasePagedListAdapter<T, AppViewHolder, AppViewHolder>(diffCallback) {

    private var customBVH: Class<out AppViewHolder>? = null
    private var onBindL: (AppViewHolder.() -> Unit)? = null
    private var onBind: (AppViewHolder.(position: Int) -> Unit)? = null
    private var onCreateL: ((parent: ViewGroup, viewType: Int) -> AppViewHolder)? = null
    private var onCreate: ((parent: ViewGroup, viewType: Int) -> AppViewHolder)? = null
    private var layoutRes: Int = -1
    private var viewType: ((pos: Int) -> Int)? = null


    private inner class AppVH(v: View) : AppViewHolder(v) {
        override fun onBind(position: Int) {
            onBind?.invoke(this, position)
        }
    }

    private inner class AppLVH(v: View) : AppViewHolder(v) {
        override fun onBind(position: Int) {
            onBindL?.invoke(this)
        }
    }

    /**
     * LayoutRes constructor
     * */
    constructor(
        diffCallback: DiffUtil.ItemCallback<T>
        , @LayoutRes layoutRes: Int
        , customBVH: Class<out AppViewHolder>
    ) : this(
        diffCallback
    ) {
        this.layoutRes = layoutRes
        this.customBVH = customBVH
    }

    /**
     * Blocks constructor
     * */
    constructor(
        diffCallback: DiffUtil.ItemCallback<T>,
        onCreateVH: (parent: ViewGroup, viewType: Int) -> AppViewHolder
        , onCreateLVH: ((parent: ViewGroup, viewType: Int) -> AppViewHolder)? = null
    ) : this(diffCallback) {

        this.onCreate = onCreateVH
        this.onCreateL = onCreateLVH
    }


    /**
     * Blocks constructor
     * */
    constructor(
        diffCallback: DiffUtil.ItemCallback<T>,
        layoutRes: Int
        , onBindVH: (AppViewHolder.(position: Int) -> Unit)
        , onBindLVH: (AppViewHolder.() -> Unit)? = null

    ) : this(diffCallback) {

        this.layoutRes = layoutRes
        this.onBind = onBindVH
        this.onBindL = onBindLVH
    }


    /**
     * Blocks constructor
     * */
    constructor(
        diffCallback: DiffUtil.ItemCallback<T>,
        getViewType: (pos: Int) -> Int
        ,onCreateVH: (parent: ViewGroup, viewType: Int) -> AppViewHolder
        , onCreateLVH: ((parent: ViewGroup, viewType: Int) -> AppViewHolder)? = null
    ) : this(diffCallback) {

        this.onCreate = onCreateVH
        this.onCreateL = onCreateLVH
        this.viewType = getViewType
    }


    private fun onCreateBasicVHFromLayoutRes(
        parent: ViewGroup,
        customBVH: Class<out AppViewHolder>
    ): AppViewHolder {
        val view = parent.appInflate(layoutRes)
        return customBVH.getConstructor(View::class.java).newInstance(view)
    }

    private fun onCreateBasicVHFromLayoutRes(
        parent: ViewGroup
    ): AppViewHolder {
        val view = parent.appInflate(layoutRes)
        return AppVH(view)
    }

    override fun onCreateBasicVH(parent: ViewGroup, viewType: Int): AppViewHolder {
        val customVH = customBVH
        return onCreate?.invoke(parent, viewType) ?: if (layoutRes != -1)
            if (customVH != null)
                onCreateBasicVHFromLayoutRes(parent, customVH)
            else
                onCreateBasicVHFromLayoutRes(parent)
        else
            throw
            IllegalArgumentException("you have to pass `onCreateVH` or override `onCreateBasicVH` function. ")
    }

    final override fun onBindBasicVH(holder: AppViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateLVH(parent: ViewGroup, viewType: Int): AppViewHolder {
        return this.onCreateL?.invoke(parent, viewType) ?: AppLVH(View(parent.context))
    }

    final override fun onBindLVH(holder: AppViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getLayoutType(pos: Int): Int {
        return viewType?.invoke(pos) ?: super.getLayoutType(pos)
    }
}