package com.digital.appadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.DataSource

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

inline fun <K,M>getDSF(crossinline body:()->DataSource<K, M>):DataSource.Factory<K, M>{
    return object: DataSource.Factory<K,M>(){
        override fun create(): DataSource<K, M> {
            return body()
        }
    }
}