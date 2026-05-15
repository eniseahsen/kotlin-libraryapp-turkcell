package com.turkcell.libraryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.turkcell.libraryapp.ui.components.BorrowedBookCard
import com.turkcell.libraryapp.ui.viewmodel.BorrowViewModel

@Composable
fun BorrowedBookScreen(
    borrowViewModel: BorrowViewModel,
    onNavigateBack: () -> Unit
){
    val books by borrowViewModel.books.collectAsState()
    val isLoading by borrowViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        borrowViewModel.loadBorrowedBooks()}
    Column(
        modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            books.isEmpty() -> {
                Text("Henüz Ödünç alınmış kitap yok.")
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ){
                    itemsIndexed(books) { index, item ->
                        BorrowedBookCard(
                            item = item,
                            onReturnClick = {
                                borrowViewModel.returnBook(it)
                            }
                        )
                    }
                }
            }
        }
    }

    }

