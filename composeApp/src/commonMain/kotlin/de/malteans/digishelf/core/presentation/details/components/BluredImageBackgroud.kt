package de.malteans.digishelf.core.presentation.details.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.book_error_blue
import digishelf.composeapp.generated.resources.book_error_green
import digishelf.composeapp.generated.resources.book_error_purple
import digishelf.composeapp.generated.resources.book_error_yellow
import digishelf.composeapp.generated.resources.book_series_red
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random.Default.nextInt

@Composable
fun BlurredImageBackground(
    imageUrl: String?,
    onBackClick: () -> Unit,
    rightIcons: @Composable () -> Unit,
    errorImageId: Int = nextInt(5),
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult = if(size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image dimensions"))
            }
        },
        onError = {
            it.result.throwable.printStackTrace()
        }
    )

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Image(
                    painter = painter,
                    contentDescription = "Book cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(20.dp)
                )
            }

            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer)
            )
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 16.dp)
                .statusBarsPadding()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            rightIcons()
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
            ElevatedCard(
                modifier = Modifier
                    .height(230.dp)
                    .aspectRatio(2 / 3f),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = if(imageLoadResult?.isSuccess == true) 15.dp else 0.dp
                ),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.Transparent
                )
            ) {
                AnimatedContent(
                    targetState = imageLoadResult
                ) { result ->
                    Box {
                        val errorPainter = painterResource(
                            when (errorImageId) {
                                0 -> Res.drawable.book_error_blue
                                1 -> Res.drawable.book_error_green
                                2 -> Res.drawable.book_error_purple
                                3 -> Res.drawable.book_series_red
                                else -> Res.drawable.book_error_yellow
                            }
                        )
                        Image(
                            painter = if(result?.isSuccess == true) painter
                                else errorPainter,
                            contentDescription = "Book Cover",
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent),
                            contentScale = if(result?.isSuccess == true) {
                                ContentScale.Crop
                            } else {
                                ContentScale.Fit
                            }
                        )
                    }
                }
            }
            content()
        }
    }
}