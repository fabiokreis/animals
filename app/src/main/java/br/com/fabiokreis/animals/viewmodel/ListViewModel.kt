package br.com.fabiokreis.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.fabiokreis.animals.model.Animal
import br.com.fabiokreis.animals.model.AnimalApiService
import br.com.fabiokreis.animals.model.ApiKey
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

    fun refresh() {
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
                        if (t.key.isNullOrEmpty()) {
                            loadError.value = true
                            loading.value = false
                        } else {
                            getAnimals(t.key)
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
                        e.printStackTrace()
                        loadError.value = true
                        animals.value = null
                        loading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        dispasible.clear()
    }
}