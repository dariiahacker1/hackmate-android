package com.teslasoft.hackerapp.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teslasoft.hackerapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


fun getSHA1(input: String): String {
    try {
        val md = MessageDigest.getInstance("SHA-1")
        val messageDigest = md.digest(input.toByteArray())
        val no = BigInteger(1, messageDigest)
        var hashtext = no.toString(16)

        while (hashtext.length < 32) {
            hashtext = "0$hashtext"
        }
        return hashtext.uppercase()
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException(e)
    }
}
fun checkPwnedPassword(password: String): Int {
    return try {
        val hash = getSHA1(password)
        val prefix = hash.substring(0, 5)
        val suffix = hash.substring(5)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.pwnedpasswords.com/range/$prefix")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return -1
            val hashes = response.body?.string() ?: return 0
            hashes.lines().forEach { line ->
                val parts = line.split(":")
                if (parts[0].equals(suffix, ignoreCase = true)) {
                    return parts[1].toInt()
                }
            }
        }
        0
    } catch (e: Exception) {
        -1
    }
}


@Composable
@ExperimentalMaterial3Api
fun PasswordStrengthScreen(
    activity: Activity? = null,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {

    val password = remember {
        mutableStateOf("")
    }

    val testStarted = remember {
        mutableStateOf(false)
    }

    val breachCount = remember { mutableStateOf(-1) }

    LaunchedEffect(password.value) {
        val count = withContext(Dispatchers.IO) {
            try {
                checkPwnedPassword(password.value)
            } catch (e: Exception) {
                -1
            }
        }
        breachCount.value = count
    }


    val hasLowercase = password.value.any { it.isLowerCase() }
    val hasUppercase = password.value.any { it.isUpperCase() }
    val hasDigit = password.value.any { it.isDigit() }
    val hasSpecial = password.value.any { !it.isLetterOrDigit() }

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
            text = "Password Strength Tester",
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
            value = password.value,
            readOnly = testStarted.value,
            visualTransformation = PasswordVisualTransformation(),
            placeholder = {
                Text(
                    text = "Enter your password",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            },
            onValueChange = { password.value = it },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.alata_regular)), fontSize = 18.sp,
                color = MaterialTheme.colorScheme.outline
            )
        )

        if (!testStarted.value) {
            Button(modifier = Modifier
                .align(Alignment.Start)
                .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                onClick = {
                    testStarted.value = true
                }) {
                Text(
                    text = "Test", style = TextStyle(
                        fontSize = 18.sp, color = MaterialTheme.colorScheme.background
                    ), fontFamily = FontFamily(Font(R.font.alata_regular)),
                    fontWeight = FontWeight(500)
                )
            }
        }

        if (testStarted.value) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = MaterialTheme.colorScheme.outlineVariant)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Standard test",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.outline,
                    fontFamily = FontFamily(Font(R.font.alata_regular)),
                    fontWeight = FontWeight(500)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Has lower?",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                        fontWeight = FontWeight(500)
                    )
                    Icon(
                        imageVector = if (hasLowercase) Icons.Filled.Check else Icons.Filled.Close,
                        contentDescription = if (hasLowercase) "true" else "false",
                        tint = if (hasLowercase) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.onError
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Has upper?",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                        fontWeight = FontWeight(500)
                    )
                    Icon(
                        imageVector = if (hasUppercase) Icons.Filled.Check else Icons.Filled.Close,
                        contentDescription = if (hasUppercase) "true" else "false",
                        tint = if (hasUppercase) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.onError
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Has digit?",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                        fontWeight = FontWeight(500)
                    )
                    Icon(
                        imageVector = if (hasDigit) Icons.Filled.Check else Icons.Filled.Close,
                        contentDescription = if (hasDigit) "true" else "false",
                        tint = if (hasDigit) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.onError
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Has special?",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                        fontWeight = FontWeight(500)
                    )
                    Icon(
                        imageVector = if (hasSpecial) Icons.Filled.Check else Icons.Filled.Close,
                        contentDescription = if (hasSpecial) "true" else "false",
                        tint = if (hasSpecial) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.onError
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = MaterialTheme.colorScheme.outlineVariant)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Length test",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.outline,
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                        fontWeight = FontWeight(500)
                    )

                    Text(
                        text = password.value.length.toString(),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                        fontWeight = FontWeight(500)
                    )

                }

                val progress = (password.value.length / 16.0).coerceAtMost(1.0).toFloat()
                val progressColor = when {
                    progress < 0.4f -> MaterialTheme.colorScheme.onError
                    progress < 0.7f -> Color.Yellow
                    else -> MaterialTheme.colorScheme.primaryContainer
                }

                LinearProgressIndicator(
                    color = progressColor,
                    progress = progress,
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp)
                        .fillMaxWidth()
                        .height(4.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = MaterialTheme.colorScheme.outlineVariant)
                    .padding(16.dp)
            ) {

                Text(
                    text = "Have i been pwned?",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.outline,
                    fontFamily = FontFamily(Font(R.font.alata_regular)),
                    fontWeight = FontWeight(500)
                )

                when {
                    breachCount.value == -1 -> {
                        Text(
                            text = "Checking...",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontFamily = FontFamily(Font(R.font.alata_regular))
                        )
                    }
                    breachCount.value == 0 -> {
                        Text(
                            text = "No breaches found!",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            fontFamily = FontFamily(Font(R.font.alata_regular)),
                            fontWeight = FontWeight(500)
                        )
                    }
                    breachCount.value > 0 -> {
                        Text(
                            text = "Breached ${breachCount.value} times!",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onError,
                            fontFamily = FontFamily(Font(R.font.alata_regular)),
                            fontWeight = FontWeight(500)
                        )
                    }
                }

            }

        }


    }
}