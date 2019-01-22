package com.adrian.payment.common.injection

import com.adrian.payment.common.injection.Url.BASE_URL
import com.adrian.payment.main.datasource.RunsApiDataSource
import com.adrian.payment.main.repository.RunsRepository
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object Url {
    const val BASE_URL = "http://www.speedrun.com/api/v1/"
}

val appModule = Kodein.Module("App") {
    //bind<OkHttpClient>() with singleton {OkHttpClient()} same as
    bind() from singleton { OkHttpClient() }
    bind() from singleton {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(instance())
                .build()
    }
    bind() from singleton { Moshi.Builder().build()}
    bind() from singleton {instance<Retrofit>().create(RunsApiDataSource::class.java)}
    bind() from singleton { RunsRepository(instance()) }
}