package com.turkcell.libraryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.data.model.BookUiModel
import com.turkcell.libraryapp.data.model.BorrowRecord
import com.turkcell.libraryapp.data.repository.AuthRepository
import com.turkcell.libraryapp.data.repository.BookRepository
import com.turkcell.libraryapp.data.repository.BorrowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BorrowViewModel(
    private val borrowRepository: BorrowRepository,
    private val bookRepository: BookRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _books = MutableStateFlow<List<BookUiModel>>(emptyList())
    val books: StateFlow<List<BookUiModel>> = _books

    private val _borrowedBookIds = MutableStateFlow<List<String>>(emptyList())
    val borrowedBookIds: StateFlow<List<String>> = _borrowedBookIds

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadData(){
        viewModelScope.launch {
            _isLoading.value = true

            val userId = authRepository.getCurrentUserId() ?: return@launch //sadece launch içinden çık
            val books = bookRepository.getAllBooks().getOrNull() ?: emptyList()
            val borrows = borrowRepository.getActiveBorrowByUser(userId).getOrNull() ?: emptyList()

            _borrowedBookIds.value = borrows.map {it.bookId} //her BorrowRecord’dan sadece bookId al

            _books.value = books.map { book ->
                BookUiModel(
                    book = book,
                    isBorrowedByUser = _borrowedBookIds.value.contains(book.id)
                )
            }
            _isLoading.value = false
        }
    }

    fun borrowBook(book: Book){
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId() ?: return@launch
            val bookId = book.id ?: return@launch

            borrowRepository.borrowBook(
                BorrowRecord(
                    id = "",
                    studentId = userId,
                    bookId = bookId
                )
            )



            loadData()
        }
    }

    fun returnBook(book: Book){
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId() ?: return@launch
            val bookId = book.id ?: return@launch


            borrowRepository.returnBook(bookId, userId)

            loadData()
        }
    }
}