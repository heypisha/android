package seneca.pmugisha3.cosmotracker.data.repository

import kotlinx.coroutines.flow.Flow
import seneca.pmugisha3.cosmotracker.data.local.entity.FavoriteEventEntity
import seneca.pmugisha3.cosmotracker.data.remote.model.ApodResponse
import seneca.pmugisha3.cosmotracker.data.remote.model.EonetResponse
import seneca.pmugisha3.cosmotracker.data.remote.model.EventDto

interface SpaceRepository {
    // Remote data
    suspend fun getApod(): Result<ApodResponse>
    suspend fun getEvents(status: String = "open", limit: Int = 50): Result<EonetResponse>

    // Local data (favorites)
    fun getAllFavorites(): Flow<List<FavoriteEventEntity>>
    suspend fun getFavoriteById(eventId: String): FavoriteEventEntity?
    suspend fun addFavorite(event: EventDto)
    suspend fun removeFavorite(eventId: String)
    suspend fun isFavorite(eventId: String): Boolean

    // Firebase sync
    suspend fun syncFavoritesToFirebase()
    suspend fun syncFavoritesFromFirebase()
}