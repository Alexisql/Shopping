package com.alexis.shopping.ui.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alexis.shopping.R
import com.alexis.shopping.ui.core.ShowCircularIndicator
import com.alexis.shopping.ui.core.ShowSpacer
import com.alexis.shopping.ui.core.UiState
import com.alexis.shopping.ui.pokemon.EmptyListContent
import com.alexis.shopping.ui.pokemon.ItemPokemon
import com.alexis.shopping.ui.route.Route

@Composable
fun CartScreen(
    modifier: Modifier,
    cartViewModel: CartViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    when (val state = cartViewModel.state.collectAsStateWithLifecycle().value) {
        is UiState.Loading -> {
            ShowCircularIndicator()
        }

        is UiState.Success -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                val pokemons = state.data
                Column {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(
                            pokemons,
                        ) { pokemon ->
                            ShowSpacer(10)
                            ItemPokemon(
                                pokemon = pokemon,
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = R.string.delete_icon,
                                onItemSelected = { pokemon ->
                                    cartViewModel.deletePokemonCart(pokemon.id)
                                }
                            )
                        }
                        if (pokemons.isEmpty()) {
                            item {
                                EmptyListContent()
                            }
                        }
                    }
                }
                if (pokemons.isNotEmpty()) {
                    FloatingActionButton(
                        onClick = { cartViewModel.sendNotification() },
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Payment,
                            contentDescription = stringResource(R.string.payment_icon)
                        )
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