package com.saidatmaca.presentation.util

sealed class Screen(val route : String){

    object ListScreen : Screen("list_Screen")

    object DetailScreen : Screen("detail_screen")
}
