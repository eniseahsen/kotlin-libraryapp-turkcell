package com.turkcell.libraryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turkcell.libraryapp.ui.components.BookCard
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.libraryapp.ui.viewmodel.BookViewModel


@Composable
fun HomeScreen(authViewModel: AuthViewModel,
               bookViewModel: BookViewModel){
    val profileState by authViewModel.profile.collectAsState();
    val books by bookViewModel.books.collectAsState();
    val isLoading by bookViewModel.isLoading.collectAsState();
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center ) {
        when{
            isLoading -> CircularProgressIndicator(modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary)
            books.isEmpty() -> Text("Henüz kitap yok.")
            else -> LazyColumn(modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(books, key = {it.id}){
                    book ->
                    BookCard(book = book,
                        modifier = Modifier.fillMaxWidth(),

                        onDeleteClick = {
                            bookViewModel.deleteBook(book.id)

                        },

                        onEditClick = {
                            bookViewModel.updateBook(it)
                        }
                        )
                }
            }
        }

    }
}