package br.com.fabiokreis.animals.di

import br.com.fabiokreis.animals.model.AnimalApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: AnimalApiService)
}