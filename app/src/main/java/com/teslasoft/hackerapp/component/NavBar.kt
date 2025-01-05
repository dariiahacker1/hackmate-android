package com.teslasoft.hackerapp.component

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Construction
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember


@ExperimentalMaterial3Api
@Composable
fun NavBar(
    activity: Activity? = null, onItemSelected: (Int) -> Unit
) {
    val selectedItem = remember { mutableIntStateOf(0) }
    val items = listOf("Generate", "Strength Test")
    val selectedIcons = listOf(Icons.Filled.Construction, Icons.Filled.Analytics)
    val unselectedIcons = listOf(Icons.Outlined.Construction, Icons.Outlined.Analytics)

    NavigationBar(containerColor = MaterialTheme.colorScheme.outlineVariant) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem.intValue == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item,
                    )
                },
                label = { Text(item) },
                selected = selectedItem.intValue == index,
                onClick = {
                    selectedItem.intValue = index
                    onItemSelected(index)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.surfaceVariant,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedIconColor = MaterialTheme.colorScheme.background
                ),

                )
        }
    }
}
