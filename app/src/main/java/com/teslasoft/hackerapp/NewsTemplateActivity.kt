package com.teslasoft.hackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.teslasoft.hackerapp.screen.NewsTemplateScreen
import com.teslasoft.hackerapp.ui.theme.HackerAppTheme

@ExperimentalMaterial3Api
class NewsTemplateActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val imgurl = intent.getStringExtra("imgurl")
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val content = intent.getStringExtra("content")
        val date = intent.getStringExtra("date")
        val author = intent.getStringExtra("author")

        setContent {
            HackerAppTheme {
                NewsTemplateScreen(this, imgUrl = imgurl, title = title, description = description, content = content,date = date, author = author)
            }
        }

    }
}