package com.mutkuensert.tagtransformationexample.util

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getTextAfterSelection
import androidx.compose.ui.text.input.getTextBeforeSelection

fun TextFieldValue.insertText(text: String): TextFieldValue {
    val maxChars = this.text.length
    val textBeforeSelection = getTextBeforeSelection(maxChars).text
    val textAfterSelection = getTextAfterSelection(maxChars).text
    val textBeforeInsertedText =
        if (textBeforeSelection.isEmpty() || textBeforeSelection.lastOrNull() == ' ') {
            ""
        } else {
            " "
        }
    val textAfterInsertedText =
        if (textBeforeSelection.isEmpty() || textAfterSelection.lastOrNull() == ' ') {
            ""
        } else {
            " "
        }
    val insertionLength = (textBeforeInsertedText + text + textAfterInsertedText).length

    val newText = textBeforeSelection + textBeforeInsertedText + text +
            textAfterInsertedText + textAfterSelection

    return TextFieldValue(
        text = newText,
        selection = TextRange(selection.end + insertionLength),
        composition = composition
    )
}