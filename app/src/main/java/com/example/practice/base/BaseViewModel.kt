package com.example.practice.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practice.util.Event
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * Created by Luis hernandez on 11/4/2021.
 * Email: luis.guaicaipuro.h.a@gmail.com
 */
open class BaseViewModel : ViewModel() {


    val loadingState = MutableLiveData<Boolean>()
    val onSuccess = MutableLiveData<Event<Any>>()
    val onError = MutableLiveData<String>()
    val onConnectionError = MutableLiveData<Throwable>()


    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun logError(throwable: Throwable?) {
        Log.d("${this::class.java.canonicalName} ERROR", throwable?.message.toString())
    }

    protected fun showLoading() = loadingState.postValue(true)

    protected fun dismissLoading() = loadingState.postValue(false)

}