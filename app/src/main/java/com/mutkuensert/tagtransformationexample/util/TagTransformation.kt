package com.mutkuensert.tagtransformationexample.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

private val defaultTagStyle = SpanStyle(
    fontFamily = FontFamily.Default,
    fontSize = 16.sp,
    fontWeight = FontWeight(700),
)

class TagTransformation(
    private val prefixAndStyle: Map<String, SpanStyle> = mapOf(
        "@" to defaultTagStyle,
        "#" to defaultTagStyle
    )
) : VisualTransformation {

    companion object {
        /**
         * Removes tags from the text that don't exist in given list
         */
        fun removeUnknownTags(
            text: String,
            prefix: Char,
            tags: List<String>
        ): String {
            var newText = text

            for (i in text.indices) {
                if (text[i] == ' ') continue

                val lastIndexOfCurrentWord = text
                    .substring(startIndex = i)
                    .indexOf(" ")
                    .run {
                        if (this == -1) {
                            text.lastIndex.coerceAtLeast(0)
                        } else {
                            this + i - 1
                        }
                    }

                val word = text.substring(i, lastIndexOfCurrentWord + 1)

                if (word.startsWith(prefix) && !tags.any { word.startsWith(it) }) {
                    newText = newText.removeRange(i..lastIndexOfCurrentWord)
                    break
                }
            }

            return newText
        }
    }

    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            text.applyStyleIfStartsWithPrefix(prefixAndStyle = prefixAndStyle),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return if (offset > text.length) text.length else offset
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return if (offset > text.length) text.length else offset
                }
            }
        )
    }

    private fun AnnotatedString.applyStyleIfStartsWithPrefix(
        prefixAndStyle: Map<String, SpanStyle>
    ): AnnotatedString {
        val words: List<String> = split(" ")
        val builder = AnnotatedString.Builder()

        for (word in words) {
            val style = prefixAndStyle.entries.find { word.startsWith(it.key) }?.value

            if (style != null) {
                builder.withStyle(style) {
                    append("$word ")
                }
            } else {
                builder.append("$word ")
            }
        }
        return builder.toAnnotatedString()
    }
}