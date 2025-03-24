package de.malteans.digishelf.core.presentation.add

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import de.malteans.digishelf.core.domain.BookSeries
import de.malteans.digishelf.core.presentation.add.components.RatingBar
import de.malteans.digishelf.core.presentation.components.CustomAlertDialog
import de.malteans.digishelf.core.presentation.components.Dropdown
import de.malteans.digishelf.core.presentation.components.customIconBarcodeScanner
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.add_book
import digishelf.composeapp.generated.resources.author
import digishelf.composeapp.generated.resources.auto_complete
import digishelf.composeapp.generated.resources.back
import digishelf.composeapp.generated.resources.book_added_success
import digishelf.composeapp.generated.resources.book_series
import digishelf.composeapp.generated.resources.data_incomplete
import digishelf.composeapp.generated.resources.error_msg_add_incomplete
import digishelf.composeapp.generated.resources.is_double_isbn
import digishelf.composeapp.generated.resources.isbn
import digishelf.composeapp.generated.resources.no_series
import digishelf.composeapp.generated.resources.owned
import digishelf.composeapp.generated.resources.read
import digishelf.composeapp.generated.resources.scan
import digishelf.composeapp.generated.resources.show
import digishelf.composeapp.generated.resources.submit
import digishelf.composeapp.generated.resources.title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddScreenRoot(
    viewModel: AddViewModel = koinViewModel(),
    onShowScanner: () -> Unit,
    onShowOverview: () -> Unit,
    onShowBookDetail: (Long) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    AddScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AddAction.OnShowOverview -> onShowOverview()
                is AddAction.OnScan -> onShowScanner()
                is AddAction.OnShowBookDetail -> {
                    onShowBookDetail(action.bookId)
                    viewModel.onAction(AddAction.ClearFields)
                }

                else -> viewModel.onAction(action)
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    state: AddState,
    onAction: (AddAction) -> Unit,
) {
    val focusManger = LocalFocusManager.current

    val snackbarHostState = remember { SnackbarHostState() }

    val resBookAdded = stringResource(Res.string.book_added_success)
    val resShow = stringResource(Res.string.show)

    LaunchedEffect(state.addedBookId) {
        if (state.addedBookId != null) {
            val snackbarResult = snackbarHostState.showSnackbar(
                message = resBookAdded,
                actionLabel = resShow,
                withDismissAction = true,
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                onAction(AddAction.OnShowBookDetail(state.addedBookId))
            }
        }
    }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onAction(AddAction.OnShowOverview) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(Res.string.back)
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(Res.string.add_book),
                        modifier = Modifier.clickable { onAction(AddAction.ClearFields) }
                    )
                },
                actions = {
                    IconButton(onClick = {
                        if (state.title.isNotBlank()) {
                            if (state.author.isBlank() || state.isbn.isBlank()) {
                                onAction(AddAction.OnShowIncompleteError)
                            } else {
                                onAction(AddAction.AddBook)
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(Res.string.submit),
                            tint = if (state.title.isNotBlank()) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .padding(bottom = 48.dp)
        ) { snackbarData ->
            Snackbar(
                snackbarData = snackbarData,
                shape = RoundedCornerShape(30),
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.onSurface,
                actionColor = MaterialTheme.colorScheme.primary,
                actionContentColor = MaterialTheme.colorScheme.primary,
                dismissActionContentColor = MaterialTheme.colorScheme.onSurface,
            )
        } },
        modifier = Modifier.fillMaxSize(),
    ) { pad ->
        val scrollState = rememberScrollState()

        if (state.showError) {
            CustomAlertDialog(
                onDismissRequest = {
                    onAction(AddAction.OnDismissError)
                },
                onConfirm = {
                    onAction(AddAction.OnDismissError)
                },
                noConfirmButton = true,
                title = { Text(state.errorTitle.asString()) },
                text = { Text(state.errorMessage.asString()) },
            )
        }

        if (state.showIncompleteError) {
            CustomAlertDialog(
                onDismissRequest = {
                    onAction(AddAction.OnDismissIncompleteError)
                },
                onConfirm = {
                    onAction(AddAction.OnDismissIncompleteError)
                    onAction(AddAction.AddBook)
                },
                title = { Text(stringResource(Res.string.data_incomplete)) },
                text = { Text(stringResource(Res.string.error_msg_add_incomplete)) }
            )
        }

        LaunchedEffect(state.isLoading) {
            if (state.isLoading) {
                focusManger.clearFocus()
            }
        }

        AnimatedVisibility(
            visible = state.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        Column(
            modifier = Modifier
                .padding(12.dp, pad.calculateTopPadding(), 12.dp, 12.dp)
                .verticalScroll(scrollState)
                .blur(if (state.isLoading) 1.dp else 0.dp)
        ) {
            // Title ------------------------------------------------------------------------------
            OutlinedTextField(
                value = state.title,
                onValueChange = {
                    onAction(AddAction.OnTitleChanged(it))
                },
                label = { Text(stringResource(Res.string.title)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    if (state.title.length >= 8) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(Res.string.auto_complete),
                            modifier = Modifier
                                .clickable {
                                    onAction(AddAction.OnAutoComplete(title = state.title))
                                }
                                .padding(end = 8.dp)
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Author -----------------------------------------------------------------------------
            OutlinedTextField(
                value = state.author,
                onValueChange = {
                    onAction(AddAction.OnAuthorChanged(it))
                },
                label = { Text(stringResource(Res.string.author)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            // ISBN -------------------------------------------------------------------------------
            Spacer(modifier = Modifier.height(16.dp))
            if (state.isDoubleIsbn) {
                Row (
                    Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = stringResource(Res.string.is_double_isbn),
                        modifier = Modifier.padding(end = 8.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = stringResource(Res.string.is_double_isbn),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            OutlinedTextField(
                value = state.isbn,
                onValueChange = {
                    onAction(AddAction.OnIsbnChanged(it))
                },
                label = { Text(stringResource(Res.string.isbn)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    if (state.showCompleteWithIsbn) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(Res.string.auto_complete),
                            modifier = Modifier
                                .clickable {
                                    onAction(AddAction.OnAutoComplete(isbn = state.isbn))
                                }
                                .padding(end = 8.dp)
                        )
                    } else {
                        Icon(
                            imageVector = customIconBarcodeScanner(),
                            contentDescription = stringResource(Res.string.scan),
                            modifier = Modifier
                                .clickable {
                                    onAction(AddAction.OnScan)
                                }
                                .padding(end = 8.dp)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Checkbox(
                        checked = state.possessionStatus,
                        onCheckedChange = {
                            onAction(AddAction.OnPossessionStatusChanged(it))
                        },
                    )
                    Text(stringResource(Res.string.owned))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Checkbox(
                        checked = state.readStatus,
                        onCheckedChange = {
                            onAction(AddAction.OnReadStatusChanged(it))
                        },
                    )
                    Text(stringResource(Res.string.read))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column{
                    // Rating with clickable stars
                    RatingBar(
                        current = state.rating,
                        onRatingChanged = { newRating ->
                            onAction(AddAction.OnRatingChanged(newRating))
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Dropdown(
                    selectedOption = Pair(
                        state.bookSeries,
                        state.bookSeries?.title ?: stringResource(Res.string.no_series)
                    ),
                    options = state.bookSeriesList.associateBy({ it }, { it.title }),
                    label = stringResource(Res.string.book_series),
                    onValueChanged = { onAction(AddAction.BookSeriesChanged(it as BookSeries?)) },
                )
            }
        }
    }
}

//private suspend fun completeWithIsbn(
//    onEvent: (AddAction) -> Unit,
//    isbnFromNav: String,
//    pErrorTitleResource: Int,
//    state: AddState,
//    pErrorMessageResource: Int,
//    pShowError: Boolean
//): Triple<Int, Int, Boolean> {
//    var errorTitleResource = pErrorTitleResource FIXME: Complete with ISBN
//    var errorMessageResource = pErrorMessageResource
//    var showError = pShowError
//    val bookResponse = BookModel.bookService.getBook(
//        isbn = "isbn:$isbnFromNav"
//    )
//    try {
//        onEvent(AddAction.TitleChanged(""))
//        onEvent(AddAction.AuthorChanged(""))
//        onEvent(AddAction.TitleChanged(bookResponse.items[0].volumeInfo.title))
//        onEvent(AddAction.AuthorChanged(bookResponse.items[0].volumeInfo.authors.joinToString(", ")))
//    } catch (e: Exception) {
//        errorTitleResource = R.string.error_scan
//        errorMessageResource = if (state.title.isBlank()) {
//            R.string.error_msg_scan
//        } else {
//            R.string.error_msg_scan_author
//        }
//        showError = true
//    }
//    //return list of errorTitleResource, errorMessageResource, showError
//    return Triple(errorTitleResource, errorMessageResource, showError)
//}

/* RIP BookSeriesDropDown
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSeriesDropDown(
    selectedOption: String,
    options: List<String>,
    label: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var currentInput by remember { mutableStateOf(selectedOption) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
            if (expanded)
                currentInput = ""
        },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = !expanded,
            value = if (expanded) currentInput else selectedOption,
            onValueChange = {
                currentInput = it
                onValueChanged(it)
            },
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            singleLine = true,
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
                if (option.contains(currentInput, ignoreCase = true)) {
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            expanded = false
                            currentInput = option
                            onValueChanged(option)
                        }
                    )
                }
            }
            if (currentInput.isNotBlank() && !options.contains(currentInput)) {
                DropdownMenuItem(
                    text = { Text(text = currentInput) },
                    onClick = {
                        expanded = false
                        onValueChanged(currentInput)
                    }
                )
            }
        }
    }
}
*/

//@Preview
//@Composable
//fun AddBookScreenPreview() {
//    BookOverviewTheme {
//        AddBookScreen()
//    }
//}
