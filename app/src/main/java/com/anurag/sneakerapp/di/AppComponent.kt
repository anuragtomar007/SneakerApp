package com.anurag.sneakerapp.di

import com.anurag.sneakerapp.ui.CheckoutActivity
import com.anurag.sneakerapp.ui.SneakerDetailsActivity
import com.anurag.sneakerapp.ui.SneakerListActivity
import dagger.Component

@Component(modules = [AppModule::class, ViewModelFactoryModule::class])
interface AppComponent {
    fun inject(activity: SneakerListActivity)
    fun inject(activity: SneakerDetailsActivity)
    fun inject(activity: CheckoutActivity)
}
