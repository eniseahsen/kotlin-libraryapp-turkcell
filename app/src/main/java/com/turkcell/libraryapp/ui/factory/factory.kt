package com.turkcell.libraryapp.ui.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turkcell.libraryapp.data.repository.AuthRepository
import com.turkcell.libraryapp.data.repository.BookRepository
import com.turkcell.libraryapp.data.repository.BorrowRepository
import com.turkcell.libraryapp.ui.viewmodel.BorrowViewModel

class BorrowViewModelFactory(
    private val borrowRepository: BorrowRepository,
    private val bookRepository: BookRepository,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BorrowViewModel(
            borrowRepository,
            bookRepository,
            authRepository
        ) as T
    }
}