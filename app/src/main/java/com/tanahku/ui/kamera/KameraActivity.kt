package com.tanahku.ui.kamera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import com.tanahku.R
import com.tanahku.ml.Tanahku
import com.tanahku.ui.detailtanah.DetailTanahActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class KameraActivity : AppCompatActivity() {
    private lateinit var selectBtn: Button
    private lateinit var predictBtn: Button
    private lateinit var captureBtn: Button
    private lateinit var result: TextView
    private lateinit var imageView: ImageView
    private lateinit var bitmap: Bitmap

    private val labels = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kamera)

        loadLabels()

        selectBtn = findViewById(R.id.selectBtn)
        predictBtn = findViewById(R.id.predictBtn)
        captureBtn = findViewById(R.id.captureBtn)
        result = findViewById(R.id.result)
        imageView = findViewById(R.id.imageView)

        var loadedLabels = application.assets.open("labels.txt").bufferedReader().readLines()

        var imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        getPermission()

        selectBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 10)
        }

        captureBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 12)
        }

        predictBtn.setOnClickListener {
            var model: Tanahku? = null

            try {
                var tensorImage = TensorImage(DataType.FLOAT32)
                tensorImage.load(bitmap)

                tensorImage = imageProcessor.process(tensorImage)

                model = Tanahku.newInstance(this)

                // Creates inputs for reference.
                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                inputFeature0.loadBuffer(tensorImage.buffer)

                // Runs model inference and gets result.
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

                var maxIdx = 0
                outputFeature0.forEachIndexed { index, fl ->
                    if (outputFeature0[maxIdx] < fl) {
                        maxIdx = index
                    }
                }

                result.text = labels[maxIdx]
                val intent = Intent(this@KameraActivity, DetailTanahActivity::class.java)
                intent.putExtra(DetailTanahActivity.TANAH, result.text)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error during model inference", e)
                result.text = "Error during model inference: ${e.message}"
            } finally {
                // Releases model resources if no longer used.
                model?.close()
            }
        }
    }

    private fun loadLabels() {
        try {
            val inputStream = assets.open("labels.txt")
            val reader = inputStream.bufferedReader()
            labels.addAll(reader.readLines())
        } catch (e: Exception) {
            Log.e("MainActivity", "Error loading labels", e)
            result.text = "Error loading labels: ${e.message}"
        }
    }

    private fun getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    11
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 11 && grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            getPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == RESULT_OK) {
            if (data != null) {
                val uri: Uri? = data.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    imageView.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error loading image from gallery", e)
                    result.text = "Error loading image from gallery: ${e.message}"
                }
            }
        } else if (requestCode == 12 && resultCode == RESULT_OK) {
            if (data != null) {
                try {
                    bitmap = data.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error capturing image", e)
                    result.text = "Error capturing image: ${e.message}"
                }
            }
        }
    }
}