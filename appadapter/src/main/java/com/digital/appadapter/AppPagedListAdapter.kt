package com.digital.appadapter

import android.view.LayoutInflater
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
    private var onBindL: (() -> Unit)? = null
    private var onBind: ((position: Int) -> Unit)? = null
    private var onCreateL: ((parent: ViewGroup, viewType: Int) -> AppViewHolder)? = null
    private var onCreate: ((parent: ViewGroup, viewType: Int) -> AppViewHolder)? = null
    private var layoutRes: Int = -1


    class AppVH(v: View) : AppViewHolder(v) {
        override fun onBind(position: Int) {

        }
    }
    class AppLVH(v: View) : AppViewHolder(v) {
        override fun onBind(position: Int) {

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
        onCreateVH: (parent: ViewGroup, viewType: Int) -> AppVH
        , onBindVH: ((position: Int) -> Unit)
        , onCreateLVH: ((parent: ViewGroup, viewType: Int) -> AppLVH)? = null
        , onBindLVH: (() -> Unit)? = null

    ) : this(diffCallback) {

        this.onCreate = onCreateVH
        this.onCreateL = onCreateLVH
        this.onBind = onBindVH
        this.onBindL = onBindLVH
    }


    private fun onCreateBasicVHFromLayoutRes(
        parent: ViewGroup,
        customBVH: Class<out AppViewHolder>
    ): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return customBVH.getConstructor(View::class.java).newInstance(view)
    }

    override fun onCreateBasicVH(parent: ViewGroup, viewType: Int): AppViewHolder {
        val customVH = customBVH
        return onCreate?.invoke(parent, viewType) ?: if (customVH!= null)
            onCreateBasicVHFromLayoutRes(parent, customVH)
        else
            throw
            IllegalArgumentException("you have to pass `onCreateVH` or override `onCreateBasicVH` function. ")
    }

    final override fun onBindBasicVH(holder: AppViewHolder, position: Int) {
        onBind?.invoke(position)// ?: holder.onBind(position)
    }

    override fun onCreateLVH(parent: ViewGroup, viewType: Int): AppViewHolder {
        return this.onCreateL?.invoke(parent, viewType) ?: AppLVH(View(parent.context))
    }

    final override fun onBindLVH(holder: AppViewHolder, position: Int) {
        onBindL?.invoke()// ?: holder.onBind(position)
    }
}