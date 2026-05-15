package com.turkcell.libraryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turkcell.libraryapp.data.repository.AuthRepository
import com.turkcell.libraryapp.data.repository.BookRepository
import com.turkcell.libraryapp.data.repository.BorrowRepository
import com.turkcell.libraryapp.ui.factory.BorrowViewModelFactory
import com.turkcell.libraryapp.ui.screen.BookBorrowScreen
import com.turkcell.libraryapp.ui.screen.BorrowedBookScreen
import com.turkcell.libraryapp.ui.screen.BookManagementSystem
import com.turkcell.libraryapp.ui.screen.HomeScreen
import com.turkcell.libraryapp.ui.screen.LoginScreen
import com.turkcell.libraryapp.ui.screen.RegisterScreen
import com.turkcell.libraryapp.ui.screen.SplashScreen

import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.libraryapp.ui.viewmodel.BookViewModel
import com.turkcell.libraryapp.ui.viewmodel.BorrowViewModel

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {

    val authRepository = AuthRepository()
    val bookRepository = BookRepository()
    val borrowRepository = BorrowRepository()

    val authViewModel: AuthViewModel = viewModel()
    val bookViewModel: BookViewModel = viewModel()
    val borrowViewModel: BorrowViewModel = viewModel(
        factory = BorrowViewModelFactory(
            borrowRepository,
            bookRepository,
            authRepository
        )
    )
    NavHost(navController = navController, startDestination = Screen.Login.route){

        composable (Screen.Splash.route) { SplashScreen(authViewModel,
            onAuthenticated = {
                role ->
                navController.navigate(Screen.Homepage.route){
                    popUpTo(Screen.Splash.route){inclusive = true}
                }
            },
            onUnauthenticated = {
                navController.navigate(Screen.Login.route){
                    popUpTo(Screen.Splash.route){inclusive = true}
                }

            })}

        composable(Screen.Login.route){LoginScreen(
            onNavigateToRegister = {navController.navigate(Screen.Register.route)},
            onLoginSuccess = {role ->
                navController.navigate(Screen.Homepage.route){
                    popUpTo(Screen.Login.route){inclusive=true} //geri bstığında çıkış yapma olmasın yığın temizlenmiş oluyor
                    //logine kadra olan ekranları temizle login ekranını da sil
                }

            },
            authViewModel = authViewModel
        ) }
        composable(Screen.Register.route){ RegisterScreen(
            onNavigateToLogin = {navController.navigate(Screen.Login.route)},
            onRegisterSuccess = { role ->
                navController.navigate(Screen.Homepage.route){
                    popUpTo(Screen.Register.route){
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
            authViewModel = authViewModel
        ) }


        composable(Screen.BookBorrowScreen.route) { BookBorrowScreen(
            bookViewModel = bookViewModel,
            borrowViewModel = borrowViewModel
        ) }

        composable(Screen.BorrowedBooksScreen.route){
            BorrowedBookScreen(
                borrowViewModel = borrowViewModel,
                onNavigateBack = { navController.popBackStack()}
            )
        }

        composable(Screen.BookManagementSystem.route){
            BookManagementSystem(
                authViewModel = authViewModel,
                bookViewModel = bookViewModel
            )
        }

        composable(Screen.Homepage.route){
            HomeScreen(
                onNavigateToManagement = {
                    navController.navigate(Screen.BookManagementSystem.route)
                },
                onNavigateToBorrow = {
                    navController.navigate(Screen.BookBorrowScreen.route)
                },
                onNavigateToBorrowedBooks =  {
                    navController.navigate(Screen.BorrowedBooksScreen.route)
                }

            )
        }

    }

}