package de.malteans.digishelf.core.presentation.overview.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import de.malteans.digishelf.core.domain.Book
import de.malteans.digishelf.core.presentation.components.customReadIcon
import de.malteans.digishelf.core.presentation.main.components.CustomBookIcon
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.book_error_blue
import digishelf.composeapp.generated.resources.book_error_green
import digishelf.composeapp.generated.resources.book_error_purple
import digishelf.composeapp.generated.resources.book_error_red
import digishelf.composeapp.generated.resources.book_error_yellow
import digishelf.composeapp.generated.resources.rating
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BookItem(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                val painter = rememberAsyncImagePainter(
                    model = book.imageUrl.replace("http://", "https://"),
                    onSuccess = {
                        imageLoadResult = (
                            if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1)
                                Result.success(it.painter)
                            else
                                Result.failure(Exception("Invalid image size"))
                        )
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )

                val painterState by painter.state.collectAsStateWithLifecycle()
                val transition by animateFloatAsState(
                    targetValue = if(painterState is AsyncImagePainter.State.Success) 1f else 0f,
                    animationSpec = tween(
                        durationMillis = 800
                    )
                )

                when (val result = imageLoadResult) {
                    null -> PulseAnimation(
                        modifier = Modifier.size(60.dp)
                    )
                    else -> {
                        val errorPainter = painterResource(
                            when (book.bookSeries?.id?.rem(5) ?: book.id.rem(5)) {
                                0L -> Res.drawable.book_error_blue
                                1L -> Res.drawable.book_error_green
                                2L -> Res.drawable.book_error_purple
                                3L -> Res.drawable.book_error_red
                                else -> Res.drawable.book_error_yellow
                            }
                        )
                        Image(
                            painter = if (result.isSuccess) painter
                                else errorPainter,
                            contentDescription = book.title,
                            contentScale = if (result.isSuccess) ContentScale.Crop
                                else ContentScale.Fit,
                            modifier = Modifier
                                .aspectRatio(
                                    ratio = 0.65f,
                                    matchHeightConstraintsFirst = true
                                )
                                .graphicsLayer {
                                    rotationX = (1f - transition) * 30f
                                    val scale = 0.8f + (0.2f * transition)
                                    scaleX = scale
                                    scaleY = scale
                                }
                                .clip(RoundedCornerShape(6.dp))
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    for (i in 1..5) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = stringResource(Res.string.rating),
                            modifier = Modifier.size(16.dp),
                            tint = if (book.rating != null && i <= book.rating) MaterialTheme.colorScheme.tertiary
                                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                        )
                    }
                }
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = CustomBookIcon,
                    contentDescription = "Possession Status",
                    tint = if (book.possessionStatus) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                )
                Icon(
                    imageVector = customReadIcon(),
                    contentDescription = "Read Status",
                    tint = if (book.readStatus) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
            )
        }
    }
}
