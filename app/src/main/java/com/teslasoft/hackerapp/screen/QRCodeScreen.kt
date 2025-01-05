package com.teslasoft.hackerapp.screen

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.teslasoft.hackerapp.QRGeneratedActivity
import com.teslasoft.hackerapp.R
import com.teslasoft.hackerapp.component.AppBar
import java.io.ByteArrayOutputStream

@ExperimentalMaterial3Api
@Composable

fun QRCodeScreen(activity: Activity? = null) {

    val content = remember { mutableStateOf("") }
    val qrBitmap = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { AppBar(activity = activity) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x88095B3D),
                            MaterialTheme.colorScheme.background
                        ), startY = 0.0f,
                        endY = 540.0f
                    )
                )
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .defaultMinSize(minHeight = 300.dp)
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Text(
                text = "QR Code Generator",
                modifier = Modifier
                    .align(Alignment.Start),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 30.sp, color = MaterialTheme.colorScheme.outline
                ),
                fontFamily = FontFamily(Font(R.font.alata_regular)),
                fontWeight = FontWeight(500)
            )

            TextField(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                value = content.value,
                onValueChange = { content.value = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.alata_regular)), fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.outline
                ),
                label = { Text("Enter content") }
            )

            Button(
                modifier = Modifier.align(Alignment.Start).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),

                onClick = {

                    if (content.value.isBlank()) {
                        Toast.makeText(context, "Missing input", Toast.LENGTH_SHORT).show()
                    } else {
                        qrBitmap.value = generateQRCode(content.value)
                        qrBitmap.value?.let {
                            val byteArrayOutputStream = ByteArrayOutputStream()
                            it.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                            val byteArray = byteArrayOutputStream.toByteArray()

                            activity?.startActivity(
                                Intent(activity, QRGeneratedActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT).apply {
                                    putExtra("qr_code_byte_array", byteArray)
                                }
                            )
                        }
                    }

                }
            ) {
                Text(
                    text = "Generate QR",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.background
                    ),
                    fontFamily = FontFamily(Font(R.font.alata_regular)),
                    fontWeight = FontWeight(500)
                )
            }
        }
    }
}

fun generateQRCode(content: String): Bitmap? {
    return try {
        val encoder = BarcodeEncoder()
        encoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 800, 800)
    } catch (e: WriterException) {
        Log.e("QR", "generateQRCode:${e.message}")
        null
    }
}