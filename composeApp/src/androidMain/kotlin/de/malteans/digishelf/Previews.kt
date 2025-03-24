package de.malteans.digishelf

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import de.malteans.digishelf.core.presentation.components.CustomDialog
import de.malteans.digishelf.theme.DigiShelfTheme
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.camera_permission_desc
import digishelf.composeapp.generated.resources.camera_permission_heading
import org.jetbrains.compose.resources.stringResource

@Preview
@Composable
private fun CustomDialogPreview() {
    DigiShelfTheme(
        useDarkTheme = true
    ) {
        CustomDialog(
            onDismissRequest = { /* Prevent dismiss */ },
            title = { Text(text = stringResource(Res.string.camera_permission_heading)) },
            leftIcon = @Composable {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancel",
                    modifier = Modifier
                        .clickable{  }
                )
            },
            rightIcon = @Composable {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Grant Permission",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            )
        ) {
            Text(text = stringResource(Res.string.camera_permission_desc))
        }
    }
}