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
fun BookCard(
    book: Book,
    modifier: Modifier = Modifier,
    onDeleteClick: (Book) -> Unit,
    onEditClick: (Book) -> Unit
)
{
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

            Spacer(modifier = Modifier.width(8.dp))
            Row{
                Button(onClick = {
                    onEditClick(book)
                }){
                    Text("Güncelle")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    onDeleteClick(book)
                }){
                    Text("Sil")
                }
            }

        }
    }
}