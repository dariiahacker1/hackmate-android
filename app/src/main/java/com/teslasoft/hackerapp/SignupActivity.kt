package com.teslasoft.hackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.teslasoft.hackerapp.screen.SignupScreen
import com.teslasoft.hackerapp.ui.theme.HackerAppTheme

@ExperimentalMaterial3Api
class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HackerAppTheme {
                SignupScreen(this)
            }
        }

    }
}
