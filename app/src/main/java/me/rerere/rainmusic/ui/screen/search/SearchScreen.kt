package me.rerere.rainmusic.ui.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.rerere.rainmusic.ui.component.PopBackIcon
import me.rerere.rainmusic.ui.component.RainTopBar

@ExperimentalMaterial3Api
@Composable
fun SearchScreen() {
    Scaffold(
        topBar = {
            RainTopBar(
                navigationIcon = {
                    PopBackIcon()
                },
                title = {
                    Text(text = "搜索")
                }
            )
        }
    ) {
        Body()
    }
}

@Composable
private fun Body() {
    Column {
        var query by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            value = query,
            onValueChange = {
                query = it
            },
            placeholder = {
                 Text(text = "尝试搜索一下吧 (●'◡'●)")
            },
            trailingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Rounded.Search, null)
                }
            }
        )
    }
}