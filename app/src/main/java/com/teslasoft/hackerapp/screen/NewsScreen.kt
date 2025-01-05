package com.teslasoft.hackerapp.screen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teslasoft.hackerapp.component.AppBar
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.teslasoft.hackerapp.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.kwabenaberko.newsapilib.models.Article
import com.teslasoft.hackerapp.EncryptionActivity
import com.teslasoft.hackerapp.NewsTemplateActivity

@ExperimentalMaterial3Api
@Composable
fun NewsScreen(activity: Activity? = null, articles: List<Article>) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

  //          val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

//            val sortedArticles = articles
//                .filter { it.title != "[Removed]" }
//                .sortedByDescending { article ->
//                    article.publishedAt?.let { dateStr ->
//                        LocalDateTime.parse(dateStr, dateFormatter)
//                    }
//                }

            Text(
                text = "Cybersecurity News",
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

            if (articles.isNotEmpty()) {
                articles.forEach { article ->
                    if (article.title != null) {
                        NewsCard(
                            title = article.title,
                            imageUrl = article.urlToImage,
                            data = article.publishedAt,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            fontColor = MaterialTheme.colorScheme.outline,
                            onClick = {
                                activity?.let {
                                    val intent = Intent(it, NewsTemplateActivity::class.java).apply {
                                        putExtra("imgurl", article.urlToImage)
                                        putExtra("title", article.title)
                                        putExtra("description", article.description)
                                        putExtra("content", article.content)
                                        putExtra("date", article.publishedAt)
                                        putExtra("author", article.author)
                                    }
                                    it.startActivity(intent)
                                }
                            }
                        )
                    }
                }


            } else {
                CircularProgressIndicator()
            }
        }
    }
}




