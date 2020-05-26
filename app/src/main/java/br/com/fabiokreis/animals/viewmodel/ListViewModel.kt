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
        val a1 = Animal("animal 1")
        val a2 = Animal("animal 2")
        val a3 = Animal("animal 3")
        val a4 = Animal("animal 4")
        val a5 = Animal("animal 5")
        val a6 = Animal("animal 6")
        val a7 = Animal("animal 7")

        val animalList = arrayListOf(a1, a2, a3, a4, a5, a6, a7)

        animals.value = animalList
        loadError.value = false
        loading.value = false
    }
}