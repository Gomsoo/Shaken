package com.gomsoo.shaken.core.asset.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class LexicalData(
    @SerialName("editorState") val editorState: EditorState,
    @SerialName("lastSaved") val lastSaved: Long, // 1723468736251
    @SerialName("source") val source: String, // Playground
    @SerialName("version") val version: String // 0.4.1
)

@Serializable
data class EditorState(
    @SerialName("root") val root: Root
)

@Serializable
data class Root(
    @SerialName("children") val paragraphs: List<Paragraph>,
    @SerialName("type") val type: String, // root
    @SerialName("version") val version: Int // 1
)

@Serializable
data class Paragraph(
    @SerialName("children") val elements: List<ParagraphElement>,
    @SerialName("type") val type: String // paragraph
)

@Serializable(with = ParagraphElementSerializer::class)
sealed interface ParagraphElement {
    val type: String

    object Type {
        const val SPEAKER_BLOCK = "speaker-block"
        const val KARAOKE = "karaoke"
    }
}

@Serializable
@SerialName("speaker-block")
data class SpeakerBlock(
    override val type: String,
    val speaker: String,
    val time: Float
) : ParagraphElement

@Serializable
@SerialName("karaoke")
data class Karaoke(
    override val type: String,
    val text: String,
    @SerialName("s") val startTime: Float,
    @SerialName("e") val endTime: Float
) : ParagraphElement

private object ParagraphElementSerializer : JsonContentPolymorphicSerializer<ParagraphElement>(
    ParagraphElement::class
) {
    override fun selectDeserializer(
        element: JsonElement
    ) = when (element.jsonObject["type"]?.jsonPrimitive?.content) {
        ParagraphElement.Type.SPEAKER_BLOCK -> SpeakerBlock.serializer()
        ParagraphElement.Type.KARAOKE -> Karaoke.serializer()
        else -> throw SerializationException("Unknown type")
    }
}
