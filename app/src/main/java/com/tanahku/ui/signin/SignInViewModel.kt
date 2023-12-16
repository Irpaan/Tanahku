package com.tanahku.ui.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tanahku.data.SignIn
import com.tanahku.data.SignInResponse
import com.tanahku.data.SignInResult
import com.tanahku.data.UserPreference
import com.tanahku.network.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel (private val preference: UserPreference) : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    val error = MutableLiveData("")
    val message = MutableLiveData("")
    private val TAG = SignInViewModel::class.simpleName
    val loginResult = MutableLiveData<SignInResponse>()

    fun getUser(): LiveData<SignInResult> {
        return preference.getUser().asLiveData()
    }
    fun saveUser(userName: String, userId: String, userToken: String){
        viewModelScope.launch { preference.saveUser(userName, userId, userToken) }
    }
    fun SignOut(){
        viewModelScope.launch { preference.signout() }
    }
    fun signIn(signIn: SignIn){
        _isLoading.value = true
        val client = ApiConfig.getApiService().doLogin(signIn)
        client.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                when (response.code()) {
                    201 -> { loginResult.postValue(response.body())
                        message.postValue("201")
                    }
                    401 -> error.postValue("401")
                    404 -> error.postValue("404")
                    else -> error.postValue("ERROR ${response.code()} : ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure ${t.message}")
                error.postValue(t.message)
            }
        })
    }
}