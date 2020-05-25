package br.com.fabiokreis.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.fabiokreis.animals.model.Animal

class ListViewModel(application: Application): AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    fun refresh() {
        getAnimals()
    }

    private fun getAnimals() {
        val a1 = Animal("a")
        val a2 = Animal("b")
        val a3 = Animal("c")
        val a4 = Animal("d")
        val a5 = Animal("e")
        val a6 = Animal("f")
        val a7 = Animal("g")

        val animalList = arrayListOf(a1, a2, a3, a4, a5, a6, a7)

        animals.value = animalList
        loadError.value = false
        loading.value = false
    }
}