package com.digital.appadapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import java.lang.IllegalArgumentException

// VH : AppViewHolder, LVH : AppViewHolder
open class AppPagedListAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>
) : AppBasePagedListAdapter<T, AppViewHolder<T>, AppViewHolder<Any>>(diffCallback) {

    private var customBVH: Class<out AppViewHolder<T>>? = null
    private var onBindL: (AppViewHolder<Any>.() -> Unit)? = null
    private var onBind: (AppViewHolder<T>.(item: T?) -> Unit)? = null
    private var onCreateL: ((parent: ViewGroup, viewType: Int) -> AppViewHolder<Any>)? = null
    private var onCreate: ((parent: ViewGroup, viewType: Int) -> AppViewHolder<T>)? = null
    private var layoutRes: Int = -1
    private var viewType: ((pos: Int) -> Int)? = null


    private inner class AppVH(v: View) : AppViewHolder<T>(v) {
        override fun onBind(item: T?) {
            onBind?.invoke(this, item)
        }
    }

    private inner class AppLVH(v: View) : AppViewHolder<Any>(v) {
        override fun onBind(item: Any?) {
            onBindL?.invoke(this)
        }
    }

    /**
     * LayoutRes constructor
     * */
    constructor(
        diffCallback: DiffUtil.ItemCallback<T>
        , @LayoutRes layoutRes: Int
        , customVH: Class<out AppViewHolder<T>>
    ) : this(
        diffCallback
    ) {
        this.layoutRes = layoutRes
        this.customBVH = customVH
    }

    /**
     * Blocks constructor
     * */
    constructor(
        diffCallback: DiffUtil.ItemCallback<T>,
        onCreateVH: (parent: ViewGroup, viewType: Int) -> AppViewHolder<T>
        , onCreateLVH: ((parent: ViewGroup, viewType: Int) -> AppViewHolder<Any>)? = null
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
        , onBindVH: (AppViewHolder<T>.(item: T?) -> Unit)
        , onBindLVH: (AppViewHolder<Any>.() -> Unit)? = null

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
        ,onCreateVH: (parent: ViewGroup, viewType: Int) -> AppViewHolder<T>
        , onCreateLVH: ((parent: ViewGroup, viewType: Int) -> AppViewHolder<Any>)? = null
    ) : this(diffCallback) {

        this.onCreate = onCreateVH
        this.onCreateL = onCreateLVH
        this.viewType = getViewType
    }


    private fun onCreateBasicVHFromLayoutRes(
        parent: ViewGroup,
        customBVH: Class<out AppViewHolder<T>>
    ): AppViewHolder<T> {
        val view = parent.appInflate(layoutRes)
        return customBVH.getConstructor(View::class.java).newInstance(view)
    }

    private fun onCreateBasicVHFromLayoutRes(
        parent: ViewGroup
    ): AppViewHolder<T> {
        val view = parent.appInflate(layoutRes)
        return AppVH(view)
    }

    override fun onCreateBasicVH(parent: ViewGroup, viewType: Int): AppViewHolder<T> {
        val customVH = customBVH
        return onCreate?.invoke(parent, viewType) ?: if (layoutRes != -1)
            if (customVH != null)
                onCreateBasicVHFromLayoutRes(parent, customVH).also { it.adapter = this }
            else
                onCreateBasicVHFromLayoutRes(parent).also { it.adapter = this }
        else
            throw
            IllegalArgumentException("you have to pass `onCreateVH` or override `onCreateBasicVH` function. ")
    }

    final override fun onBindBasicVH(holder: AppViewHolder<T>, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateLVH(parent: ViewGroup, viewType: Int): AppViewHolder<Any> {
        return this.onCreateL?.invoke(parent, viewType) ?: AppLVH(View(parent.context))
    }

    final override fun onBindLVH(holder: AppViewHolder<Any>, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getLayoutType(pos: Int): Int {
        return viewType?.invoke(pos) ?: super.getLayoutType(pos)
    }
}