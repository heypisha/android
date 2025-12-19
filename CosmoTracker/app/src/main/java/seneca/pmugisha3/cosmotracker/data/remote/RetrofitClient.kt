package seneca.pmugisha3.cosmotracker.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import seneca.pmugisha3.cosmotracker.BuildConfig
import seneca.pmugisha3.cosmotracker.data.remote.api.EonetApiService
import seneca.pmugisha3.cosmotracker.data.remote.api.NasaApiService
import seneca.pmugisha3.cosmotracker.utils.Constants
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Interceptor to add API key to all NASA API requests
    private val apiKeyInterceptor = okhttp3.Interceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.NASA_API_KEY)
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        chain.proceed(request)
    }

    private val baseClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val nasaClient = baseClient.newBuilder()
        .addInterceptor(apiKeyInterceptor)
        .build()

    val nasaApi: NasaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.NASA_BASE_URL)
            .client(nasaClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(NasaApiService::class.java)
    }

    val eonetApi: EonetApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.EONET_BASE_URL)
            .client(baseClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(EonetApiService::class.java)
    }
}
