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

    private val _tags =
        MutableStateFlow(listOf("@siriusblack", "@harrypotter", "#azkaban", "@ronweasley", "@dobby"))
    val tags = _tags.asStateFlow()


    fun onMessageChange(value: TextFieldValue) {
        _message.value = value.copy(
            text = TagTransformation.removeUnknownTags(
                value.text,
                tags.value
            )
        )
    }

    fun insertText(text: String) {
        onMessageChange(message.value.insertText(text))
    }
}
