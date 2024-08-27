package com.gomsoo.shaken.feature.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gomsoo.shaken.core.designsystem.theme.ShakenTheme
import com.gomsoo.shaken.core.ui.YouTubePlayer

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
    var playerState by remember { mutableIntStateOf(-1) }
    var duration by remember { mutableFloatStateOf(0f) }
    var currentTime by remember { mutableFloatStateOf(0f) }
    Column(modifier = modifier.fillMaxSize()) {
        YouTubePlayer(videoId = "Dd1N9NrPt3A") { state, d, current ->
            playerState = state
            duration = d
            currentTime = current
            onPlayerStateChanged(state, d, current)
        }
        Text("Player State: $playerState")
        Text("Current Time: ${currentTime.toInt()} seconds")
        Text("Duration: ${duration.toInt()} seconds")
//        when (detailUiState) {
//            is VideoUiState.Initial -> InitialState(modifier)
//            is VideoUiState.InvalidId -> EmptyState(modifier)
//            is VideoUiState.InvalidResult -> EmptyState(modifier)
//            is VideoUiState.Success -> CocktailDetail(
//                detailUiState.cocktail,
//                onFavoriteClick,
//                modifier
//            )
//        }
        Spacer(modifier = modifier.height(20.dp))
//        Text((videoUiState as? VideoUiState.Success)?.decoded ?: "")
//        Text((videoUiState as? VideoUiState.Success)?.scriptText ?: "")
//
//        Spacer(modifier = modifier.height(20.dp))
//        ComplexStyledText()

        if (videoUiState is VideoUiState.Success) {
            Script(videoUiState.script)
        }
    }
}

@Composable
fun ComplexStyledText() {
    val annotatedString = buildAnnotatedString {
        append("This is ")
        withStyle(
            style = SpanStyle(
                background = Color.Yellow,
                color = Color.Red,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("highlighted")
        }
        append(" and ")
        withStyle(
            style = SpanStyle(
                background = Color.Cyan,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("styled")
        }
        append(" text")
    }

    Text(text = annotatedString)
}

/**
 * TODO
 */
@Composable
private fun InitialState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "오쉣")
        Text(text = "시간 없어라")
    }
}

/**
 * TODO
 */
@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "OMG")
        Text(text = "The time is running out")
    }
}

@Composable
private fun Header(text: String, isTopPaddingVisible: Boolean = true) {
    if (isTopPaddingVisible) {
        Spacer(modifier = Modifier.height(16.dp))
    }
    Text(text, style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(4.dp))
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
                is Script.Speaker -> Text("${it.name} ${it.startTimeAsSeconds}")
                is Script.TextParagraph -> {
                    Text(buildAnnotatedString {
                        it.paragraph.forEach { scriptText ->
                            val background =
                                if (scriptText.isHighlighted) Color.Cyan else Color.Transparent
                            withStyle(style = SpanStyle(background = background)) {
                                append(scriptText.text)
                            }
                        }
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InitialStatePreview() {
    ShakenTheme {
        InitialState()
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyStatePreview() {
    ShakenTheme {
        EmptyState()
    }
}

@Preview(showBackground = true)
@Composable
private fun CocktailsPreview() {
    ShakenTheme {
    }
}
