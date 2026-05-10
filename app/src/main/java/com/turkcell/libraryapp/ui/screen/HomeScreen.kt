package com.turkcell.libraryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    onNavigateToManagement: () -> Unit,
    onNavigateToBorrow: () -> Unit,
    onNavigateToBorrowedBooks: () -> Unit

){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = onNavigateToManagement
        ){
            Text("Kitap Yönetimi")
        }
        Button(
            onClick = onNavigateToBorrow
        ){
            Text("Kitap Ödünç Al")
        }
        Button(
            onClick = onNavigateToBorrowedBooks
        ){
            Text("Ödünç Aldığım Kitaplar")
        }
    }
}
