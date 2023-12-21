package com.tanahku.ui.detailtanaman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.tanahku.databinding.ActivityDetailTanamanBinding

class DetailTanamanActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailTanamanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTanamanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUrl = intent.getStringExtra(PHOTO_URL)
        val name = intent.getStringExtra(NAME)
        val latin = intent.getStringExtra(LATIN)
        val desc = intent.getStringExtra(DESC)
        val habitat = intent.getStringExtra(HABITAT)
        val kegunaan1 = intent.getStringExtra(KEGUNAAN1)
        val kegunaan2 = intent.getStringExtra(KEGUNAAN2)
        val kegunaan3 = intent.getStringExtra(KEGUNAAN3)

        Glide.with(binding.root.context)
            .load(photoUrl)
            .into(binding.imgTanaman)

        binding.namaTanaman.text = name
        binding.namaLatin.text = latin
        binding.descTanaman.text = desc
        binding.habitatTanaman.text = habitat
        binding.kegunaan1.text = kegunaan1
        binding.kegunaan2.text = kegunaan2
        binding.kegunaan3.text = kegunaan3
        binding.pelajariButton.setOnClickListener{
            val toast = Toast.makeText(this, "Coming Soon...", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    companion object{
        const val NAME = "name"
        const val LATIN = "latin"
        const val DESC = "desc"
        const val HABITAT = "habitat"
        const val KEGUNAAN1 = "kegunaan1"
        const val KEGUNAAN2 = "kegunaan2"
        const val KEGUNAAN3 = "kegunaan3"
        const val PHOTO_URL = "photo_url"
    }
}