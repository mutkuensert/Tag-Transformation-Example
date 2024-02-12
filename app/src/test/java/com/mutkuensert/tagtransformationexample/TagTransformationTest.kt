package com.mutkuensert.tagtransformationexample

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.common.truth.Truth.assertThat
import com.mutkuensert.tagtransformationexample.util.TagTransformation
import org.junit.Test

class TagTransformationTest {

    @Test
    fun `given tags list, then should return same if user adds any suffix`() {
        val tags = listOf("@harrypotter", "@siriusblack")
        val text = "@siriusblack is @harrypotter's godfather."

        assertThat(TagTransformation.removeUnknownTags(text = text, prefix = '@', tags = tags))
            .isEqualTo("@siriusblack is @harrypotter's godfather.")
    }

    @Test
    fun `given tags list, if user tries to change a tag, should remove the tag`() {
        val tags = listOf("@harrypotter", "@siriusblack")
        val text = "@siriusblack is @harryypotter godfather." // User adds a second y to harry

        assertThat(TagTransformation.removeUnknownTags(text = text, prefix = '@', tags = tags))
            .isEqualTo("@siriusblack is  godfather.")
    }

    @Test
    fun `given tags list, then should remove tags that doesn't exist in the list from the text`() {
        val tags = listOf("@harrypotter", "@siriusblack")
        val text = "@siriusblack is @harrypotters godfather. @dumbledore is @harrypotter's teacher."

        assertThat(TagTransformation.removeUnknownTags(text = text, prefix = '@', tags = tags))
            .isEqualTo(
                "@siriusblack is @harrypotters godfather.  is @harrypotter's teacher."
            )
    }

    @Test
    fun `given tag prefix and style, then should apply style to tags`() {
        val text = "@siriusblack is Harry Potter's godfather."

        val taggedTextStyle = SpanStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            fontWeight = FontWeight(700)
        )

        val prefixAndStyle = mapOf("@" to taggedTextStyle)
        val tagTransformation = TagTransformation(prefixAndStyle)
        val annotatedText = tagTransformation.filter(AnnotatedString(text)).text

        annotatedText.spanStyles.forEach {
            if (it.start == 0 && it.end == 12) {
                assertThat(it.item).isEqualTo(taggedTextStyle)
            }

            if (it.start >= 13) {
                assertThat(it.item).isNotEqualTo(taggedTextStyle)
            }
        }
    }
}