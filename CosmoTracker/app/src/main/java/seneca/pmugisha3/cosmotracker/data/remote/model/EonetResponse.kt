package seneca.pmugisha3.cosmotracker.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EonetResponse(
  @Json(name = "title") val title: String? = null,
  @Json(name = "description") val description: String? = null,
  @Json(name = "link") val link: String? = null,
  @Json(name = "events") val events: List<EventDto> = emptyList()
)
