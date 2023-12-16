package com.tanahku.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tanahku.data.SignUp
import com.tanahku.data.SignUpResponse
import com.tanahku.data.UserPreference
import com.tanahku.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel (private val preference: UserPreference) : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    val error = MutableLiveData("")
    val message = MutableLiveData("")
    private val TAG = SignUpViewModel::class.simpleName

    fun signup(signUp: SignUp) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().doSignup(signUp)
        client.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                when (response.code()) {
                    400 -> error.postValue("400")
                    401 -> error.postValue("401")
                    201 -> message.postValue("201")
                    else -> error.postValue("ERROR ${response.code()} : ${response.errorBody()}")
                }
                _isLoading.value = false
            }
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure Call: ${t.message}")
                error.postValue(t.message)
            }
        })
    }
}