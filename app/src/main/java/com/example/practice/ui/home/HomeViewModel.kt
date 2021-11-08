package com.example.practice.ui.home

import androidx.lifecycle.*
import com.example.practice.base.BaseViewModel
import com.example.practice.model.PokemonListResponse
import com.example.practice.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository
) :  BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val pokemonList = MutableLiveData<PokemonListResponse?>()

    fun getPokemonList(){

        viewModelScope.launch {
            getPokemonListAsync()
        }
    }

    suspend  fun getPokemonListAsync() {
        val result = runCatching {
            showLoading()
            pokemonRepository.getPokemonList()
        }

        with(result) {
            dismissLoading()
            onSuccess {
                it.let {
                    Timber.d("onSuccess $it")
                    pokemonList.postValue(it)
                }
            }
            onFailure {
                dismissLoading()
                when (it) {
                    is IOException -> {
                        Timber.e("IOException $it")
                        onError.postValue(it.message)
                    }
                    is HttpException -> {
                        val code = it.code()
                        Timber.e("ERROR CODE -> $code")
                        if (it.code() == 400){
                            /*val errorResponse = it.response()?.errorBody()?.source()?.let {
                                val moshiAdapter = Moshi.Builder().build().adapter(
                                    ErrorResponse::class.java)
                                val errorResponse = moshiAdapter.fromJson(it)
                                Timber.e("moshiAdapter -> ${errorResponse}")
                                onError.postValue(moshiAdapter.toJson(errorResponse))
                            }*/
                        }else{
                            Timber.e("ERROR JSON -> ${it.message()}")
                            onError.postValue(it.message())
                        }
                    }
                    else -> {
                        Timber.e("Exception else $it")
                        onError.postValue(it.message)
                    }
                }

                Timber.d("onFailure message:${it.message}")
            }
        }
    }
}