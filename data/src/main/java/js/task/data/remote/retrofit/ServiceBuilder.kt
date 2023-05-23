package js.task.data.remote.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder
{
    private lateinit var baseUrl : String

    private val client by lazy {

        OkHttpClient.Builder().build()
    }

    private val retrofit by lazy {

        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
    }

    fun <T> buildService(baseAddress : String, service : Class<T>) : T
    {
        baseUrl = baseAddress
        return retrofit.create(service)
    }
}