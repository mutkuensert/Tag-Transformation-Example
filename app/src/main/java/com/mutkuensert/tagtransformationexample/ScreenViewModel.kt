package com.mutkuensert.tagtransformationexample

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.mutkuensert.tagtransformationexample.util.TagTransformation
import com.mutkuensert.tagtransformationexample.util.insertText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScreenViewModel : ViewModel() {
    private val _message = MutableStateFlow(TextFieldValue(""))
    val message = _message.asStateFlow()

    private val _peopleTags =
        MutableStateFlow(
            listOf(
                "@siriusblack",
                "@harrypotter",
                "@ronweasley",
                "@dobby"
            )
        )
    val peopleTags = _peopleTags.asStateFlow()

    private val _hashTags =
        MutableStateFlow(listOf("#azkaban", "#hogwartz"))
    val hashTags = _hashTags.asStateFlow()


    fun onMessageChange(value: TextFieldValue) {
        var newValue = value

        newValue = newValue.copy(
            text = TagTransformation.removeUnknownTags(
                text = newValue.text,
                prefix = '@',
                tags = peopleTags.value
            )
        )

        newValue = newValue.copy(
            text = TagTransformation.removeUnknownTags(
                text = newValue.text,
                prefix = '#',
                tags = hashTags.value
            )
        )

        _message.value = newValue
    }

    fun insertText(text: String) {
        onMessageChange(message.value.insertText(text))
    }
}
