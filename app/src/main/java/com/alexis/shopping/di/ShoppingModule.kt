package com.alexis.shopping.di

import android.content.Context
import androidx.room.Room
import com.alexis.shopping.data.PokemonRepositoryImpl
import com.alexis.shopping.data.local.PokemonDataBase
import com.alexis.shopping.data.local.dao.PokemonDao
import com.alexis.shopping.data.notification.NotificationRepositoryImpl
import com.alexis.shopping.data.remote.service.PokemonService
import com.alexis.shopping.domain.repository.INotificationRepository
import com.alexis.shopping.domain.repository.IPokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://pokeapi.co/api/v2/"

@Module
@InstallIn(SingletonComponent::class)
object ShoppingModule {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providerRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PokemonDataBase::class.java, "PokemonDataBase").build()

    @Singleton
    @Provides
    fun providerPokemonDao(dataBase: PokemonDataBase) = dataBase.getPokemonDao()

    @Singleton
    @Provides
    fun providerPokemonService(retrofit: Retrofit): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }

    @Singleton
    @Provides
    fun providerPokemonRepository(
        api: PokemonService,
        db: PokemonDataBase,
        dao: PokemonDao
    ): IPokemonRepository {
        return PokemonRepositoryImpl(api, db, dao)
    }

    @Singleton
    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    fun providerNotificationRepository(@ApplicationContext context: Context) : INotificationRepository {
        return NotificationRepositoryImpl(context)
    }

}