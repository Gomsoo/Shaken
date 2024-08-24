package com.gomsoo.shaken.core.network.di

import com.gomsoo.shaken.core.network.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val SHAKEN_BASE_URL: String = BuildConfig.BACKEND_URL

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesOkHttpCallFactory(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(
        okhttpCallFactory: OkHttpClient,
        networkJson: Json
    ): Retrofit = Retrofit.Builder()
        .baseUrl(SHAKEN_BASE_URL)
        .client(okhttpCallFactory)
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()
}
