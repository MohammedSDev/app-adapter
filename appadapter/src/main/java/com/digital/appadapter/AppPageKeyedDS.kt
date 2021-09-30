package com.digital.appadapter

import androidx.annotation.CallSuper
import androidx.paging.PageKeyedDataSource

/**
 * PageKeyedDataSource include support for retry load after.
 * */
@Deprecated("use PagingSource")
abstract class AppPageKeyedDS<K:Any, T:Any> : PageKeyedDataSource<K, T>() {

	private var appParams: LoadParams<K>? = null
	private var appCallback: LoadCallback<K, T>? = null

	fun reTryLoadAfter() {
		val param = appParams ?: return
		val callback = appCallback ?: return
		loadAfter(param, callback)
	}

	@CallSuper
	override fun loadAfter(params: LoadParams<K>, callback: LoadCallback<K, T>) {
		appParams = params
		appCallback = callback
	}

}

