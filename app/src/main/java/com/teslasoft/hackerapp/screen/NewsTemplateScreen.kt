package com.teslasoft.hackerapp.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.ResolvedTextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import com.teslasoft.hackerapp.R
import com.teslasoft.hackerapp.component.AppBar

@ExperimentalMaterial3Api
@Composable
fun NewsTemplateScreen(
    activity: Activity? = null,
    imgUrl: String?,
    title: String?,
    content: String?,
    description: String?,
    date: String?,
    author: String?
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppBar(activity = activity) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize() .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                AsyncImage(
                    model = imgUrl,
                    contentDescription = "News Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )

                // Gradient overlay on top of the image
                Box(
                    modifier = Modifier
                        .matchParentSize()  // Fill the entire space of the parent Box
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xDD155748), // The color at the top of the gradient
                                    Color(0xBF000000)// The color at the bottom
                                )
                            )
                        )
                ) {
                    Text(
                        text = title ?: "",
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp),
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 25.sp, color = MaterialTheme.colorScheme.outline
                        ),
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                        fontWeight = FontWeight(500)
                    )
                }
            }


            Column(
                modifier = Modifier.padding(16.dp)
                    .fillMaxSize()
            ) {


                Text(
                    text = description ?: "",
                    style = TextStyle(
                        fontSize = 16.sp, color = MaterialTheme.colorScheme.inverseSurface,
                        fontStyle = FontStyle.Italic
                    ),
                    fontFamily = FontFamily(Font(R.font.alata_regular))
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = content ?: "",
                    style = TextStyle(
                        fontSize = 16.sp, color = MaterialTheme.colorScheme.inverseSurface
                    ),
                    fontFamily = FontFamily(Font(R.font.alata_regular))
                )

            }

            //to ignore fucking inner padding
            Box(modifier = Modifier
                .padding(innerPadding)
                .height(0.dp)
                .width(0.dp)) {}
        }
    }
}