package com.teslasoft.hackerapp.component

import android.app.Activity
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teslasoft.hackerapp.MainActivity
import com.teslasoft.hackerapp.R


@ExperimentalMaterial3Api
@Composable
fun AppBar(
    activity: Activity? = null,
    ) {

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
        if(activity is MainActivity) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimaryContainer)) { // Style for "Hack"
                            append("Hack")
                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.surfaceVariant)) { // Style for "Mate"
                            append("Mate")
                        }
                    },
                    style = TextStyle(
                        fontSize = 23.sp, color = Color(0xFF131313),
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                        fontWeight = FontWeight(600)
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { activity?.finish() }) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Localized description",
                    tint = if(activity is MainActivity)MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.outline
                )
            }
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Localized description",
                    tint = if(activity is MainActivity)MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.outline
                )
            }
        }
        //,
        // scrollBehavior = scrollBehavior

    )
}

