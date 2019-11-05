package com.digital.appadapter

import android.view.View

typealias OnItemClick<T> = (view: View, position:Int, model:T, any:String?)->Unit
