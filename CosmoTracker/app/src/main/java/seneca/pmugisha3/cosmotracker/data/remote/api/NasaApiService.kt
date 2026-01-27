package seneca.pmugisha3.cosmotracker.data.remote.api

import retrofit2.http.GET
import seneca.pmugisha3.cosmotracker.data.remote.model.ApodResponse

interface NasaApiService {
  @GET("planetary/apod")
  suspend fun getApod(): ApodResponse
}
