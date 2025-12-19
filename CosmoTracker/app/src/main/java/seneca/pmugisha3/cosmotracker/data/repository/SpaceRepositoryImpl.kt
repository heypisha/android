package seneca.pmugisha3.cosmotracker.data.repository

import seneca.pmugisha3.cosmotracker.data.remote.RetrofitClient
import seneca.pmugisha3.cosmotracker.data.remote.model.ApodResponse
import seneca.pmugisha3.cosmotracker.data.remote.model.EonetResponse

class SpaceRepositoryImpl : SpaceRepository {

    private val nasaApi = RetrofitClient.nasaApi
    private val eonetApi = RetrofitClient.eonetApi

    override suspend fun getApod(): Result<ApodResponse> {
        return try {
            val response = nasaApi.getApod()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEvents(status: String, limit: Int): Result<EonetResponse> {
        return try {
            val response = eonetApi.getEvents(status, limit)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
