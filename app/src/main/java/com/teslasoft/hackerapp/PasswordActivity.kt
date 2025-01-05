package com.teslasoft.hackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.teslasoft.hackerapp.component.AppBar
import com.teslasoft.hackerapp.component.NavBar
import com.teslasoft.hackerapp.screen.PasswordScreen
import com.teslasoft.hackerapp.screen.PasswordStrengthScreen
import com.teslasoft.hackerapp.ui.theme.HackerAppTheme


@ExperimentalMaterial3Api
class PasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HackerAppTheme {

                var selectedScreen by remember { mutableIntStateOf(0) }

                Scaffold(
                    modifier = Modifier,
                    topBar = { AppBar(this) },
                    bottomBar = { NavBar(this,
                        onItemSelected = { index -> selectedScreen = index }) }

                ) { innerPadding ->
                    when (selectedScreen) {
                        0 -> PasswordScreen(this, innerPadding)
                        1 -> PasswordStrengthScreen(this, innerPadding)
                    }
                }
            }
        }

    }
}
