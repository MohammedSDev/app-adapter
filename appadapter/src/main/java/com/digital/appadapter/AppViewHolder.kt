package com.digital.appadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AppViewHolder<M>(v: View) : RecyclerView.ViewHolder(v) {

    abstract fun onBind(item: M?)
    internal var adapter: RecyclerView.Adapter<*>? = null
    private var tag: String? = null
    private val onClickListener = View.OnClickListener {
        callback?.invoke(it, adapterPosition, getItem()!!, tag)
    }

    fun <A : AppRecyclerAdapter<M>> getAdapter(): A {
        return adapter as A
    }

    fun <T : View> get(id: Int): T {
        return customFindCachedViewById(id) as T
    }

    fun getItem(pos: Int = adapterPosition): M? =
        getAdapter<AppRecyclerAdapter<M>>()?.getItemModel(pos)

    private val callback
        get() = getAdapter<AppRecyclerAdapter<M>>()?.callBack

    fun callCallBack(view: View, pos: Int, item: M, tag: String? = null) {
        callback?.invoke(view, pos, item, tag)
    }

    /**
     * call onItemClick when specific view clicked
     * @param view: this clickable view
     * @param tag: any thing to pass with click to onClickItem callback
     * @param safeItemClick: true,determine don't clear OnItemClick if @getItem(pos) return null,
     * */
    @JvmOverloads
    fun setOnItemClick(view: View, tag: String? = null, safeItemClick: Boolean = false) {
        if (safeItemClick && getItem() == null) {
            view.setOnClickListener(null);return
        }
        this.tag = tag
        view.setOnClickListener(onClickListener)
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