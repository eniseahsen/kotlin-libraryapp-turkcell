package com.turkcell.libraryapp.ui.navigation

sealed class Screen(val route: String){
    object Login: Screen("login")
    object Register: Screen("register")
    object Homepage: Screen("homepage")
    object Splash : Screen("splash")
    object BookBorrowScreen: Screen("bookborrow")
    object BorrowedBooksScreen : Screen("borrowedbooks")
    object BookManagementSystem : Screen("bookmanagement")
}