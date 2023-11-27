package com.tanahku.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tanahku.data.UserPreference
import com.tanahku.data.dataStore
import com.tanahku.databinding.ActivityMainBinding
import com.tanahku.ui.ViewModelFactory
import com.tanahku.ui.signin.SignInActivity
import com.tanahku.ui.signin.SignInViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var signInViewModel: SignInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signInViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SignInViewModel::class.java]

        signInViewModel.getUser().observe(this) { user ->
            if (user.token.isEmpty()) {
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
        }
    }
}