package com.teslasoft.hackerapp.screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.teslasoft.hackerapp.R
import com.teslasoft.hackerapp.SecurityX
import com.teslasoft.hackerapp.component.AppBar

@ExperimentalMaterial3Api
@Composable
fun EncryptionScreen(activity: Activity? = null) {

    val plainText = remember { mutableStateOf("") }
    val encryptedText = remember { mutableStateOf("") }
    val encryptionKey = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { AppBar(activity = activity) },

        ) { innerPadding ->

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .defaultMinSize(minHeight = 300.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x88095B3D),
                            MaterialTheme.colorScheme.background
                        ), startY = 0.0f,
                        endY = 540.0f
                    )
                )
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = "AES-256\nEncryption/Decryption",
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

                OutlinedTextField(
                    shape = RoundedCornerShape(16.dp),
                    value = plainText.value,
                    onValueChange = { plainText.value = it },
                    label = { Text("Plain Text") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )

                OutlinedTextField(
                    shape = RoundedCornerShape(16.dp),
                    value = encryptedText.value,
                    onValueChange = { encryptedText.value = it },
                    label = { Text("Encrypted Text") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )

                OutlinedTextField(
                    shape = RoundedCornerShape(16.dp),
                    value = encryptionKey.value,
                    onValueChange = { encryptionKey.value = it },
                    label = { Text("Encryption Key") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                ){

                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                        .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        onClick = {

                            if (encryptionKey.value == "" || plainText.value == "") {
                                Toast.makeText(activity, "Missing input(s)", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                encryptedText.value =
                                    SecurityX.encrypt(encryptionKey.value, plainText.value)
                            }

                        }) {
                        Text(
                            text = "Encrypt", style = TextStyle(
                                fontSize = 18.sp, color = MaterialTheme.colorScheme.background
                            ), fontFamily = FontFamily(Font(R.font.alata_regular)),
                            fontWeight = FontWeight(500)
                        )
                    }

                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                        .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        onClick = {

                            if (encryptionKey.value == "" || encryptedText.value == "") {
                                Toast.makeText(activity, "Missing input(s)", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                plainText.value =
                                    SecurityX.decrypt(encryptionKey.value, encryptedText.value)
                            }

                        }) {
                        Text(
                            text = "Decrypt", style = TextStyle(
                                fontSize = 18.sp, color = MaterialTheme.colorScheme.background
                            ), fontFamily = FontFamily(Font(R.font.alata_regular)),
                            fontWeight = FontWeight(500)
                        )
                    }

                }
            }

        }
    }

}

