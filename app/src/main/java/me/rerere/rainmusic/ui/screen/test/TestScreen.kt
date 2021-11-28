package me.rerere.rainmusic.ui.screen.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.rerere.rainmusic.retrofit.api.NeteaseMusicApi
import me.rerere.rainmusic.retrofit.weapi.NeteaseMusicWeApi
import me.rerere.rainmusic.ui.component.RainTopBar
import me.rerere.rainmusic.ui.local.LocalUserData
import me.rerere.rainmusic.util.encrypt.encryptWeAPI
import javax.inject.Inject

@ExperimentalMaterial3Api
@Composable
fun TestScreen(testViewModel: TestViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            RainTopBar(
                title = {
                    Text(text = "测试页面")
                }
            )
        }
    ) {
        val user = LocalUserData.current
        Column(Modifier.fillMaxSize()) {
            Button(onClick = {
                testViewModel.test(user.id)
            }) {
                Text(text = "发起请求")
            }
        }
    }
}

@HiltViewModel
class TestViewModel @Inject constructor(
    private val weApi: NeteaseMusicWeApi,
    private val api: NeteaseMusicApi
) : ViewModel() {
    fun test(id: Long){
        viewModelScope.launch {
            api.getUserPlaylist(
                mapOf(
                    "uid" to id.toString(),
                    "limit" to "1000",
                    "offset" to "0",
                    "includeVideo" to "false"
                )
            ).let {
                println(it)
            }
        }
    }
}