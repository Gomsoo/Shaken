package com.gomsoo.shaken.feature.video

import com.gomsoo.shaken.core.asset.model.Karaoke
import com.gomsoo.shaken.core.asset.model.LexicalData
import com.gomsoo.shaken.core.asset.model.SpeakerBlock
import com.gomsoo.shaken.core.data.repository.VideoRepository
import com.gomsoo.shaken.core.extension.combine
import com.gomsoo.shaken.core.extension.indexedWith
import com.gomsoo.shaken.core.network.Dispatcher
import com.gomsoo.shaken.core.network.ShakenDispatchers
import com.gomsoo.shaken.core.ui.BaseViewModel
import com.gomsoo.shaken.core.ui.YouTubePlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val repository: VideoRepository,
    @Dispatcher(ShakenDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val decoded: StateFlow<String> =
        flow { emit(repository.decodeAssetBase64("test_data_base64.txt")) }
            .stateIn("")

    private val scriptText: StateFlow<String> =
        flow { emit(repository.getTextFromAsset("test_data_lexical.txt")) }
            .stateIn("")

    private val lexicalData: StateFlow<LexicalData?> =
        flow { emit(repository.getLexicalDataFromAsset("test_data_lexical.txt")) }
            .stateIn(null)

    /**
     * Lexical 데이터를 뷰 레이어에서 표현할 모델들로 컨버트.
     * 이 과정은 한 번만 수행하면 되므로 재생 시간에 따라 하이라이트할 부분을 체크하는 로직과 별도의 단계로 구분합니다.
     *
     * 인접한 Text들끼리 TextParagraph라는 하나의 객체로 묶어내어
     * 기존 [Speaker, Text, Text, Text, ..., Speaker, Text, Text, ...] 형태의 데이터를
     * [Speaker, TextParagraph, Speaker, TextParagraph, ...] 와 같은 형태로 변형합니다.
     */
    private val script: StateFlow<List<Script>> = lexicalData.filterNotNull()
        .mapLatest { lexical ->
            lexical.editorState.root.paragraphs
                .map { paragraph ->
                    // 인접한 Text(Karaoke)들끼리 TextParagraph 하나의 객체로 묶어냅니다.
                    paragraph.elements.asSequence()
                        .withIndex()
                        .groupBy { it.value is Karaoke }
                        .flatMap { (isKaraoke, value) ->
                            if (isKaraoke) {
                                // 원본 인덱스에서 한 단계 그룹화된 인덱스를 빼면
                                // 인접한 원소들끼리는 같은 값이 되는 것을 이용
                                value.groupBy { it.index - value.indexOf(it) }
                                    .values
                                    .map { group ->
                                        val textParagraph = group.map { it.value }.toTextParagraph()
                                        group.first().index indexedWith textParagraph
                                    }
                            } else {
                                value.mapNotNull { (index, value) ->
                                    when (value) {
                                        is SpeakerBlock -> index indexedWith value.toSpeaker()
                                        else -> null
                                    }
                                }
                            }
                        }
                        .sortedBy { it.index }
                        .map { it.value }
                }
                .flatten()
        }
        .flowOn(ioDispatcher)
        .stateIn(emptyList())

    data class PlayerState(val state: Int, val duration: Float, val current: Float) {
        companion object {
            fun createInitial() = PlayerState(YouTubePlayerState.BEFORE_START, 0f, 0f)
        }
    }

    private val playerState = MutableStateFlow(PlayerState.createInitial())

    fun setPlayerState(state: Int, duration: Float, current: Float) {
        playerState.update {
            it.copy(state = state, duration = duration, current = current)
        }
    }

    /**
     * 재생 시간에 따라 하이라이트할 부분을 체크
     */
    private val highlightedScript: StateFlow<List<Script>> = script.combine(playerState)
        .mapLatest { (script, state) ->
            script.map {
                if (it is Script.TextParagraph) {
                    it.copy(
                        paragraph = it.paragraph.map { text ->
                            text.copy(isHighlighted = state.current in text.start..text.end)
                        }
                    )
                } else {
                    it
                }
            }
        }
        .flowOn(ioDispatcher)
        .stateIn(emptyList())

    val videoUiState: StateFlow<VideoUiState> = decoded.combine(scriptText, highlightedScript)
        .mapLatest { (decoded, scriptText, script) ->
            VideoUiState.Success(decoded, scriptText, script)
        }
        .stateIn(VideoUiState.Initial)
}
