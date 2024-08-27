package com.gomsoo.shaken.feature.video

import com.gomsoo.shaken.core.asset.model.Karaoke
import com.gomsoo.shaken.core.asset.model.ParagraphElement
import com.gomsoo.shaken.core.asset.model.SpeakerBlock

sealed interface VideoUiState {

    data object Initial : VideoUiState

    data class Success(
        val decoded: String,
        val scriptText: String,
        val script: List<Script>
    ) : VideoUiState
}

/**
 * 뷰 레이어에서 표현할 정보를 모델링.
 */
sealed interface Script {

    data class Text(val text: String, val isHighlighted: Boolean, val start: Float, val end: Float)

    data class TextParagraph(val paragraph: List<Text>) : Script

    data class Speaker(val name: String, val startTimeAsSeconds: Float) : Script
}

internal fun Karaoke.toScriptText(currentTime: Float): Script.Text = Script.Text(
    text = text,
    isHighlighted = currentTime in startTime..endTime,
    start = startTime,
    end = endTime
)

internal fun Karaoke.toScriptText(): Script.Text = Script.Text(
    text = text,
    isHighlighted = false,
    start = startTime,
    end = endTime
)

/**
 * Lexical 데이터 중 Karaoke만 존재하는 리스트를 TextParagraph 객체로 만들어 줍니다.
 *
 * 이 과정에서, Lexical 데이터에서는 단어 단위로 짧은 시간 간격으로 구분되어 있어, 문장을 기준으로 텍스트를 합치도록 합니다.
 */
fun Collection<ParagraphElement>.toTextParagraph(): Script.TextParagraph = Script.TextParagraph(
    filterIsInstance<Karaoke>()
        .map { it.toScriptText() }
        .fold(emptyList<List<Script.Text>>()) { acc, text ->
            val last = acc.lastOrNull() ?: return@fold listOf(listOf(text))
            val lastOfLast = last.last()
            if (lastOfLast.text.trimEnd().last() in "?!,.") {
                acc + listOf(listOf(text))
            } else {
                acc.subList(0, acc.lastIndex) + listOf(last + text)
            }
        }
        .map { list ->
            Script.Text(
                text = list.joinToString("") { it.text },
                isHighlighted = false,
                start = list.first().start,
                end = list.last().end
            )
        }
)

fun SpeakerBlock.toSpeaker(): Script.Speaker = Script.Speaker(speaker, time)
