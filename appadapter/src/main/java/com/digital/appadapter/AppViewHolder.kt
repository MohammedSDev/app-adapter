package com.digital.appadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AppViewHolder(v:View) : RecyclerView.ViewHolder(v) {

    abstract fun onBind(position:Int)



    fun <T : View> get(id: Int): T {
        return customFindCachedViewById(id) as T
    }

    //------------Cached functions-----------------------
    var customFindViewCache: HashMap<Int, View>? = null

    private fun customFindCachedViewById(var1: Int): View? {
        if (this.customFindViewCache == null) {
            this.customFindViewCache = HashMap()
        }

        var var2 = customFindViewCache?.get(var1)
        if (var2 == null) {
            val var10000 = itemView

            var2 = var10000.findViewById(var1)
            customFindViewCache?.put(var1, var2)
        }

        return var2
    }

    private fun customeClearFindViewByIdCache() {
        customFindViewCache?.clear()
    }
}