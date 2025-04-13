package de.malteans.digishelf.core.data.network

import de.malteans.digishelf.core.data.network.dto.BookResponse
import de.malteans.digishelf.core.domain.errorHandling.DataError
import de.malteans.digishelf.core.domain.errorHandling.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://www.googleapis.com/books/v1"

class KtorRemoteBookDataSource(
    private val client: HttpClient,
) : RemoteBookDataSource {

    override suspend fun fetchBook(
        isbn: String?, title: String?, author: String?
    ): Result<BookResponse, DataError.Remote> {
        val query = if (isbn != null) {
            "isbn:$isbn"
        } else if (title != null && author != null) {
            "intitle:$title+inauthor:$author"
        } else if (title != null) {
            "intitle:$title"
        } else if (author != null) {
            "inauthor:$author"
        } else {
            return Result.Error(DataError.Remote.INVALIDE_QUERY)
        }

        return safeCall<BookResponse> {
            client.get("$BASE_URL/volumes") {
                parameter("q", query)
            }
        }
    }
}
