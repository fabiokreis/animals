package br.com.fabiokreis.animals.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.fabiokreis.animals.model.Animal
import br.com.fabiokreis.animals.model.AnimalApiService
import br.com.fabiokreis.animals.model.ApiKey
import br.com.fabiokreis.animals.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel(application: Application) : AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val dispasible = CompositeDisposable()
    private val apiService = AnimalApiService()

    private val prefs = SharedPreferencesHelper(getApplication())

    private var invalidApiKey = false

    fun refresh() {
        loading.value = true
        invalidApiKey = false
        val key = prefs.getApiKey()
        if (key.isNullOrEmpty()) {
            getKey()
        } else {
            getAnimals(key)
        }
    }

    fun hardRefresh() {
        loading.value = true
        getKey()
    }

    private fun getKey() {
        dispasible.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(t: ApiKey) {
                        val key = t.key
                        if (key.isNullOrEmpty()) {
                            loadError.value = true
                            loading.value = false
                        } else {
                            prefs.saveApiKey(key)
                            getAnimals(key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    private fun getAnimals(key: String) {
        dispasible.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Animal>>() {
                    override fun onSuccess(t: List<Animal>) {
                        loadError.value = false
                        animals.value = t
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        if (!invalidApiKey) {
                            invalidApiKey = true
                            getKey()
                        } else {
                            e.printStackTrace()
                            loadError.value = true
                            animals.value = null
                            loading.value = false
                        }
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        dispasible.clear()
    }
}