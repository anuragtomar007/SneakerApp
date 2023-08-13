package com.anurag.sneakerapp.di

import android.content.Context
import com.anurag.sneakerapp.data.repository.SneakerRepository
import com.anurag.sneakerapp.db.CartDao
import com.anurag.sneakerapp.db.CartDatabase
import com.anurag.sneakerapp.ui.viewmodel.CheckoutViewModel
import com.anurag.sneakerapp.ui.viewmodel.SneakerDetailsViewModel
import com.anurag.sneakerapp.ui.viewmodel.SneakerViewModel
import com.anurag.sneakerapp.ui.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val applicationContext: Context) {

    @Provides
    fun provideApplicationContext(): Context {
        return applicationContext
    }

    @Provides
    fun provideSneakerRepository(): SneakerRepository {
        return SneakerRepository()
    }

    @Provides
    fun provideCartDao(context: Context): CartDao {
        return CartDatabase.getDatabase(context).cartDao()
    }

    @Provides
    fun provideViewModelFactory(
        sneakerRepository: SneakerRepository,
        cartDao: CartDao
    ): ViewModelFactory {
        return ViewModelFactory(sneakerRepository, cartDao)
    }

    @Provides
    fun provideSneakerViewModel(
        sneakerRepository: SneakerRepository,
    ): SneakerViewModel {
        return SneakerViewModel(sneakerRepository)
    }

    @Provides
    fun provideSneakerDetailsViewModel(
        sneakerRepository: SneakerRepository
    ): SneakerDetailsViewModel {
        return SneakerDetailsViewModel(sneakerRepository)
    }

    @Provides
    fun provideCheckoutViewModel(
        sneakerRepository: SneakerRepository,
        cartDao: CartDao
    ): CheckoutViewModel {
        return CheckoutViewModel(sneakerRepository, cartDao)
    }
}
