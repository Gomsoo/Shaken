package com.gomsoo.shaken.feature.video

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gomsoo.shaken.core.extension.secondsToDurationText
import com.gomsoo.shaken.core.ui.YouTubePlayer

private const val YOUTUBE_VIDEO_ID = "Dd1N9NrPt3A"

@Composable
fun VideoRoute(
    modifier: Modifier = Modifier,
    viewModel: VideoViewModel = hiltViewModel()
) {
    val videoUiState by viewModel.videoUiState.collectAsStateWithLifecycle()
    VideoScreen(
        videoUiState = videoUiState,
        onPlayerStateChanged = viewModel::setPlayerState,
        modifier = modifier
    )
}

@Composable
internal fun VideoScreen(
    videoUiState: VideoUiState,
    onPlayerStateChanged: (Int, Float, Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var isPlayerReady by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            YouTubePlayer(
                videoId = YOUTUBE_VIDEO_ID,
                onReady = { isPlayerReady = true },
                onChanged = { state, d, current -> onPlayerStateChanged(state, d, current) }
            )
            Spacer(modifier = modifier.height(20.dp))

            if (videoUiState is VideoUiState.Success) {
                Script(videoUiState.script)
            }
        }

        if (videoUiState is VideoUiState.Initial || !isPlayerReady) {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun Script(script: List<Script>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        script.forEach {
            when (it) {
                is Script.Speaker -> Speaker(it)
                is Script.TextParagraph -> TextParagraph(it)
            }
        }
    }
}

@Composable
private fun Speaker(speaker: Script.Speaker) {
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        "${speaker.name} ${speaker.startTimeAsSeconds.secondsToDurationText()}",
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun TextParagraph(textParagraph: Script.TextParagraph) {
    Text(
        buildAnnotatedString {
            textParagraph.paragraph.forEach { scriptText ->
                val background = if (scriptText.isHighlighted) {
                    Color(0xFFAABBEE)
                } else {
                    Color.Transparent
                }
                withStyle(style = SpanStyle(background = background)) {
                    append(scriptText.text)
                }
            }
        },
        style = MaterialTheme.typography.bodyMedium
    )
}
