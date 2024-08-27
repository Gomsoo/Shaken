package com.gomsoo.shaken.core.ui

import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

private class YouTubePlayerJavascriptInterface(
    private val onStateChanged: (Int) -> Unit,
    private val onDurationChanged: (Float) -> Unit,
    private val onCurrentTimeChanged: (Float) -> Unit
) {
    @JavascriptInterface
    fun onStateChanged(state: Int) {
        onStateChanged.invoke(state)
    }

    @JavascriptInterface
    fun onDurationChanged(duration: Float) {
        onDurationChanged.invoke(duration)
    }

    @JavascriptInterface
    fun onCurrentTimeChanged(currentTime: Float) {
        onCurrentTimeChanged.invoke(currentTime)
    }
}

/**
 * [YouTube IFrame Player API -> Events](https://developers.google.com/youtube/iframe_api_reference#Events)
 */
object YouTubePlayerState {
    /**
     * 시작되지 않음
     */
    const val BEFORE_START = -1

    /**
     * 종료됨
     */
    const val ENDED = 0

    /**
     * 재생 중
     */
    const val PLAYING = 1

    /**
     * 일시중지됨
     */
    const val PAUSED = 2

    /**
     * 버퍼링 중
     */
    const val BUFFERING = 3

    /**
     * 동영상 신호
     */
    const val CUED = 5
}

/**
 * [YouTube IFrame Player API](https://developers.google.com/youtube/iframe_api_reference#Getting_Started)
 */
@Composable
fun YouTubePlayer(videoId: String, onChanged: (Int, Float, Float) -> Unit) {
    var playerState by remember { mutableIntStateOf(-1) }
    var duration by remember { mutableFloatStateOf(0f) }
    var currentTime by remember { mutableFloatStateOf(0f) }

    val htmlContent = remember {
        """
        <!DOCTYPE html>
        <html>
        <body>
            <div id="player"></div>
            <script src="https://www.youtube.com/iframe_api"></script>
            <script>
                var player;
                function onYouTubeIframeAPIReady() {
                    player = new YT.Player('player', {
                        height: '100%',
                        width: '100%',
                        videoId: '$videoId',
                        events: {
                            'onReady': onPlayerReady,
                            'onStateChange': onPlayerStateChange
                        }
                    });
                }
                function onPlayerReady(event) {
                    setInterval(updatePlayerInfo, 1000);
                }
                function onPlayerStateChange(event) {
                    AndroidInterface.onStateChanged(event.data.toString());
                }
                function updatePlayerInfo() {
                    var currentTime = player.getCurrentTime();
                    var duration = player.getDuration();
                    AndroidInterface.onCurrentTimeChanged(currentTime);
                    AndroidInterface.onDurationChanged(duration);
                }
            </script>
        </body>
        </html>
        """.trimIndent()
    }

    Column {
        // YouTube Player
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.mediaPlaybackRequiresUserGesture = false
                    addJavascriptInterface(YouTubePlayerJavascriptInterface(
                        onStateChanged = {
                            playerState = it
                            onChanged(it, duration, currentTime)
                        },
                        onDurationChanged = {
                            duration = it
                            onChanged(playerState, it, currentTime)
                        },
                        onCurrentTimeChanged = {
                            currentTime = it
                            onChanged(playerState, duration, it)
                        }
                    ), "AndroidInterface")
                    webViewClient = WebViewClient()
                    loadDataWithBaseURL(
                        "https://www.youtube.com",
                        htmlContent,
                        "text/html",
                        "UTF-8",
                        null
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }
}
