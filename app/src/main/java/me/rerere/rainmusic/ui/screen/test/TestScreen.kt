package me.rerere.rainmusic.ui.screen.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soywiz.krypto.AES
import com.soywiz.krypto.Padding
import com.soywiz.krypto.encoding.unhex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.rerere.rainmusic.retrofit.api.NeteaseMusicApi
import me.rerere.rainmusic.retrofit.eapi.NeteaseMusicEApi
import me.rerere.rainmusic.retrofit.weapi.NeteaseMusicWeApi
import me.rerere.rainmusic.ui.component.PopBackIcon
import me.rerere.rainmusic.ui.component.RainTopBar
import me.rerere.rainmusic.ui.local.LocalUserData
import me.rerere.rainmusic.util.encrypt.encryptEApi
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
                },
                navigationIcon = {
                    PopBackIcon()
                }
            )
        }
    ) {
        val user = LocalUserData.current
        Column(Modifier.fillMaxSize()) {
            var content by remember {
                mutableStateOf("")
            }
            TextField(value = content, onValueChange = {content = it})
            Button(onClick = {
                val decrypt = AES.decryptAesEcb(
                    data = content.unhex,
                    key = "e82ckenh8dichen8".toByteArray(),
                    padding = Padding.PKCS7Padding
                )
                println("解密结果: ${String(decrypt)}")
            }) {
                Text(text = "解密EAPI")
            }
        }
    }
}

@HiltViewModel
class TestViewModel @Inject constructor(
    private val weApi: NeteaseMusicWeApi,
    private val api: NeteaseMusicApi,
    private val eApi: NeteaseMusicEApi
) : ViewModel() {
    fun test(id: Long){
        viewModelScope.launch {
            eApi.count(
                encryptEApi(
                    url = "/api/pl/count",
                    data = mapOf(

                    )
                )
            ).let {
                println(it)
            }
        }
    }
}