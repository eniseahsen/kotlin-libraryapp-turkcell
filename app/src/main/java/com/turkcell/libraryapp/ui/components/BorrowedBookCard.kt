package com.turkcell.libraryapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.ui.model.BorrowedBookUiModel

@Composable
fun BorrowedBookCard(
    item: BorrowedBookUiModel,
    onReturnClick: (Book) -> Unit
){
    val book = item.book
    val borrow = item.borrow

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)

    ){
        Column(modifier = Modifier.padding(16.dp)){
            Text(book.title, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(4.dp))

            Text("Yazar: ${book.author}")
            Spacer(modifier = Modifier.height(4.dp))

            Text("Kategori: ${book.category}")
            Spacer(modifier = Modifier.height(8.dp))

            Text("Ödünç alınma tarihi: ${borrow.borrowedAt}")
            Text("Teslim Tarihi: ${borrow.dueDate}")

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onReturnClick(book)}
            ){
                Text("iade et")
            }
        }
    }
}