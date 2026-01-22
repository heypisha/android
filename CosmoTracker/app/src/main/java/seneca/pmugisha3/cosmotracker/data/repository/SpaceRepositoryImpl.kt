package seneca.pmugisha3.cosmotracker.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import seneca.pmugisha3.cosmotracker.data.local.database.CosmoDatabase
import seneca.pmugisha3.cosmotracker.data.local.entity.FavoriteEventEntity
import seneca.pmugisha3.cosmotracker.data.mapper.toFavoriteEntity
import seneca.pmugisha3.cosmotracker.data.remote.RetrofitClient
import seneca.pmugisha3.cosmotracker.data.remote.model.ApodResponse
import seneca.pmugisha3.cosmotracker.data.remote.model.EonetResponse
import seneca.pmugisha3.cosmotracker.data.remote.model.EventDto

class SpaceRepositoryImpl(context: Context) : SpaceRepository {

    private val nasaApi = RetrofitClient.nasaApi
    private val eonetApi = RetrofitClient.eonetApi
  private val favoriteEventDao = CosmoDatabase.getDatabase(context).favoriteEventDao()


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

  override fun getAllFavorites(): Flow<List<FavoriteEventEntity>> {
    return favoriteEventDao.getAllFavorites()
  }

  override suspend fun getFavoriteById(eventId: String): FavoriteEventEntity? {
    return favoriteEventDao.getFavoriteById(eventId)
  }

  override suspend fun addFavorite(event: EventDto) {
   favoriteEventDao.insertFavorite(event.toFavoriteEntity())
  }

  override suspend fun removeFavorite(eventId: String) {
    favoriteEventDao.deleteFavoriteById(eventId)
  }

  override suspend fun isFavorite(eventId: String): Boolean {
    return favoriteEventDao.isFavorite(eventId)
  }
}
