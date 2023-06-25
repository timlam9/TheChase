package com.lamti.thechase.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextBox(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = "Enter a name",
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChange,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
    )
}