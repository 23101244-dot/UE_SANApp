package com.example.uesanapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uesanapp.presentation.auth.LoginScreen
import com.example.uesanapp.presentation.auth.RegisterScreen
import com.example.uesanapp.presentation.home.HomeScreen
import com.example.uesanapp.presentation.home.FavoritesScreen
import com.example.uesanapp.presentation.permitions.GalleryPermissionsScrean
import com.google.firebase.auth.FirebaseAuth // Importante: añade esta importación

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()

    // Verificamos si ya hay un usuario logueado en el dispositivo
    val currentUser = FirebaseAuth.getInstance().currentUser
    // Si hay usuario, vamos a "home", si no, a "register"
    val startDestination = if (currentUser != null) "home" else "register"

    NavHost(navController = navController, startDestination = startDestination){

        composable("register"){ RegisterScreen(navController) }

        composable("login"){ LoginScreen(navController) }

        composable("gallery"){
            DrawerScaffold(navController) {
                GalleryPermissionsScrean()
            }
        }

        composable("home"){
            DrawerScaffold(navController) {
                HomeScreen(navController)
            }
        }

        composable("favorites"){
            DrawerScaffold(navController) {
                FavoritesScreen(navController)
            }
        }
    }
}