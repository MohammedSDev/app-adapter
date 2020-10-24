package com.digital.appadapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.lang.IllegalArgumentException

open class AppAdapter<T>() : AppBasicAdapter<T, AppViewHolder<T>>() {

    private var onBind: (AppViewHolder<T>.(position: T?) -> Unit)? = null
    private var onCreate: ((parent: ViewGroup, viewType: Int) -> AppViewHolder<T>)? = null
    private var layoutRes: Int = -1
    private var customBVH: Class<out AppViewHolder<T>>? = null
    private var itemType: ((position: Int) -> Int)? = null

    constructor(@LayoutRes layoutRes: Int, customBVH: Class<out AppViewHolder<T>>) : this() {
        this.layoutRes = layoutRes
        this.customBVH = customBVH
    }

    constructor(@LayoutRes layoutRes: Int, onBind: AppViewHolder<T>.(item: T?) -> Unit) : this() {
        this.layoutRes = layoutRes
        this.onBind = onBind
    }

    constructor(onCreate: (parent: ViewGroup, viewType: Int) -> AppViewHolder<T>) : this() {
        this.onCreate = onCreate
    }

    constructor(
        getItemType: (position: Int) -> Int,
        onCreate: (parent: ViewGroup, viewType: Int) -> AppViewHolder<T>
    ) : this() {
        this.onCreate = onCreate
        this.itemType = getItemType
    }


    /**
     * check if onCreate passed
     * else if layoutRes exist,then check
     *                        if custom VH passed
     *                        or
     *                        onBind passed
     * else throw exception
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder<T> {
        val customVH = customBVH
        val create = onCreate
        return create?.invoke(parent, viewType)?.also {
            it.adapter = this
            it.onCreate(viewType)
        } ?: if (layoutRes != -1)
            if (customVH != null)
                createReflectionVHLayoutRes(parent, customVH).also {
                    it.adapter = this
                    it.onCreate(viewType)
                }
            else
                createBinderVHLayoutRes(parent).also {
                    it.adapter = this
                    it.onCreate(viewType)
                }
        else
            throw
            IllegalArgumentException(
                "you have to pass `layoutRes & customVH` or `layoutRes & block` or " +
                        "override `onCreateViewHolder` function."
            )
    }

    final override fun onBindViewHolder(holder: AppViewHolder<T>, position: Int) {
        super.onBindViewHolder(holder, position)
    }

    final override fun getItemCount(): Int {
        return super.getItemCount()
    }

    final override fun getItemViewType(position: Int): Int {
        return itemType?.invoke(position) ?: super.getItemViewType(position)
    }


    /**
     * create Vh using layout res
     * */
    private fun createReflectionVHLayoutRes(
        parent: ViewGroup,
        customBVH: Class<out AppViewHolder<T>>
    ): AppViewHolder<T> {
        val view = parent.appInflate(layoutRes)
        val cc: Class<View> = View::class.java
        val o = customBVH.getConstructor(cc)
        return o.newInstance(view)
    }

    /**
     * create Vh using layout res
     * */
    private fun createBinderVHLayoutRes(
        parent: ViewGroup
    ): AppViewHolder<T> {
        val view = parent.appInflate(layoutRes)
        return object : AppViewHolder<T>(view) {
            override fun onBind(item: T?) {
                onBind?.invoke(this, item)
            }
        }
    }


}