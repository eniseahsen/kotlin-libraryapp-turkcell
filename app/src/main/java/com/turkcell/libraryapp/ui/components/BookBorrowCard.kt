package com.turkcell.libraryapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.turkcell.libraryapp.data.model.Book

@Composable
fun BookBorrowCard(
    book: Book,
    isBorrowedByUser: Boolean,
    modifier: Modifier = Modifier,
    onBorrowClick: (Book) -> Unit,
    onReturnClick: (Book) -> Unit
){
    Card(modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)){
        Column(modifier = Modifier.padding(16.dp)){
            Text(
                text = book.title,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "ID: ${book.id}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Kategori: ${book.category}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Yazar: ${book.author}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "ISBN: ${book.isbn}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Toplam: ${book.totalCopies}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Müsait: ${book.availableCopies}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.width(8.dp))
            Row{
                if(book.availableCopies > 0 && !isBorrowedByUser){
                    Button(onClick = {
                        onBorrowClick(book)
                    }){
                        Text("Ödünç Al")
                    }
                }

                if(book.availableCopies == 0){
                    Text(
                        text = "Stokta yok",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                if(isBorrowedByUser){
                    Button(
                        onClick = { onReturnClick(book)}
                    ){
                        Text("iade et")
                    }
                }

            }
        }
    }

}