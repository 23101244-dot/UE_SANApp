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


@Composable
fun AppNavGraph(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "register"){

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