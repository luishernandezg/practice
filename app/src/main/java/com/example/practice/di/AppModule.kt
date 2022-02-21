package com.example.practice.di

import android.content.Context
import com.example.practice.api.PokemonService
import com.example.practice.repository.PokemonRepository
import com.example.practice.repository.PokemonRepositoryImp
import com.example.practice.util.Constants.Companion.BASE_URL
import com.example.practice.util.Constants.Companion.BASE_URL_1
import com.example.practice.util.Constants.Companion.CONSUMER_KEY
import com.example.practice.util.Constants.Companion.CONSUMER_SECRET
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor


/**
 * Created by Luis hernandez on 11/4/2021.
 * Email: luis.guaicaipuro.h.a@gmail.com
 */

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * As these types are scoped to the application lifecycle using @Singleton, they're installed
 * in Hilt's ApplicationComponent.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*@Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteTasksDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalTasksDataSource

    @Singleton
    @RemoteTasksDataSource
    @Provides
    fun provideTasksRemoteDataSource(): TasksDataSource {
        return TasksRemoteDataSource
    }

    @Singleton
    @LocalTasksDataSource
    @Provides
    fun provideTasksLocalDataSource(
        database: ToDoDatabase,
        ioDispatcher: CoroutineDispatcher
    ): TasksDataSource {
        return TasksLocalDataSource(
            database.taskDao(), ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java,
            "Tasks.db"
        ).build()
    }*/

    @Singleton
    @Provides
    fun providePokemonService(@ApplicationContext appContext: Context): PokemonService {

        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY

        val consumer = OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET)
        consumer.setTokenWithSecret("", "")


        val httpClient = OkHttpClient.Builder()
            .addInterceptor(
                Interceptor { chain ->
                    val request = chain.request().newBuilder().build()
                    chain.proceed(request)
                })
            .addInterceptor(SigningInterceptor(consumer))
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(BASE_URL_1)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(PokemonService::class.java)
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

/**
 * The binding for PokemonRepository is on its own module so that we can replace it easily in tests.
 */
@Module
@InstallIn(SingletonComponent::class)
object PokemonRepositoryModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        pokemonService: PokemonService,
        ioDispatcher: CoroutineDispatcher
    ): PokemonRepository {
        return PokemonRepositoryImp(
           pokemonService, ioDispatcher
        )
    }
}
