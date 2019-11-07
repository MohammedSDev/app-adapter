package com.digital.appadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

typealias OnItemClick<T> = (view: View, position: Int, model: T, any: String?) -> Unit

fun ViewGroup.appInflate(@LayoutRes layoutRes: Int, attach: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attach)

}

fun appInflate(
    @LayoutRes layoutRes: Int, context: Context,
    root: ViewGroup? = null,
    attach: Boolean = false
): View {
    return LayoutInflater.from(context).inflate(layoutRes, root, attach)

}