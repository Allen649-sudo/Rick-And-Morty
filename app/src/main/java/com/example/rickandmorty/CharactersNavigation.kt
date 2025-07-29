package com.example.rickandmorty

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.rickandmorty.screens.CharacterCardList
import com.example.rickandmorty.screens.CharacterDetailScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import androidx.hilt.navigation.compose.hiltViewModel

sealed class Screen(val route: String) {
    object CharacterCardList : Screen("CharacterCardList")
    object CharacterDetailScreen: Screen("CharacterDetailScreen")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CharactersNavigation(navController: NavHostController) {

//    val context = LocalContext.current
//    val app = context.applicationContext as App
//    val database = app.database
//    val dao = database.characterDao()

//    val viewModel: CharactersViewModel = viewModel(factory = CharactersViewModelFactory(repository))
    val viewModel: CharactersViewModel = hiltViewModel()

        AnimatedNavHost(
            navController = navController,
            startDestination = Screen.CharacterCardList.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() }
        ) {
            composable(Screen.CharacterCardList.route) {
                CharacterCardList(
                    viewModel = viewModel,
                    onShowDetailInfo = { characterId ->
                        viewModel.showDetailedInfo(characterId)
                        navController.navigate(Screen.CharacterDetailScreen.route)
                    }
                )
            }

            composable(Screen.CharacterDetailScreen.route) {
                CharacterDetailScreen(
                    viewModel,
                    onBackListCharacter = {
                        navController.navigate(Screen.CharacterCardList.route)
                    }
                )
            }
        }
}