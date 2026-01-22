package seneca.pmugisha3.cosmotracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_events")
data class FavoriteEventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val category: String,
    val date: String,
    val latitude: Double?,
    val longitude: Double?,
    val isClosed: Boolean,
    val savedAt: Long = System.currentTimeMillis()
)