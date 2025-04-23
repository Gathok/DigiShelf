package de.malteans.digishelf.core.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.malteans.digishelf.core.domain.Book
import de.malteans.digishelf.core.domain.BookRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModel (
    private val repository: BookRepository
): ViewModel() {

    private val _bookId = MutableStateFlow<Long?>(null)

    private val _bookSeriesList = repository.querySeries()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _book: Flow<Book?> = _bookId
        .flatMapLatest { bookId ->
            if (bookId != null) {
                repository.getBook(bookId)
            } else {
                flowOf(null)
            }
        }

    private val _state = MutableStateFlow(DetailsState())

    val state = combine(_book, _state, _bookSeriesList) { book, state, bookSeriesList ->
        val coverImageChanged = state.imageUrl != book?.imageUrl
        val isbnChanged = state.isbn != book?.isbn
        val ratingChanged = !((state.rating == book?.rating) ||
                (state.rating == 0 && book?.rating == null))
        val titleChanged = state.title != book?.title
        val authorChanged = state.author != book?.author
        val priceChanged = state.price != book?.price || state.currency != book?.currency
        val pageCountChanged = state.pageCount != book?.pageCount
        val statusChanged = (state.possessionStatus != book?.possessionStatus)
                || (state.readStatus != book?.readStatus)
        val readingTimeChanged = state.readingTime != book?.readingTime
        val seriesChanged = (state.series?.id != book?.bookSeries?.id)
        val descriptionChanged = state.description != book?.description

        state.copy(
            bookId = book?.id,
            book = book,

            imageUrlChanged = coverImageChanged,
            isbnChanged = isbnChanged,
            ratingChanged = ratingChanged,
            titleChanged = titleChanged,
            authorChanged = authorChanged,
            priceChanged = priceChanged,
            pagesChanged = pageCountChanged,
            descriptionChanged = descriptionChanged,
            statusChanged = statusChanged,
            readingTimeChanged = readingTimeChanged,
            seriesChanged = seriesChanged,
            somethingChanged = coverImageChanged || isbnChanged || ratingChanged || titleChanged ||
                    authorChanged || priceChanged || pageCountChanged || statusChanged ||
                    readingTimeChanged || seriesChanged || descriptionChanged,

            bookSeriesList = bookSeriesList,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailsState())

    fun onAction(action: DetailsAction) {
        when (action) {
            is DetailsAction.SetBookId -> {
                _bookId.update {
                    action.bookId
                }
                viewModelScope.launch {
                    onBookChanged(
                        book = repository.getBook(action.bookId).first()
                            ?: throw IllegalArgumentException("Invalide book id ${action.bookId} passed to DetailsViewModel")
                    )
                }
            }
            is DetailsAction.TitleChanged -> {
                _state.value = _state.value.copy(title = action.title)
            }

            is DetailsAction.AuthorChanged -> {
                _state.value = _state.value.copy(author = action.author)
            }
            is DetailsAction.IsbnChanged -> {
                val newIsbn = action.isbn
                _state.update {
                    it.copy(isbn = newIsbn)
                }
                //check if isbn is already in the database
                viewModelScope.launch {
                    repository.queryBooks(isbnQuery = newIsbn).first() . forEach { bookWithIsbn ->
                        if (bookWithIsbn.id != _state.value.bookId) {
                            _state.update {
                                it.copy(isDoubleIsbn = true)
                            }
                            return@launch
                        }
                    }
                    _state.update {
                        it.copy(isDoubleIsbn = false)
                    }
                }
            }
            is DetailsAction.PageCountChanged -> {
                _state.update { it.copy(
                    pageCount = action.pages
                ) }
            }
            is DetailsAction.PriceChanged -> {
                _state.update { it.copy(
                    price = action.price,
                    currency = action.currency,
                ) }
            }
            is DetailsAction.StatusChanged -> {
                _state.update { it.copy(
                    possessionStatus = action.possessionStatus,
                    readStatus = action.readStatus,
                ) }
            }
            is DetailsAction.RatingChanged -> {
                _state.value = _state.value.copy(rating = action.rating)
            }
            is DetailsAction.DescriptionChanged -> {
                _state.value = _state.value.copy(description = action.description)
            }
            is DetailsAction.SeriesChanged -> {
                _state.value = _state.value.copy(series = action.series)
            }
            is DetailsAction.ReadingTimeChanged -> {
                _state.value = _state.value.copy(readingTime = action.readingTime)
            }
            is DetailsAction.ImageUrlChanged -> {
                _state.value = _state.value.copy(imageUrl = action.coverImage)
            }
            is DetailsAction.SetOnlineDescription -> {
                _state.value = _state.value.copy(onlineDescription = action.onlineDescription)
            }
            is DetailsAction.SwitchEditing -> {
                _state.value = _state.value.copy(isEditing = !_state.value.isEditing)
            }
            is DetailsAction.UpdateBook -> {
                val book = state.value.book?.copy(
                    imageUrl = _state.value.imageUrl,
                    isbn = _state.value.isbn,
                    rating = when (_state.value.rating) {
                        0 -> null
                        else -> _state.value.rating
                    },
                    title = _state.value.title,
                    author = _state.value.author,
                    pageCount = _state.value.pageCount,
                    price = _state.value.price,
                    possessionStatus = _state.value.possessionStatus,
                    readStatus = _state.value.readStatus,
                    readingTime = _state.value.readingTime,
                    bookSeries = _state.value.series,
                    description = _state.value.description,
                ) ?: throw IllegalStateException("No book to update")
                viewModelScope.launch {
                    if (book.bookSeries?.id == 0L) {
                        val seriesId = repository.addSeries(book.bookSeries)
                        _state.update { it.copy(
                            series = book.bookSeries.copy(id = seriesId)
                        ) }
                        repository.updateBook(book.copy(
                            bookSeries = book.bookSeries.copy(id = seriesId)
                        ))
                    } else {
                        repository.updateBook(book)
                    }
                }
            }
            is DetailsAction.DeleteBook -> {
                viewModelScope.launch {
                    repository.trashBook(state.value.bookId
                        ?: throw IllegalStateException("No book to delete"))
                }
            }
            is DetailsAction.ResetState -> {
                _state.value = DetailsState()
            }

            else -> TODO("This should not happen")
        }
    }

    private fun onBookChanged(book: Book) {
        _state.update {
            it.copy(
                imageUrl = book.imageUrl,
                isbn = book.isbn,
                rating = book.rating ?: 0,
                title = book.title,
                author = book.author,
                pageCount = book.pageCount,
                price = book.price,
                currency = book.currency,
                possessionStatus = book.possessionStatus,
                readStatus = book.readStatus,
                readingTime = book.readingTime,
                series = book.bookSeries,
                description = book.description,
                onlineDescription = book.onlineDescription,
                isEditing = false,
            )
        }
    }
}
