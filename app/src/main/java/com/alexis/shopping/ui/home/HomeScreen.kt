package com.alexis.shopping.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.alexis.shopping.R
import com.alexis.shopping.ui.core.ShowCircularIndicator
import com.alexis.shopping.ui.core.ShowSpacer
import com.alexis.shopping.ui.core.UiState
import com.alexis.shopping.ui.pokemon.EmptyListContent
import com.alexis.shopping.ui.pokemon.ItemPokemon
import com.alexis.shopping.ui.route.Route
import com.alexis.shopping.ui.search.SearchScreen

@Composable
fun HomeScreen(
    modifier: Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val query by homeViewModel.searchQuery.collectAsStateWithLifecycle()
    val pokemons = homeViewModel.pokemons.collectAsLazyPagingItems()

    when (val state = homeViewModel.state.collectAsStateWithLifecycle().value) {
        is UiState.Loading -> {
            ShowCircularIndicator()
        }

        is UiState.Success -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Column {
                    SearchScreen(
                        query = query,
                        onQueryChange = {
                            homeViewModel.searchPokemon(it)
                        }
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(
                            count = pokemons.itemCount,
                            key = pokemons.itemKey { it.id }
                        ) { index ->
                            val item = pokemons[index]
                            if (item != null) {
                                ShowSpacer(10)
                                ItemPokemon(
                                    pokemon = item,
                                    imageVector = Icons.Default.AddShoppingCart,
                                    contentDescription = R.string.cart_icon,
                                    onItemSelected = { pokemon ->
                                        homeViewModel.addToCart(pokemon)
                                    }
                                )
                            }
                        }
                        val isListEmpty = pokemons.itemCount == 0

                        if (isListEmpty) {
                            item {
                                EmptyListContent()
                            }
                        }
                    }
                }
            }
        }

        is UiState.Failure -> {
            navController.navigate(
                Route.Failure.createRoute(
                    state.exception.message ?: stringResource(id = R.string.exception)
                )
            )
        }
    }
}