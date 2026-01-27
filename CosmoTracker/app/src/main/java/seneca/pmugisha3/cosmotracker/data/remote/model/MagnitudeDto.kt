package seneca.pmugisha3.cosmotracker.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MagnitudeDto(
  @Json(name = "value") val value: Double?,
  @Json(name = "unit") val unit: String?
)
