package seneca.pmugisha3.cosmotracker.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeometryDto(
    @Json(name = "date") val date: String?,
    @Json(name = "type") val type: String?,
    @Json(name = "coordinates") val coordinates: List<Double>?,
    @Json(name = "magnitudeValue") val magnitudeValue: Double?,
    @Json(name = "magnitudeUnit") val magnitudeUnit: String?
)
