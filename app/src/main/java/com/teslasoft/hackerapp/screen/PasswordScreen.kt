package com.teslasoft.hackerapp.screen

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teslasoft.hackerapp.R
import com.teslasoft.hackerapp.component.AppBar
import com.teslasoft.hackerapp.component.NavBar
import org.passay.CharacterRule
import org.passay.EnglishCharacterData
import org.passay.PasswordGenerator

fun copy(context: Context, value: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("password", value)
    clipboard.setPrimaryClip(clip)
}

fun generateSecurePassword(
    length: Int,
    upper: Boolean,
    lower: Boolean,
    digits: Boolean,
    special: Boolean
): String {

    if (!upper && !lower && !digits && !special) return ""

    val rules = mutableListOf<CharacterRule>()

    if (upper) {
        rules.add(CharacterRule(EnglishCharacterData.UpperCase))
    }
    if (lower) {
        rules.add(CharacterRule(EnglishCharacterData.LowerCase))
    }
    if (digits) {
        rules.add(CharacterRule(EnglishCharacterData.Digit))
    }
    if (special) {
        rules.add(CharacterRule(EnglishCharacterData.SpecialAscii))
    }

    return PasswordGenerator().generatePassword(length, *rules.toTypedArray())
}


@ExperimentalMaterial3Api
@Composable
fun PasswordScreen(activity: Activity? = null, innerPadding: PaddingValues) {

    val upper = remember { mutableStateOf(true) }
    val lower = remember { mutableStateOf(true) }
    val digits = remember { mutableStateOf(true) }
    val special = remember { mutableStateOf(true) }

    val context = LocalContext.current
    val length = remember { mutableIntStateOf(10) }
    val password = remember {
        mutableStateOf(
            generateSecurePassword(
                length.value,
                upper.value,
                lower.value,
                digits.value,
                special.value
            )
        )
    }

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
            text = "Password Generator",
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

        //Text(generateSecurePassword(20))

        TextField(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(),
            value = password.value,
            onValueChange = { password.value = it },
            trailingIcon = {
                IconButton(onClick = {
                    copy(context, password.value)
                }) {
                    Icon(
                        Icons.Filled.ContentCopy,
                        contentDescription = "Copy Password",
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            },
            readOnly = true,
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


        Card(
            modifier = Modifier
                .fillMaxWidth(),
            //   .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.outlineVariant,
            ),
            shape = RoundedCornerShape(16.dp)

        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Character Length",
                        style = TextStyle(
                            fontSize = 19.sp, color = MaterialTheme.colorScheme.outline
                        ),
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                    )
                    Text(
                        "${length.value}",
                        style = TextStyle(
                            fontSize = 25.sp, color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight(600)
                        ),
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                    )
                }
                Slider(
                    value = length.value.toFloat(),
                    onValueChange = { newValue ->
                        length.value = newValue.toInt()
                        password.value =
                            generateSecurePassword(
                                length.value,
                                upper.value,
                                lower.value,
                                digits.value,
                                special.value
                            )
                    },
                    valueRange = 8f..25f,
                    colors = SliderDefaults.colors(
                        activeTrackColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        inactiveTrackColor = MaterialTheme.colorScheme.background
                    )
                )



                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            checkmarkColor = MaterialTheme.colorScheme.background
                        ),
                        checked = upper.value,
                        onCheckedChange = {
                            upper.value = it
                            password.value = generateSecurePassword(
                                length.value,
                                upper.value,
                                lower.value,
                                digits.value,
                                special.value
                            )
                        }
                    )
                    Text(
                        "Include Uppercase",
                        style = TextStyle(
                            fontSize = 18.sp, color = MaterialTheme.colorScheme.outline
                        ),
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                    )
                }


                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            checkmarkColor = MaterialTheme.colorScheme.background
                        ),
                        checked = lower.value,
                        onCheckedChange = {
                            lower.value = it
                            password.value = generateSecurePassword(
                                length.value,
                                upper.value,
                                lower.value,
                                digits.value,
                                special.value
                            )
                        }
                    )
                    Text(
                        "Include Lowercase",
                        style = TextStyle(
                            fontSize = 18.sp, color = MaterialTheme.colorScheme.outline
                        ),
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                    )
                }


                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            checkmarkColor = MaterialTheme.colorScheme.background
                        ),
                        checked = digits.value,
                        onCheckedChange = {
                            digits.value = it
                            password.value = generateSecurePassword(
                                length.value,
                                upper.value,
                                lower.value,
                                digits.value,
                                special.value
                            )
                        }
                    )
                    Text(
                        "Include Digits",
                        style = TextStyle(
                            fontSize = 18.sp, color = MaterialTheme.colorScheme.outline
                        ),
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                    )
                }


                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            checkmarkColor = MaterialTheme.colorScheme.background
                        ),
                        checked = special.value,
                        onCheckedChange = {
                            special.value = it
                            password.value = generateSecurePassword(
                                length.value,
                                upper.value,
                                lower.value,
                                digits.value,
                                special.value
                            )
                        }
                    )
                    Text(
                        "Include Special Characters",
                        style = TextStyle(
                            fontSize = 18.sp, color = MaterialTheme.colorScheme.outline
                        ),
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                    )
                }
            }
        }


        Button(modifier = Modifier
            .align(Alignment.Start)
            .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            onClick = {
                password.value = generateSecurePassword(
                    length.value,
                    upper.value,
                    lower.value,
                    digits.value,
                    special.value
                )
            }) {
            Text(
                text = "Generate", style = TextStyle(
                    fontSize = 18.sp, color = MaterialTheme.colorScheme.background
                ), fontFamily = FontFamily(Font(R.font.alata_regular)),
                fontWeight = FontWeight(500)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

    }
}
