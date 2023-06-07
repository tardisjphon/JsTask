package js.task.data.remote.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ServiceBuilder
{
    private lateinit var baseUrl : String

    private val client by lazy {

        OkHttpClient.Builder()
            .build()
    }

    private val retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun <T> buildService(baseAddress : String, service : Class<T>) : T
    {
        baseUrl = baseAddress
        return retrofit.create(service)
    }
}