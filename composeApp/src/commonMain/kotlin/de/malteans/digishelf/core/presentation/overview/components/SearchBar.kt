package de.malteans.digishelf.core.presentation.overview.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = stringResource(Res.string.search),
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = { Text(text = hint) },
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = stringResource(Res.string.search)
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = value.isNotBlank(),
                enter = expandIn(expandFrom = Alignment.Center),
                exit = shrinkOut(shrinkTowards = Alignment.Center),
            ) {
                IconButton(
                    onClick = { onValueChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Search,
        )
    )
}