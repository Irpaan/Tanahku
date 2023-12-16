package com.tanahku.ui.detailtanah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.tanahku.adapter.Adapter
import com.tanahku.data.TanahResponse
import com.tanahku.data.TanamanResponse
import com.tanahku.data.UserPreference
import com.tanahku.data.dataStore
import com.tanahku.databinding.ActivityDetailTanahBinding
import com.tanahku.network.ApiConfig
import com.tanahku.ui.ViewModelFactory
import com.tanahku.ui.signin.SignInActivity
import com.tanahku.ui.signin.SignInViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTanahActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTanahBinding
    private lateinit var signInViewModel: SignInViewModel
    private lateinit var adapter : Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tanah = intent.getStringExtra(TANAH)
        binding = ActivityDetailTanahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = Adapter(this@DetailTanahActivity, arrayListOf())

        binding.rvTanaman.adapter = adapter
        binding.rvTanaman.setHasFixedSize(true)
        binding.rvTanaman.layoutManager = GridLayoutManager(this,2)
        if (tanah == "Aluvial"){
            val namaluvial = ApiConfig.getApiService().gettanamanaluvial()
            val nahaluvial = ApiConfig.getApiService().gettanahaluvial()
            remoteGetTanaman(namaluvial)
            remoteGetTanah(nahaluvial)
        }else if (tanah == "Andosol"){
            val namandosol = ApiConfig.getApiService().gettanamanandosol()
            val nahandosol = ApiConfig.getApiService().gettanahandosol()
            remoteGetTanaman(namandosol)
            remoteGetTanah(nahandosol)
        }else if (tanah == "Entisol"){
            val namentisol = ApiConfig.getApiService().gettanamanentisol()
            val nahentisol = ApiConfig.getApiService().gettanahentisol()
            remoteGetTanaman(namentisol)
            remoteGetTanah(nahentisol)
        }else if (tanah == "Humus"){
            val namhumus = ApiConfig.getApiService().gettanamanhumus()
            val nahhumus = ApiConfig.getApiService().gettanahhumus()
            remoteGetTanaman(namhumus)
            remoteGetTanah(nahhumus)
        }else if (tanah == "Inseptisol"){
            val naminseptisol = ApiConfig.getApiService().gettanamaninseptisol()
            val nahinseptisol = ApiConfig.getApiService().gettanahinseptisol()
            remoteGetTanaman(naminseptisol)
            remoteGetTanah(nahinseptisol)
        }else if (tanah == "Laterit"){
            val namkapur = ApiConfig.getApiService().gettanamankapur()
            val nahkapur = ApiConfig.getApiService().gettanahkapur()
            remoteGetTanaman(namkapur)
            remoteGetTanah(nahkapur)
        }else if (tanah == "Kapur"){
            val namlaterit = ApiConfig.getApiService().gettanamanlaterit()
            val nahlaterit = ApiConfig.getApiService().gettanahlaterit()
            remoteGetTanaman(namlaterit)
            remoteGetTanah(nahlaterit)
        }else if (tanah == "Pasir"){
            val nampasir = ApiConfig.getApiService().gettanamanpasir()
            val nahpasir = ApiConfig.getApiService().gettanahpasir()
            remoteGetTanaman(nampasir)
            remoteGetTanah(nahpasir)
        }

        signInViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[SignInViewModel::class.java]




        binding.descTanah.setOnClickListener{
            signInViewModel.SignOut()
        }


    }

    private fun remoteGetTanaman(namaluvial: Call<ArrayList<TanamanResponse>>) {
        namaluvial.enqueue(object : Callback<ArrayList<TanamanResponse>> {
            override fun onResponse(
                call: Call<ArrayList<TanamanResponse>>,
                response: Response<ArrayList<TanamanResponse>>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    data?.let { setDataToAdapter(it) }
                }
            }

            override fun onFailure(call: Call<ArrayList<TanamanResponse>>, t: Throwable) {
                Log.d("Error", ""+t.stackTraceToString())
            }

        })
    }

    private fun remoteGetTanah(nahaluvial: Call<ArrayList<TanahResponse>>) {
        loading(isLoading = true)
        nahaluvial.enqueue(object : Callback<ArrayList<TanahResponse>> {
            override fun onResponse(
                call: Call<ArrayList<TanahResponse>>,
                response: Response<ArrayList<TanahResponse>>
            ) {
                loading(isLoading = false)
                if(response.isSuccessful){
                    val data = response.body()
                    binding.namaTanah.text = data!!.get(0).nama_tanah
                    binding.descTanah.text = data.get(0).desc_tanah
                    binding.karakterTanah.text = data.get(0).karateresitik_fisik_tanah
                    binding.sifatKimiaTanah.text = data.get(0).sifat_kimia_tanah
                    binding.sifatBiologiTanah.text = data.get(0).sifat_biologi_tanah
                    binding.penyebaranTanah.text = data.get(0).penyebaran_tanah
                    binding.penggunaanTanah.text = data.get(0).penggunaan_tanah
                    Glide.with(this@DetailTanahActivity)
                        .load(data.get(0).gambar_tanah)
                        .into(binding.gambarTanah)
                }
            }

            override fun onFailure(call: Call<ArrayList<TanahResponse>>, t: Throwable) {
                loading(isLoading = true)
                Log.d("Error", ""+t.stackTraceToString())
            }

        })
    }

    fun setDataToAdapter(data: ArrayList<TanamanResponse>){
        adapter.setData(data)
    }

    private fun loading(isLoading: Boolean) { binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE }

    companion object{
        const val TANAH = "tanah"
    }

}