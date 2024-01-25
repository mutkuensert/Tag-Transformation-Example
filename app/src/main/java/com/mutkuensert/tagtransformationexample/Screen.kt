package com.mutkuensert.tagtransformationexample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mutkuensert.tagtransformationexample.util.TagTransformation

@Composable
fun Screen(viewModel: ScreenViewModel) {
    val message by viewModel.message.collectAsState()
    val peopleTags by viewModel.peopleTags.collectAsState()
    val hashTags by viewModel.hashTags.collectAsState()

    Column {
        LazyRow {
            items(peopleTags.size) { index ->
                TextButton(onClick = { viewModel.insertText(peopleTags[index]) }) {
                    Text(text = peopleTags[index])
                }
            }
        }

        LazyRow {
            items(hashTags.size) { index ->
                TextButton(onClick = { viewModel.insertText(hashTags[index]) }) {
                    Text(text = hashTags[index])
                }
            }
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            value = message,
            onValueChange = viewModel::onMessageChange,
            visualTransformation = TagTransformation()
        )
    }
}