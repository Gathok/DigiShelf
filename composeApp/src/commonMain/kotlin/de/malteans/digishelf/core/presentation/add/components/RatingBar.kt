package de.malteans.digishelf.core.presentation.add.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.no_rating
import digishelf.composeapp.generated.resources.rating
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RatingBar(
    max: Int = 5,
    current: Int,
    onRatingChanged: (Int) -> Unit,
    enabled: Boolean = true,
    changed: Boolean = false,
    showText: Boolean = true,
    activeColor: Color = MaterialTheme.colorScheme.tertiary,
    inactiveColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
) {

//    val notEditingToast = Toast.makeText( TODO: Replace Toast with Snackbar
//        LocalContext.current,
//        stringResource(id = Res.string.not_editing_desc),
//        Toast.LENGTH_SHORT
//    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            for (i in 1..max) {
                Icon(
                    imageVector = if (i <= current) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "${stringResource(Res.string.rating)}: $i",
                    modifier = Modifier
                        .combinedClickable(
                            onClick = {
                                if (enabled) {
                                    onRatingChanged(i)
                                } else {
//                                    notEditingToast.show() TODO: Replace Toast with Snackbar
                                }
                            },
                            onLongClick = {
                                if (enabled) {
                                    onRatingChanged(0)
                                } else {
//                                    notEditingToast.show() TODO: Replace Toast with Snackbar
                                }
                            }
                        )
                        .padding(horizontal = 4.dp)
                        .weight(1f) // This will divide the available space equally between the stars
                        .aspectRatio(1f), // This will make the stars square
                    tint =
                    if (i <= current) activeColor
                    else inactiveColor,
                )
            }
        }
        if (showText) {
            Row {
                Text(
                    text = when (current) {
                        0 -> stringResource(Res.string.no_rating)
                        else -> "${stringResource(Res.string.rating)}: $current★"
                    } + if (changed) "*" else "",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}