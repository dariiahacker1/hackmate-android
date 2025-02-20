package com.teslasoft.hackerapp.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teslasoft.hackerapp.R
import com.teslasoft.hackerapp.component.AppBar
import com.teslasoft.hackerapp.data.db.model.Term

@ExperimentalMaterial3Api
@Composable
fun TermsScreen(activity: Activity? = null, termsList:List<Term>) {

    val links = arrayOf("A-B", "C-D", "E-F", "G-H", "I-K", "L-M", "N-O", "P-Q", "R-S", "T-U", "V-Z")
    val lazyListState = rememberLazyListState()
    val selectedLetterIndex = remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(selectedLetterIndex.value) {
        selectedLetterIndex.value?.let { firstTermIndex ->
            lazyListState.scrollToItem(firstTermIndex)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { AppBar(activity = activity) },

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

                .fillMaxSize()
                .defaultMinSize(minHeight = 300.dp)
                .padding(start = 16.dp, end = 16.dp, top = innerPadding.calculateTopPadding(), bottom = 0.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Glossary of Terms",
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(shape = RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.outlineVariant)
                    .horizontalScroll(rememberScrollState())
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                links.forEach { link ->
                    Text(
                        text = link,
                        style = TextStyle(
                            fontSize = 18.sp, color = MaterialTheme.colorScheme.outline
                        ),
                        fontFamily = FontFamily(Font(R.font.alata_regular)),
                        fontWeight = FontWeight(500),
                        modifier = Modifier
                            .clickable {
                                val firstLetter = link.split("-")[0][0]
                                val firstTermIndex = termsList.indexOfFirst { it.name.startsWith(firstLetter, ignoreCase = true) }

                                if (firstTermIndex != -1) {
                                    selectedLetterIndex.value = firstTermIndex
                                }

                            }
                    )
                }
            }

            if (termsList.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn(
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize().padding(bottom = 0.dp)
                ) {
                    items(termsList.sortedBy{it.name}) { term ->
                        TermCard(term = term)
                    }
                }
            }
        }
    }

}

@Composable
fun TermCard(term:Term) {

    val isExpanded = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(24.dp))
        .background(MaterialTheme.colorScheme.outlineVariant)
        .padding(16.dp)
        .clickable {
            isExpanded.value = !isExpanded.value
        }
        .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = term.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontSize = 18.sp, color = MaterialTheme.colorScheme.outline
            ),
            fontFamily = FontFamily(Font(R.font.alata_regular)),
            fontWeight = FontWeight(500)
        )
        Text(
            text = term.description,
            maxLines = if (isExpanded.value) Int.MAX_VALUE else 2,
            overflow = if (isExpanded.value) TextOverflow.Visible  else TextOverflow.Ellipsis ,
            style = TextStyle(
                fontSize = 15.sp, color = MaterialTheme.colorScheme.surfaceTint
            ),
            fontFamily = FontFamily(Font(R.font.alata_regular)),
            fontWeight = FontWeight(500)
        )

    }
}