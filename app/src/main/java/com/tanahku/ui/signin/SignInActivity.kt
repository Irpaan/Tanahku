package com.tanahku.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.tanahku.data.SignIn
import com.tanahku.data.UserPreference
import com.tanahku.data.dataStore
import com.tanahku.databinding.ActivitySignInBinding
import com.tanahku.ui.ViewModelFactory
import com.tanahku.ui.detailtanah.DetailTanahActivity
import com.tanahku.ui.signup.SignUpActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignInBinding
    private lateinit var signInViewModel: SignInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupAction()

        binding.signup.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun setupViewModel(){
        signInViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[SignInViewModel::class.java]
        signInViewModel?.let { signInViewModel -> signInViewModel.loginResult.observe(this){
                login->
            signInViewModel.saveUser(
                login.loginResult.name,
                login.loginResult.userId,
                login.loginResult.token,
            )
        }
            signInViewModel.message.observe(this){
                    message->
                if (message == "201"){
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Informasi")
                    builder.setMessage("Login Berhasil!")
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                        val intent = Intent(this, DetailTanahActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, 3000)
                }
                signInViewModel.error.observe(this){
                        error->
                    if (error == "401"){
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Informasi")
                        builder.setMessage("Password Salah!")
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            alertDialog.dismiss()
                        }, 3000)
                    }
                    if (error == "404"){
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Informasi")
                        builder.setMessage("User Tidak Ditemukan!")
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            alertDialog.dismiss()
                        }, 3000)
                    }
                }
                signInViewModel.isLoading.observe(this){loading(it)}
            }
        }
    }

    private fun setupAction() {
        binding.signinButton.setOnClickListener {
            val email = binding.signinEmail.text.toString()
            val password = binding.signinPassword.text.toString()
            when{
                email.isEmpty()->{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Informasi")
                    builder.setMessage("Tolong Isi Email Anda!")
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                    }, 3000)
                }
                password.isEmpty()->{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Informasi")
                    builder.setMessage("Tolong Isi Password Anda!")
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                    }, 3000)
                }
                else->{
                    signInViewModel.signIn(signIn = SignIn(email, password))
                }
            }
        }
    }

    private fun loading(isLoading: Boolean) { binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE }
}