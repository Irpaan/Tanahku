package com.tanahku.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.tanahku.data.SignUp
import com.tanahku.data.UserPreference
import com.tanahku.data.dataStore
import com.tanahku.databinding.ActivitySignUpBinding
import com.tanahku.ui.ViewModelFactory
import com.tanahku.ui.detailtanah.DetailTanahActivity
import com.tanahku.ui.signin.SignInActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupAction()

        binding.signin.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun setupViewModel(){
        signUpViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[SignUpViewModel::class.java]
        signUpViewModel?.let { signUpViewModel -> signUpViewModel.message.observe(this){
                message->
            if (message == "201"){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Informasi")
                builder.setMessage("User Berhasil Dibuat!")
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
            signUpViewModel.error.observe(this){
                    error->
                if (error == "400"){
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Informasi")
                    builder.setMessage("Email sudah terdaftar!")
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                    }, 3000)
                }
            }

        }
            signUpViewModel.isLoading.observe(this) {loading(it)}
        }
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.signupName.text.toString()
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val konfirmasi = binding.signupKonfirmasiPassword.text.toString()

            when{
                name.isEmpty()->{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Informasi")
                    builder.setMessage("Tolong Isi Nama Anda!")
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                    }, 3000)
                }
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
                password!=konfirmasi->{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Informasi")
                    builder.setMessage("Password Anda Berbeda, Tolong Dicek!")
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                    }, 3000)
                }
                else->{
                    signUpViewModel.signup(signUp = SignUp(name, email, password))
                }
            }

        }
    }

    private fun loading(isLoading: Boolean) { binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE }
}