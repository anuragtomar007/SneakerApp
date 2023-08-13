package com.anurag.sneakerapp.di

import com.anurag.sneakerapp.data.repository.SneakerRepository
import com.anurag.sneakerapp.db.CartDao
import com.anurag.sneakerapp.ui.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideViewModelFactory(
        sneakerRepository: SneakerRepository,
        cartDao: CartDao
    ): ViewModelFactory {
        return ViewModelFactory(sneakerRepository, cartDao)
    }
}
