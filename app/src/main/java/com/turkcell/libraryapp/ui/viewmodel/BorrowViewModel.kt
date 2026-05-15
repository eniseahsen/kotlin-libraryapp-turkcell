package com.turkcell.libraryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.data.model.BorrowRecord
import com.turkcell.libraryapp.ui.model.BorrowedBookUiModel
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
    private val _books = MutableStateFlow<List<BorrowedBookUiModel>>(emptyList())
    val books: StateFlow<List<BorrowedBookUiModel>> = _books



    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _borrowedBookIds = MutableStateFlow<Set<String>>(emptySet())
    val borrowedBookIds: StateFlow<Set<String>> = _borrowedBookIds



    fun borrowBook(book: Book){
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId() ?: return@launch
            val bookId = book.id ?: return@launch
            println("USER ID FROM AUTH = ${authRepository.getCurrentUserId()}")


            borrowRepository.borrowBook(
                BorrowRecord(
                    studentId = userId,
                    bookId = bookId
                )
            )

            _borrowedBookIds.value = _borrowedBookIds.value + bookId


            loadBorrowedBooks()




        }
    }

    fun returnBook(book: Book){
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId() ?: return@launch
            val bookId = book.id ?: return@launch




            borrowRepository.returnBook(bookId, userId)
            bookRepository.updateBook(
                book.copy(availableCopies = book.availableCopies - 1)
            )


            loadBorrowedBooks()
        }
    }

    fun loadBorrowedBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            val userId = authRepository.getCurrentUserId()
            if (userId == null) {
                _isLoading.value = false
                return@launch
            }

            val books = bookRepository.getAllBooks().getOrNull() ?: emptyList()
            val borrows = borrowRepository.getActiveBorrowByUser(userId).getOrNull() ?: emptyList()

            _borrowedBookIds.value = borrows.map { it.bookId }.toSet()

            _books.value = borrows.mapNotNull { borrow ->
                books.find { it.id == borrow.bookId }?.let {
                    BorrowedBookUiModel(book = it, borrow = borrow)
                }
            }
            _isLoading.value = false
        }
    }
}