package com.digital.appadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.lang.IllegalArgumentException

open class AppAdapter<T>() : AppBasicAdapter<T, AppViewHolder>() {

    private var onBind: ((position: Int) -> Unit)? = null
    private var layoutRes: Int = -1
    private var customBVH: Class<out AppViewHolder>? = null


    constructor(@LayoutRes layoutRes: Int, customBVH: Class<out AppViewHolder>):this() {
        this.layoutRes = layoutRes
        this.customBVH = customBVH
    }
    constructor(@LayoutRes layoutRes: Int, onBind:(position:Int)->Unit ):this() {
        this.layoutRes = layoutRes
        this.onBind = onBind
    }



    //----------------



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val customVH = customBVH
        return if (layoutRes != -1)
            if(customVH != null)
                onCreateVHLayoutRes(parent, customVH )
        else
                onCreateAppVHLayoutRes(parent)
        else
            throw
            IllegalArgumentException("you have to pass `layoutRes & customVH` or `layoutRes & block` or " +
                    "override `onCreateViewHolder` function.")
    }


    /**
     * create Vh using layout res
     * */
    private fun onCreateVHLayoutRes(
        parent: ViewGroup,
        customBVH: Class<out AppViewHolder>
    ): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        val cc:Class<View> = View::class.java
        val o = customBVH.getConstructor(cc)
        return o.newInstance(view)
    }

    /**
     * create Vh using layout res
     * */
    private fun onCreateAppVHLayoutRes(
        parent: ViewGroup
    ): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return object:AppViewHolder(view){
            override fun onBind(position: Int) {
                onBind?.invoke(position)
            }
        }
    }


}