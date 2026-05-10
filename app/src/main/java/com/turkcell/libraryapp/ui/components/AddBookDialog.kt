package com.turkcell.libraryapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.turkcell.libraryapp.data.model.Book

@Composable
fun AddBookDialog(
    onDismiss: () -> Unit,
    onSave: (Book) -> Unit
){
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("")}
    var category by remember { mutableStateOf("")}
    var isbn by remember { mutableStateOf("")}
    var pagecount by remember { mutableStateOf("")}
    var totalCopies by remember { mutableStateOf("")}
    var availableCopies by remember { mutableStateOf("")}

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Kitap Ekle")},
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it},
                    label = { Text("Başlık")}

                )
                OutlinedTextField(
                    value = author,
                    onValueChange = {author = it},
                    label = { Text("Yazar")}
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = {category = it},
                    label = { Text("Kategori")}
                )

                OutlinedTextField(
                    value = isbn,
                    onValueChange = {isbn = it},
                    label = { Text("ISBN")}
                )
                OutlinedTextField(
                    value = pagecount,
                    onValueChange = {pagecount = it},
                    label = { Text("Sayfa Sayısı")}
                )
                OutlinedTextField(
                    value = totalCopies,
                    onValueChange = {totalCopies = it},
                    label = { Text("Toplam ")}
                )
                OutlinedTextField(
                    value = availableCopies,
                    onValueChange = {availableCopies = it},
                    label = { Text("Müsait")}
                )

            }
        },
        confirmButton = {
            Button(onClick = {
                val book = Book(
                    title = title,
                    author = author,
                    category = category,
                    isbn = isbn,
                    pageCount = pagecount.toIntOrNull() ?: 0,
                    totalCopies = totalCopies.toIntOrNull() ?: 0,
                    availableCopies = availableCopies.toIntOrNull() ?: 0

                )
                onSave(book)
            }) {
                Text("Kaydet")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss){
                Text("İptal")
            }
        }

    )

}