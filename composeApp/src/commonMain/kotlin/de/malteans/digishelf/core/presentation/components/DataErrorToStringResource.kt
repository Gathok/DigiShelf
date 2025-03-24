package de.malteans.digishelf.core.presentation.components

import de.malteans.digishelf.core.domain.errorHandling.DataError
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.error_disk_full
import digishelf.composeapp.generated.resources.error_no_internet
import digishelf.composeapp.generated.resources.error_no_result
import digishelf.composeapp.generated.resources.error_request_timeout
import digishelf.composeapp.generated.resources.error_serialization
import digishelf.composeapp.generated.resources.error_too_many_requests
import digishelf.composeapp.generated.resources.error_unknown

fun DataError.toUiText(): UiText {
    val stringRes = when(this) {
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization
        DataError.Remote.NO_RESULT -> Res.string.error_no_result
        else -> Res.string.error_unknown
    }
    
    return UiText.StringResourceId(stringRes)
}