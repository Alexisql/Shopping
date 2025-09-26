package com.alexis.shopping.ui.pokemon

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexis.shopping.R
import com.alexis.shopping.domain.model.Pokemon
import com.alexis.shopping.ui.core.ShowSpacer
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemPokemon(
    pokemon: Pokemon,
    imageVector: ImageVector,
    @StringRes contentDescription: Int,
    onItemSelected: (Pokemon) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                LoadImage(
                    modifier = Modifier.size(90.dp),
                    path = pokemon.url,
                    contentDescription = stringResource(id = R.string.pokemon),
                    roundedCorners = 10
                )
                ShowSpacer(10)
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = pokemon.name,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                    )
                    ShowSpacer(5)
                    Text(
                        text = "Price = ${pokemon.price}",
                        fontSize = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            FloatingActionButton(onClick = { onItemSelected(pokemon) }) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = stringResource(contentDescription)
                )
            }
        }
    }
}

@Composable
fun EmptyListContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.not_found),
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@ExperimentalGlideComposeApi
@Composable
fun LoadImage(
    modifier: Modifier,
    path: String,
    contentDescription: String,
    roundedCorners: Int = 1
) {
    GlideImage(
        modifier = modifier,
        model = path,
        contentDescription = contentDescription
    ) {
        it.error(R.drawable.image_not_found)
            .placeholder(R.drawable.placeholder)
            .transform(MultiTransformation(CenterInside(), RoundedCorners(roundedCorners)))
    }
}