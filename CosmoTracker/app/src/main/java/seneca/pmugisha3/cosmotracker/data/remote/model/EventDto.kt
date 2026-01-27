package seneca.pmugisha3.cosmotracker.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventDto(
  @Json(name = "id") val id: String = "",
  @Json(name = "title") val title: String = "",
  @Json(name = "description") val description: String? = null,
  @Json(name = "link") val link: String? = null,
  @Json(name = "closed") val closed: String? = null,
  @Json(name = "categories") val categories: List<CategoryDto> = emptyList(),
  @Json(name = "sources") val sources: List<SourceDto> = emptyList(),
  @Json(name = "geometries") val geometries: List<GeometryDto> = emptyList()
)
