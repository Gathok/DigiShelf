package de.malteans.digishelf.core.presentation.details.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.malteans.digishelf.core.presentation.components.customReadIcon
import de.malteans.digishelf.core.presentation.details.DetailsAction
import de.malteans.digishelf.core.presentation.details.DetailsState
import de.malteans.digishelf.core.presentation.main.components.CustomBookIcon
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.not_owned
import digishelf.composeapp.generated.resources.not_read
import digishelf.composeapp.generated.resources.owned
import digishelf.composeapp.generated.resources.read
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Deprecated("Dialog will be used for editing instead")
fun PossessionIcon(state: DetailsState, onEvent: (DetailsAction) -> Unit, fillWidth: Float = 0.4f) {

    val description = if (state.possessionStatus) {
        stringResource(Res.string.owned)
    } else {
        stringResource(Res.string.not_owned)
    }

    Icon(
        imageVector = CustomBookIcon,
        contentDescription = stringResource(Res.string.owned),
        modifier = Modifier
            .fillMaxWidth(fillWidth)
            .combinedClickable(
                onClick = {
                    if (state.isEditing) {
//                        onEvent(DetailsAction.PossessionStatusChanged(!state.possessionStatus))
                    } else {
//                        Toast.makeText(context, description, Toast.LENGTH_SHORT).show() TODO: Replace Toast with Snackbar
                    }
                },
                onLongClick = {
                    if (!state.isEditing) onEvent(DetailsAction.SwitchEditing)
//                    onEvent(DetailsAction.PossessionStatusChanged(!state.possessionStatus))
                }
            ),
        tint = if (state.possessionStatus) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Deprecated("Dialog will be used for editing instead")
fun ReadIcon(state: DetailsState, onEvent: (DetailsAction) -> Unit, fillWidth: Float = 0.5f) {

    val description = if (state.readStatus) {
        stringResource(Res.string.read)
    } else {
        stringResource(Res.string.not_read)
    }

    Icon(
        imageVector = customReadIcon(),
        contentDescription = stringResource(Res.string.read),
        modifier = Modifier
            .fillMaxWidth(fillWidth)
            .combinedClickable(
                onClick = {
                    if (state.isEditing) {
//                        onEvent(DetailsAction.ReadStatusChanged(!state.readStatus))
                    } else {
//                        Toast.makeText(context, description, Toast.LENGTH_SHORT).show() TODO: Replace Toast with Snackbar
                    }
                },
                onLongClick = {
                    if (!state.isEditing) onEvent(DetailsAction.SwitchEditing)
//                    onEvent(DetailsAction.ReadStatusChanged(!state.readStatus))
                }
            )
            .padding(top = 6.dp, bottom = 6.dp),
        tint = if (state.readStatus) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        },
    )
}