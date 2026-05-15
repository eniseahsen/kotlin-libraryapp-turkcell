package com.turkcell.libraryapp.ui.model

import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.data.model.BorrowRecord

data class BorrowedBookUiModel(
    val book: Book,
    val borrow: BorrowRecord
)