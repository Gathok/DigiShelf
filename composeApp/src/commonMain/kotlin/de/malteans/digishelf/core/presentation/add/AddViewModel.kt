package de.malteans.digishelf.core.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.malteans.digishelf.core.domain.Book
import de.malteans.digishelf.core.domain.BookRepository
import de.malteans.digishelf.core.domain.errorHandling.onError
import de.malteans.digishelf.core.domain.errorHandling.onSuccess
import de.malteans.digishelf.core.presentation.components.UiText
import de.malteans.digishelf.core.presentation.components.toUiText
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.error
import digishelf.composeapp.generated.resources.error_completion
import digishelf.composeapp.generated.resources.error_unknown
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddViewModel (
    private val repository: BookRepository
): ViewModel() {

    private val _bookSeriesList = repository.querySeries()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    private val _state = MutableStateFlow(AddState())

    val state = combine(_state, _bookSeriesList) { state, bookSeriesList ->
        state.copy(
            bookSeriesList = bookSeriesList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AddState())

    fun onAction(action: AddAction) {
        when(action) {
            // OnChanged actions ------------------------------------------------------------------
            is AddAction.OnTitleChanged -> {
                _state.update {
                    it.copy(
                        title = action.title
                    )
                }
            }
            is AddAction.OnAuthorChanged -> {
                _state.value = _state.value.copy(author = action.author)
            }
            is AddAction.OnIsbnChanged -> {
                val isbn = action.isbn.replace(Regex("\\D"), "")

                _state.update { it.copy(
                    isbn = isbn,
                    showCompleteWithIsbn = isbn.isIsbnFormat(),
                ) }
                viewModelScope.launch {
                    _state.update { it.copy(
                        isDoubleIsbn = isbn.isDoubleIsbn()
                    ) }
                }
            }
            is AddAction.OnPossessionStatusChanged -> {
                _state.value = _state.value.copy(possessionStatus = action.possessionStatus)
            }
            is AddAction.OnReadStatusChanged -> {
                _state.value = _state.value.copy(readStatus = action.readStatus)
            }
            is AddAction.OnRatingChanged -> {
                _state.value = _state.value.copy(rating = action.rating)
            }
            is AddAction.OnPagesChanged -> {
                _state.value = _state.value.copy(
                    pages = action.pages,
                    pagesError = action.pages.isBlank() || action.pages.toIntOrNull() != null
                )
            }
            is AddAction.OnPriceChanged -> {
                _state.value = _state.value.copy(
                    price = action.price,
                    priceError = action.price.isBlank() || action.price.toDoubleOrNull() != null
                )
            }
            is AddAction.OnImageUrlChanged -> {
                _state.value = _state.value.copy(imageUrl = action.imageUrl)
            }
            // On Auto Complete -------------------------------------------------------------------
            is AddAction.OnAutoComplete -> {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }

                viewModelScope.launch {
                    repository.fetchBookFromRemote(
                        isbn = if (action.isbn?.isIsbnFormat() == true) action.isbn else null,
                        title = action.title,
                        author = action.author
                    )
                        .onSuccess { book ->
                            _state.update {
                                it.copy(
                                    title = book.title,
                                    author = book.author,
                                    isbn = book.isbn,
                                    isDoubleIsbn = book.isbn.isDoubleIsbn(),
                                    imageUrl = book.imageUrl,
                                    pages = book.pageCount?.toString() ?: "",
                                    price = book.price?.toString() ?: "",
                                    isLoading = false,
                                )
                            }
                        }
                        .onError { error ->
                            _state.update {
                                it.copy(
                                    errorTitle = UiText.StringResourceId(Res.string.error_completion),
                                    errorMessage = error.toUiText(),
                                    showError = true,
                                    isLoading = false,
                                )
                            }
                        }
                }
            }
            // Error handling ---------------------------------------------------------------------
            is AddAction.OnDismissError -> {
                _state.update {
                    it.copy(
                        showError = false,
                        errorTitle = UiText.StringResourceId(Res.string.error),
                        errorMessage = UiText.StringResourceId(Res.string.error_unknown)
                    )
                }
            }
            is AddAction.OnShowIncompleteError -> {
                _state.update {
                    it.copy(
                        showIncompleteError = true
                    )
                }
            }
            is AddAction.OnDismissIncompleteError -> {
                _state.update {
                    it.copy(
                        showIncompleteError = false
                    )
                }
            }
            // Other actions ----------------------------------------------------------------------
            is AddAction.AddBook -> {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }

                val title = _state.value.title.trim()
                if (title.isBlank()) return
                val author = _state.value.author.trim()
                val isbn = _state.value.isbn.trim()
                val possessionStatus = _state.value.possessionStatus
                val readStatus = _state.value.readStatus
                val rating = _state.value.rating
                val pageCount = _state.value.pages.toIntOrNull()
                val price = _state.value.price.toDoubleOrNull()
                val imageUrl = _state.value.imageUrl

                var book = Book(
                    title = title,
                    author = author,
                    isbn = isbn,
                    possessionStatus = possessionStatus,
                    readStatus = readStatus,
                    rating = rating,
                    pageCount = pageCount,
                    imageUrl = imageUrl,
                    price = price,
                    currency = "EUR", // TODO: Implement currency selection
                    bookSeries = _state.value.bookSeries
                )

                viewModelScope.launch {
                    val newId = repository.addBook(book)
                    _state.value = AddState(
                        addedBookId = newId,
                    )
                }
            }
            is AddAction.ClearFields -> {
                _state.value = AddState()
            }
            is AddAction.OnBookSeriesChanged -> {
                _state.value = _state.value.copy(
                    bookSeries = action.bookSeries
                )
            }

            else -> TODO("This should not happen")
        }
    }

    private suspend fun String.isDoubleIsbn(): Boolean {
        repository.queryBooks(isbnQuery = this).first() . forEach { book ->
            if (book.isbn == this) {
                return true
            }
        }
        return false
    }
}

fun String.isDigitsOnly() : Boolean {
    return all { it.isDigit() }
}

fun String.isIsbnFormat() : Boolean {
    return length == 13 && isDigitsOnly() && startsWith("978")
}
