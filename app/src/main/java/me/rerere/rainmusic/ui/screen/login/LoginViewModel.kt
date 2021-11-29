package me.rerere.rainmusic.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.rerere.rainmusic.repo.UserRepo
import me.rerere.rainmusic.util.DataState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {
    /**
     * 登录状态
     *
     * -1: 异常
     * 0: idle
     * 1: 登录中
     * 2: 密码错误
     * 3: 账号不存在
     *
     * 1000: 登录成功
     */
    val loginState = MutableStateFlow(0)

    fun loginCellPhone(
        phone: String,
        password: String
    ) {
        userRepo.loginCellPhone(
            phone,
            password
        ).onEach {
            loginState.value = when (it) {
                is DataState.Success -> when (it.data.code) {
                    200 -> 1000
                    502 -> 2
                    501 -> 3
                    else -> -1
                }
                is DataState.Loading -> 1
                is DataState.Error -> -1
                is DataState.Empty -> 0
            }
        }.launchIn(viewModelScope)
    }
}