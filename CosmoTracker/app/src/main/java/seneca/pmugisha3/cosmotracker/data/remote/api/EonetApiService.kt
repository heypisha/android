package seneca.pmugisha3.cosmotracker.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import seneca.pmugisha3.cosmotracker.data.remote.model.EonetResponse

interface EonetApiService {
  @GET("api/v3/events")
  suspend fun getEvents(
    @Query("status") status: String? = "open",
    @Query("limit") limit: Int? = null,
    @Query("days") days: Int? = null,
    @Query("source") source: String? = null,
    @Query("category") category: String? = null
  ): EonetResponse
}
