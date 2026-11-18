package com.example.iraunkortasuna.ui.pantailak

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


//nav controller
@Composable
fun AppAplication(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "lPantaila"
    ){
        composable("lpantaila") {
            LPantaila(navController)
        }
    }
}
