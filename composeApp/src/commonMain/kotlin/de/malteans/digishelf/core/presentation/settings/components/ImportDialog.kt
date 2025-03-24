package de.malteans.digishelf.core.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import de.malteans.digishelf.core.presentation.components.CustomDialog
import de.malteans.digishelf.core.presentation.components.OutlinedText
import de.malteans.digishelf.core.presentation.settings.components.icons.CustomFileOpenIcon
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.file
import digishelf.composeapp.generated.resources.import_data
import digishelf.composeapp.generated.resources.no_file_selected
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readString
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImportDialog(
    onDismiss: () -> Unit,
    onFinish: (fileContent: String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    // Local state to hold selected file info
    var fileName by remember { mutableStateOf("") }
    var fileContent by remember { mutableStateOf<String?>(null) }

    // Flag to show that file picking is in progress or finished
    var filePicker = rememberFilePickerLauncher(
        type = FileKitType.File(listOf("csv", "CSV")),
    ) {
        it?.let { file ->
            scope.launch {
                fileName = file.name
                fileContent = file.readString()
            }
        }
    }

    LaunchedEffect(fileContent) {
        fileContent?.let {
            print(fileContent)
        }
    }

    // A simple dialog UI – you can style as needed.
    CustomDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(Res.string.import_data),
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        leftIcon = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable { onDismiss() }
            )
        },
        rightIcon = {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Confirm",
                tint = if (fileContent != null) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                },
                modifier = Modifier.clickable {
                    if (fileContent != null) {
                        onFinish(fileContent!!)
                    }
                }
            )
        },
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    filePicker.launch()
                },
        ) {
            OutlinedText(
                text = { Text (
                    text = fileName.ifEmpty { stringResource(Res.string.no_file_selected) },
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (fileName.isEmpty()) {
                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    } else MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )},
                label = { Text(stringResource(Res.string.file)) },
                trailingIcon = {
                    Icon(
                        imageVector = CustomFileOpenIcon,
                        contentDescription = "Open file",
                    )
                }
            )
        }
    }
}
