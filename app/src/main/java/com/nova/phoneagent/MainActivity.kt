package com.nova.phoneagent

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : Activity() {
    private lateinit var status: TextView
    private lateinit var log: TextView
    private val prefs by lazy { getSharedPreferences("nova", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        status = findViewById(R.id.status)
        log = findViewById(R.id.log)
        val url = findViewById<EditText>(R.id.serverUrl)
        val token = findViewById<EditText>(R.id.deviceToken)

        url.setText(prefs.getString("url", ""))
        token.setText(prefs.getString("token", ""))

        findViewById<Button>(R.id.connect).setOnClickListener {
            prefs.edit()
                .putString("url", url.text.toString().trim())
                .putString("token", token.text.toString().trim())
                .apply()
            status.text = "Ready for Nova connection"
            log.text = "Settings saved. PC→phone command channel will be connected in the next integration step."
        }

        findViewById<Button>(R.id.camera).setOnClickListener { openCamera() }

        findViewById<Button>(R.id.youtube).setOnClickListener {
            val launch = packageManager.getLaunchIntentForPackage("com.google.android.youtube")
            if (launch != null) startActivity(launch)
            else startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com")))
        }

        findViewById<Button>(R.id.files).setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "*/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }, 10)
        }
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 20)
            return
        }
        startActivity(Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == RESULT_OK) {
            log.text = "Selected: ${data?.data}"
        }
    }
}
