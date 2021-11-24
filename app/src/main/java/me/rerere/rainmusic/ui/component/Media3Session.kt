package me.rerere.rainmusic.ui.component

import android.content.ComponentName
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors

@Composable
fun rememberMediaSessionPlayer(clazz: Class<out Any>): State<MediaController?> {
    val context = LocalContext.current
    val builder = remember(context) {
        MediaController.Builder(
            context,
            SessionToken(
                context,
                ComponentName(
                    context,
                    clazz
                )
            )
        ).buildAsync()
    }
    val controller = remember(context) {
        mutableStateOf<MediaController?>(null)
    }
    DisposableEffect(context) {
        builder.addListener({
            controller.value = builder.get()
        }, MoreExecutors.directExecutor())

        onDispose {
            MediaController.releaseFuture(builder)
        }
    }
    return controller
}