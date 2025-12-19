package seneca.pmugisha3.cosmotracker.data.repository

import seneca.pmugisha3.cosmotracker.data.remote.model.ApodResponse
import seneca.pmugisha3.cosmotracker.data.remote.model.EonetResponse

interface SpaceRepository {
    suspend fun getApod(): Result<ApodResponse>
    suspend fun getEvents(status: String = "open", limit: Int = 50): Result<EonetResponse>
}