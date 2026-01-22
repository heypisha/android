package seneca.pmugisha3.cosmotracker.data.mapper

import seneca.pmugisha3.cosmotracker.data.local.entity.FavoriteEventEntity
import seneca.pmugisha3.cosmotracker.data.remote.model.EventDto

fun EventDto.toFavoriteEntity(): FavoriteEventEntity {
    val firstGeometry = geometries.firstOrNull()
    val coords = firstGeometry?.coordinates

    return FavoriteEventEntity(
        id = id,
        title = title,
        description = description,
        category = categories.firstOrNull()?.title ?: "Unknown",
        date = firstGeometry?.date ?: "",
        latitude = coords?.getOrNull(1),
        longitude = coords?.getOrNull(0),
        isClosed = closed != null
    )
}