package de.malteans.digishelf.core.presentation.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.malteans.digishelf.core.presentation.components.CustomTopBar
import de.malteans.digishelf.core.presentation.settings.components.ImportDialog
import de.malteans.digishelf.core.presentation.settings.components.SettingsItem
import de.malteans.digishelf.core.presentation.settings.components.icons.CustomCloudSyncIcon
import de.malteans.digishelf.core.presentation.settings.components.icons.CustomDownloadIcon
import de.malteans.digishelf.core.presentation.settings.components.icons.CustomUploadIcon
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.cloud_completion
import digishelf.composeapp.generated.resources.cloud_completion_desc
import digishelf.composeapp.generated.resources.empty
import digishelf.composeapp.generated.resources.export_data
import digishelf.composeapp.generated.resources.import_data
import digishelf.composeapp.generated.resources.settings
import digishelf.composeapp.generated.resources.settings_export_desc
import digishelf.composeapp.generated.resources.settings_import_desc
import digishelf.composeapp.generated.resources.settings_trash_desc
import digishelf.composeapp.generated.resources.trash
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel = koinViewModel(),
    openDrawer: () -> Unit,
    onTrashClicked: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    SettingsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is SettingsAction.OnTrashClicked -> {
                    onTrashClicked()
                    viewModel.onAction(action)
                }
                is SettingsAction.OnOpenDrawer -> openDrawer()

                else -> viewModel.onAction(action)
            }

        }
    )
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    var showImportDialog by remember { mutableStateOf(false) }

    // Show the import dialog when triggered.
    if (showImportDialog) {
        ImportDialog(
            onDismiss = { showImportDialog = false },
            onFinish = { fileContent ->
                onAction(SettingsAction.OnImport(fileContent))
                showImportDialog = false
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopBar(
                title = {
                    Text(text = stringResource(Res.string.settings))
                },
                navigationAction = { onAction(SettingsAction.OnOpenDrawer) }
            )
        },
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize(),
        ) {
            SettingsItem(
                title = stringResource(Res.string.trash)
                        + if (state.trashIsEmpty) " (${stringResource(Res.string.empty)})" else "",
                description = stringResource(Res.string.settings_trash_desc),
                icon = CustomUploadIcon, // (or custom icon for trash if needed)
                onClick = { onAction(SettingsAction.OnTrashClicked) },
                onLongClick = { }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            SettingsItem(
                title = stringResource(Res.string.export_data),
                description = stringResource(Res.string.settings_export_desc),
                icon = CustomDownloadIcon,
                onClick = { onAction(SettingsAction.OnExportClicked) },
                onLongClick = { }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            SettingsItem(
                title = stringResource(Res.string.import_data),
                description = stringResource(Res.string.settings_import_desc),
                icon = CustomUploadIcon,
                onClick = { showImportDialog = true },
                onLongClick = { }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            SettingsItem(
                title = stringResource(Res.string.cloud_completion),
                description = stringResource(Res.string.cloud_completion_desc),
                icon = CustomCloudSyncIcon,
                onClick = { onAction(SettingsAction.OnCloudCompleteClicked) },
                onLongClick = { }
            )
        }
    }

    // Loading ----------------------------------------------------------------
    AnimatedVisibility(
        visible = state.isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

fun formatForCsv(value: Any?): String {
    return "\"$value\""
}