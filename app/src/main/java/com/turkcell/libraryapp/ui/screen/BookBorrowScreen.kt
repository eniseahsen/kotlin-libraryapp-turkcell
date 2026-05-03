package com.turkcell.libraryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.turkcell.libraryapp.ui.components.BookBorrowCard
import com.turkcell.libraryapp.ui.components.BookCard
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.libraryapp.ui.viewmodel.BookViewModel
import com.turkcell.libraryapp.ui.viewmodel.BorrowViewModel

@Composable
fun BookBorrowScreen(borrowViewModel: BorrowViewModel){
    val books by borrowViewModel.books.collectAsState()
    val isLoading by borrowViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        borrowViewModel.loadData()
    }



    Column(modifier = Modifier.fillMaxSize().statusBarsPadding(),horizontalAlignment = Alignment.CenterHorizontally){
        when{
            isLoading -> CircularProgressIndicator(modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary)
            books.isEmpty() -> Text("Henüz kitap yok.")
            else -> LazyColumn(modifier = Modifier.fillMaxSize().weight(1f),
                contentPadding = PaddingValues(32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(books, key = { it.book.id ?: "" }){
                        item ->
                    BookBorrowCard(book = item.book,
                        modifier = Modifier.fillMaxWidth(),
                        isBorrowedByUser = item.isBorrowedByUser,


                        onBorrowClick = {
                            borrowViewModel.borrowBook(item.book)
                        },

                        onReturnClick = {
                            borrowViewModel.returnBook(item.book)

                        }
                    )
                }
            }
        }
    }
}