package com.teslasoft.hackerapp.screen

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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
import coil3.compose.AsyncImage
import com.google.gson.Gson
import com.kwabenaberko.newsapilib.models.Article
import com.teslasoft.hackerapp.EncryptionActivity
import com.teslasoft.hackerapp.NewsActivity
import com.teslasoft.hackerapp.PasswordActivity
import com.teslasoft.hackerapp.QRCodeActivity
import com.teslasoft.hackerapp.R
import com.teslasoft.hackerapp.TermsActivity
import com.teslasoft.hackerapp.component.AppBar
import com.teslasoft.hackerapp.data.NewsViewModel
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@ExperimentalMaterial3Api
@Composable

fun HomePage(
    modifier: Modifier = Modifier,
    activity: Activity? = null,
    newsViewModel: NewsViewModel
) {

    val articles by newsViewModel.articles.observeAsState(emptyList())

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    val sortedArticles = articles
        .filter { it.title != "[Removed]" }
        .sortedByDescending { article ->
            article.publishedAt?.let { dateStr ->
                LocalDateTime.parse(dateStr, dateFormatter)
            }
        }

    Scaffold(
        modifier = modifier,
        topBar = { AppBar(activity = activity) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            RecentlyPostedNewsList(modifier = Modifier, articles = sortedArticles)
            Spacer(modifier = Modifier.height(12.dp))
            ToolsList(modifier = Modifier, activity = activity, sortedArticles = sortedArticles)

        }
    }

}

@Composable
fun RecentlyPostedNewsList(modifier: Modifier = Modifier, articles: List<Article>) {
    val swiped = remember { mutableIntStateOf(0) }

    val newsList = if (articles.isNotEmpty()) {
        val startIndex = swiped.intValue % articles.size
        articles.slice(startIndex until (startIndex + 3).coerceAtMost(articles.size)).reversed()
    } else {
        emptyList()
    }

    if (newsList.isEmpty()) {
        CircularProgressIndicator()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        newsList.forEachIndexed { index, news ->

            val color = when (index) {
                0 -> MaterialTheme.colorScheme.secondary
                1 -> MaterialTheme.colorScheme.primary
                2 -> MaterialTheme.colorScheme.surfaceVariant
                else -> MaterialTheme.colorScheme.surface
            }

            NewsCard(
                title = news.title,
                imageUrl = news.urlToImage,
                data = news.publishedAt,
                color = color,
                modifier = Modifier
                    .offset(y = (-4).dp * index),
                onClick = {
                    swiped.intValue += 1
                },
                showImage = index == 2
            )
        }
    }
}


@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String? = null,
    imageResId: Int? = null,
    data: String,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    fontColor: Color = MaterialTheme.colorScheme.background,
    showImage: Boolean = true,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .size(width = 330.dp, height = 135.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = color)
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
            ) {

                if (showImage) {
                Image(
                    painter = painterResource(id = R.drawable.android),
                    contentDescription = "Image Not Found",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                       .fillMaxHeight()
                        .width(175.dp)
                        .clip(RoundedCornerShape(24.dp))
                )

                    if (imageUrl != null) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "News Image",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(175.dp)
                                .clip(RoundedCornerShape(24.dp))
                        )
                    } else if (imageResId != null) {
                        // Load image from resource ID if URL is not provided
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = "News Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(175.dp)
                                .clip(RoundedCornerShape(24.dp))
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(shape = RoundedCornerShape(24.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x5202311F),
                                    Color(0x5202311F)
                                )
                            )
                        )
                ){}
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 16.sp, color = fontColor
                    ),
                    fontFamily = FontFamily(Font(R.font.alata_regular)),
                    fontWeight = FontWeight(400)
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = fontColor)) {
                            append("Published: ")
                        }

                        val parsedDate = ZonedDateTime.parse(data)
                        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                        val formattedDate = parsedDate.format(formatter)

                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) {
                            append(formattedDate)
                        }
                    },
                    style = TextStyle(
                        fontSize = 12.sp, color = Color(0xFF131313),
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                        fontWeight = FontWeight(200)
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ToolsList(modifier: Modifier = Modifier, activity: Activity?, sortedArticles: List<Article>) {
    val toolsList = com.teslasoft.hackerapp.data.ToolsList.Tools

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {

            Column(modifier = Modifier.weight(3.0f, true)) {
                LargerButton(
                    onClick = {
                        val jsonArticles = Gson().toJson(sortedArticles)
                        val intent = Intent(activity, NewsActivity::class.java).apply {
                            putExtra("articles_json", jsonArticles)
                        }
                       // activity?.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
                        activity?.startActivity(intent)
                    },
                    text = toolsList[0].name,
                    //  iconResId = toolsList[0].iconResId,
                    icon = Icons.Filled.Newspaper,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Column(modifier = Modifier.weight(1.0f, true)) {
                SmallerButton(
                    onClick = {
                        activity?.startActivity(Intent(activity, PasswordActivity::class.java))
                    },
                    icon = Icons.Filled.Key
                )
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)

        ) {

            Column(modifier = Modifier.weight(1.0f, true)) {
                SmallerButton(
                    onClick = {
                        activity?.startActivity(Intent(activity, TermsActivity::class.java))
                    },
                    //     iconResId = toolsList[2].iconResId
                    icon = Icons.AutoMirrored.Filled.ManageSearch
                )
            }

            Column(modifier = Modifier.weight(3.0f, true)) {
                LargerButton(
                    onClick = {
                        activity?.startActivity(Intent(activity, EncryptionActivity::class.java))
                    },
                    text = toolsList[3].name,
                    icon = Icons.Filled.EnhancedEncryption,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            Column(modifier = Modifier.weight(3.0f, true)) {
                LargerButton(
                    onClick = { /**/ },
                    text = toolsList[4].name,
                    icon = Icons.Filled.NetworkCheck,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Column(modifier = Modifier.weight(1.0f, true)) {
                SmallerButton(
                    onClick = {
                        activity?.startActivity(
                            Intent(
                                activity,
                                QRCodeActivity::class.java
                            )
                        )
                    },
                    icon = Icons.Filled.QrCode2
                )
            }

        }
    }


}


@Composable
fun LargerButton(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    color: Color = MaterialTheme.colorScheme.primaryContainer
) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = {
            Icon(
                imageVector = icon,
                "Extended floating action button.",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = FontFamily(Font(R.font.alata_regular)),
                    fontWeight = FontWeight(400)
                )
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 0.dp, height = 80.dp)
            .clip(RoundedCornerShape(24.dp)),
        containerColor = color

    )
}


@Composable
fun SmallerButton(onClick: () -> Unit, icon: ImageVector) {
    FloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 0.dp, height = 80.dp)
            .clip(RoundedCornerShape(24.dp)),
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "tool icon", modifier = Modifier
                .size(40.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}




