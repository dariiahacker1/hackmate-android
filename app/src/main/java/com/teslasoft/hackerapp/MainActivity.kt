package com.teslasoft.hackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.teslasoft.hackerapp.data.NewsViewModel
import com.teslasoft.hackerapp.screen.HomePage
import com.teslasoft.hackerapp.ui.theme.HackerAppTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        setContent {
            HackerAppTheme {
                HomePage(modifier = Modifier.fillMaxSize(), this, newsViewModel = newsViewModel)
            }
        }
    }
}
