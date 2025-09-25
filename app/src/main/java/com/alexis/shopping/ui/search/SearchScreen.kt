package com.alexis.shopping.ui.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexis.shopping.R

@Composable
fun SearchScreen(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Search(
        text = query,
        onValueChange = {
            onQueryChange(it)
        }
    )
}

@Composable
fun Search(text: String, onValueChange: (String) -> Unit) {
    TextField(
        placeholder = {
            Text(stringResource(id = R.string.search_placeholder))
        },
        value = text,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFCCCCCC),
                shape = RoundedCornerShape(25.dp)
            ),
        shape = RoundedCornerShape(25.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_icon)
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                Icon(
                    modifier = Modifier.clickable {
                        onValueChange("")
                    },
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(id = R.string.clear)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}