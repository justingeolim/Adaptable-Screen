package com.example.adaptablescreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val items = listOf("CS 501", "CS 519", "CS 460", "CS 320", "CS 440")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                ResponsiveScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResponsiveScreen() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val isWide = LocalConfiguration.current.screenWidthDp >= 600

    //m3 component req Scaffold
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        //m3 component req TopAppBar
        topBar = { TopAppBar(title = { Text("CS Courses") }) }
    ) { innerPadding ->
        if (isWide) {
            TabletLayout(selectedIndex, { selectedIndex = it }, Modifier.padding(innerPadding))
        } else {
            PhoneLayout(selectedIndex, { selectedIndex = it }, Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun PhoneLayout(selectedIndex: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        items.forEachIndexed { index, item ->
            //m3 component req Listitem
            ListItem(
                headlineContent = { Text(item) },
                modifier = Modifier.clickable { onSelect(index) },
                colors = if (index == selectedIndex) ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer) else ListItemDefaults.colors()
            )
            //m3 component req HOrizontalDivider
            HorizontalDivider()
        }
        Spacer(modifier = Modifier.height(16.dp))
        DetailContent(items[selectedIndex], Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
fun TabletLayout(selectedIndex: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxSize()) {
        Column(
            //modifier requirements
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .verticalScroll(rememberScrollState())
        ) {
            items.forEachIndexed { index, item ->
                ListItem(
                    headlineContent = { Text(item) },
                    modifier = Modifier.clickable { onSelect(index) },
                    colors = if (index == selectedIndex) ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer) else ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                )
                HorizontalDivider()
            }
        }
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .padding(24.dp),
            contentAlignment = Alignment.TopStart
        ) {
            DetailContent(items[selectedIndex], Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun DetailContent(item: String, modifier: Modifier = Modifier) {
    //m3 component req Card
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        //m3 component req Text
        Text(
            text = "Selected: $item",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PhonePreview() {
    MaterialTheme { ResponsiveScreen() }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 480)
@Composable
fun TabletPreview() {
    MaterialTheme { ResponsiveScreen() }
}