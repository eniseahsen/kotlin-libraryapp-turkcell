package com.turkcell.libraryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//model repository viewmodel
class BookViewModel: ViewModel() {
    private val repository = BookRepository()

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadBooks()
    }



    fun loadBooks(){
        viewModelScope.launch {
            _isLoading.value = true
            repository
                .getAllBooks()
                .onSuccess { _books.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }

    fun deleteBook(id: String){
        println("DELETE ID = $id")

        viewModelScope.launch {
            repository.deleteBook(id)
                .onSuccess {
                    _books.value = _books.value.filter { it.id != id }

                }
                .onFailure {
                    println("DELETE ERROR = ${it.message}")
                    _error.value = it.message
                }
        }
    }

    fun updateBook(book: Book){
        viewModelScope.launch {
            repository.updateBook(book)
                .onSuccess { loadBooks() }
                .onFailure {
                    _error.value = it.message
                }
        }

    }

    fun searchBooks(query: String){
        viewModelScope.launch {
            repository.searchBooks(query)
                .onSuccess {
                    _books.value = it
                }
                .onFailure { _error.value = it.message }
        }
    }

}