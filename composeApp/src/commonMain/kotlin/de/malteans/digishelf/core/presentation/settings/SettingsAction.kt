package de.malteans.digishelf.core.presentation.settings

import de.malteans.digishelf.core.domain.Book

sealed class SettingsAction {
    data object OnBack: SettingsAction()
    data object OnOpenDrawer: SettingsAction()

    data object SettingsOpened: SettingsAction()

    data object OnTrashClicked : SettingsAction()
    data class OnTrashRestoreClicked(val book: Book) : SettingsAction()
    data class OnTrashDeleteClicked(val book: Book) : SettingsAction()
    data object OnTrashDeleteAllClicked : SettingsAction()
    data object OnTrashRestoreAllClicked : SettingsAction()
    data object OnExportClicked : SettingsAction()
    data object OnCloudCompleteClicked: SettingsAction()

    data class SetLoading(val isLoading: Boolean) : SettingsAction()
    data object ResetExportData : SettingsAction()

    data class OnImport(val fileContent: String) : SettingsAction()
}
