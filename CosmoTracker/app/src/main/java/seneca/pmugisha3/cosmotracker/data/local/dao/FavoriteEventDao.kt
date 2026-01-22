package seneca.pmugisha3.cosmotracker.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import seneca.pmugisha3.cosmotracker.data.local.entity.FavoriteEventEntity

@Dao
interface FavoriteEventDao {

    @Query("SELECT * FROM favorite_events ORDER BY savedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteEventEntity>>

    @Query("SELECT * FROM favorite_events WHERE id = :eventId")
    suspend fun getFavoriteById(eventId: String): FavoriteEventEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(event: FavoriteEventEntity)

    @Delete
    suspend fun deleteFavorite(event: FavoriteEventEntity)

    @Query("DELETE FROM favorite_events WHERE id = :eventId")
    suspend fun deleteFavoriteById(eventId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_events WHERE id = :eventId)")
    suspend fun isFavorite(eventId: String): Boolean
}