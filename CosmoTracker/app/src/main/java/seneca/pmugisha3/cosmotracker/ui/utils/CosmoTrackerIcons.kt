package seneca.pmugisha3.cosmotracker.ui.utils

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import seneca.pmugisha3.cosmotracker.R

@Composable
fun FavoriteButton(onClick: () -> Unit) {
  IconButton(onClick) {
    Icon(
      painter = painterResource(R.drawable.ic_thumb_up),
      contentDescription = stringResource(R.string.cd_add_to_favorites),
    )
  }
}

@Composable
fun BookmarkButton(isBookmarked: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
  val clickLabel = stringResource(
    if (isBookmarked) R.string.unbookmark else R.string.bookmark,
  )
  IconToggleButton(
    checked = isBookmarked,
    onCheckedChange = { onClick() },
    modifier = modifier.semantics {
      this.onClick(label = clickLabel, action = null)
    },
  ) {
    Icon(
      painter = if (isBookmarked) painterResource(R.drawable.ic_bookmark) else painterResource(R.drawable.ic_bookmark_outline),
      contentDescription = null,
    )
  }
}

@Composable
fun ShareButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
  IconButton(onClick = onClick, modifier = modifier) {
    Icon(
      painter = painterResource(id = R.drawable.ic_share),
      contentDescription = "Share",
      modifier = Modifier.size(18.dp)
    )
  }
}

@Composable
fun MapButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
  IconButton(onClick = onClick, modifier = modifier) {
    Icon(
      painter = painterResource(id = R.drawable.ic_map),
      contentDescription = "Open on Map",
      modifier = Modifier.size(18.dp)
    )
  }
}

@Composable
fun MapIcon(modifier: Modifier = Modifier) {
  Icon(
    painter = painterResource(id = R.drawable.ic_map),
    contentDescription = null,
    modifier = modifier
  )
}
