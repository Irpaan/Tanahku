package com.tanahku.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tanahku.R
import com.tanahku.data.UserPreference
import com.tanahku.data.dataStore
import com.tanahku.databinding.ActivityMainBinding
import com.tanahku.ui.ViewModelFactory
import com.tanahku.ui.kamera.KameraActivity
import com.tanahku.ui.signin.SignInActivity
import com.tanahku.ui.signin.SignInViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var signInViewModel: SignInViewModel
    private lateinit var bottomNavationView: BottomNavigationView
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
            }else{
                bottomNavationView = findViewById(R.id.bottomNavigationView)

                replaceFragment(Home())

                binding.bottomNavigationView.setOnItemSelectedListener {

                    when(it.itemId){

                        R.id.navigation_home -> replaceFragment(Home())
                        R.id.navigation_profile -> replaceFragment(Profile())
                        R.id.navigation_signout -> {
                            signInViewModel.SignOut()
                        }

                        else ->{
                        }
                    }
                    true
                }
                replaceFragment(Home())



                binding.fab.setOnClickListener{
                    intent = Intent(this@MainActivity, KameraActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}