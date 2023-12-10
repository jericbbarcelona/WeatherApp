package com.jbarcelona.weatherapp.module

import android.content.Context
import androidx.room.Room
import com.jbarcelona.weatherapp.constants.Constants
import com.jbarcelona.weatherapp.database.AppDatabase
import com.jbarcelona.weatherapp.database.dao.WeatherHistoryDao
import com.jbarcelona.weatherapp.firebase.BaseAuthenticator
import com.jbarcelona.weatherapp.firebase.FirebaseAuthenticator
import com.jbarcelona.weatherapp.network.ApiService
import com.jbarcelona.weatherapp.repository.BaseRepository
import com.jbarcelona.weatherapp.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthenticator(): BaseAuthenticator {
        return FirebaseAuthenticator()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideMainRepository(
        weatherHistoryDao: WeatherHistoryDao,
        apiService: ApiService,
        authenticator: BaseAuthenticator
    ) = MainRepository(weatherHistoryDao, apiService, authenticator) as BaseRepository

    @Singleton
    @Provides
    fun provideWeatherHistoryDao(database: AppDatabase) = database.weatherHistoryDao()
}