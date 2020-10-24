package com.digital.appadapter

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

abstract class AppViewHolder<M>(v: View) : RecyclerView.ViewHolder(v) {

    open fun onCreate(viewType:Int){}
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


    fun getString(@StringRes resId:Int):String = itemView.context.getString(resId)
    fun getColorComp(@ColorRes resId:Int):Int = ContextCompat.getColor(itemView.context,resId)
    fun getDrawableComp(@DrawableRes resId:Int):Drawable? = ContextCompat.getDrawable(itemView.context,resId)
    fun getDimenComp(@DimenRes resId:Int):Float? = itemView.context.resources.getDimension(resId)




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