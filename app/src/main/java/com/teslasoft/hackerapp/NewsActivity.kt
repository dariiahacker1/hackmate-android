package com.teslasoft.hackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.kwabenaberko.newsapilib.models.Article
import com.teslasoft.hackerapp.screen.NewsScreen
import com.teslasoft.hackerapp.ui.theme.HackerAppTheme

@ExperimentalMaterial3Api
class NewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var articles: ArrayList<Article> = arrayListOf()

        val jsonArticles = intent.getStringExtra("articles_json")
        if(jsonArticles != null){
            val articleType = object: TypeToken<ArrayList<Article>>() {}.type
            articles = Gson().fromJson(jsonArticles, articleType)
        }

        setContent {
            HackerAppTheme {
                NewsScreen(activity = this, articles = articles)
            }
        }

    }
}
