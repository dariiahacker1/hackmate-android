package com.teslasoft.hackerapp


import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.teslasoft.hackerapp.screen.QRGenerated
import com.teslasoft.hackerapp.ui.theme.HackerAppTheme


@ExperimentalMaterial3Api
class QRGeneratedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val byteArray = intent.getByteArrayExtra("qr_code_byte_array")
        val bitmap = byteArray?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }

        setContent {
            HackerAppTheme {
                QRGenerated(this, bitmap)
            }
        }

    }
}
