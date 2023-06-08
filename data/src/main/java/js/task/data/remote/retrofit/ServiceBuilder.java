package js.task.data.remote.retrofit;

import kotlin.Lazy;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceBuilder
{
    String baseUrl;

    private final Lazy<OkHttpClient> clientLazy = kotlin.LazyKt.lazy(() -> new OkHttpClient.Builder().build());

    private final Lazy<Retrofit> retrofitLazy = kotlin.LazyKt.lazy(() ->
       new Retrofit.Builder()
               .baseUrl(baseUrl)
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .addConverterFactory(GsonConverterFactory.create())
               .client(clientLazy.getValue())
               .build()
    );

    public <T> T buildService(String baseAddress, Class<T> service)
    {
        baseUrl = baseAddress;
        return retrofitLazy.getValue().create(service);
    }
}