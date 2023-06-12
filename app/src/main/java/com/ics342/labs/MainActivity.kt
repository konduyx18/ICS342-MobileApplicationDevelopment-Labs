package com.ics342.labs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ics342.labs.data.DataItem
import com.ics342.labs.ui.theme.LabsTheme



private val dataItems = listOf(
    DataItem(1, "Item 1", "Description 1"),
    DataItem(2, "Item 2", "Description 2"),
    DataItem(3, "Item 3", "Description 3"),
    DataItem(4, "Item 4", "Description 4"),
    DataItem(5, "Item 5", "Description 5"),
    DataItem(6, "Item 6", "Description 6"),
    DataItem(7, "Item 7", "Description 7"),
    DataItem(8, "Item 8", "Description 8"),
    DataItem(9, "Item 9", "Description 9"),
    DataItem(10, "Item 10", "Description 10"),
    DataItem(11, "Item 11", "Description 11"),
    DataItem(12, "Item 12", "Description 12"),
    DataItem(13, "Item 13", "Description 13"),
    DataItem(14, "Item 14", "Description 14"),
    DataItem(15, "Item 15", "Description 15"),
    DataItem(16, "Item 16", "Description 16"),
    DataItem(17, "Item 17", "Description 17"),
    DataItem(18, "Item 18", "Description 18"),
    DataItem(19, "Item 19", "Description 19"),
    DataItem(20, "Item 20", "Description 20"),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabsTheme {
                // applies some design like color, shape, elevation, etc. to its children
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    DataItemList(dataItems = dataItems)
                }
            }
        }
    }
}

// Greeting: This is a simple composable function that displays a string of text on the screen.
// The text is "Hello $name!", where $name is replaced with the input to the function.

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
// DataItemView: This Composable function defines the UI for each individual item in your list
// It takes a DataItem as an argument and formats the id, name, and description
// It's using a Row to place items horizontally.

// First, it places the id and the name next to each other, both with larger, bold font
// Then, it displays the description below them with smaller font.
// The Spacer is used to add some space between elements.

@Composable
fun DataItemView(dataItem: DataItem) {
    /* Create the view for the data item here. */

    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.height(60.dp).fillMaxWidth(),
    ){
        Text(
            text = "${dataItem.id}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(25.dp))
        Text(
            text = dataItem.name,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
    Text(
        text = dataItem.description,
        fontSize = 20.sp,
        textAlign = TextAlign.Left,
        modifier = Modifier.fillMaxWidth()
    )
    Text(
        text = "",
        fontSize = 20.sp
    )

}
// I used the website: https://developer.android.com/jetpack/compose/lists
// DataItemList: This Composable function takes a list of DataItems
// and creates a vertical list using LazyColumn.
// This is an efficient way to display lists in Jetpack Compose,
// as it only renders the items currently visible on screen.
// For each item in the list, it calls DataItemView to display that item.

@Composable
fun DataItemList(dataItems: List<DataItem>) {
    /* Create the list here. This function will call DataItemView() */

    LazyColumn(){
        items(items = dataItems){
            DataItemView(dataItem = it)}
    }
}

// Preview annotation: This is used to preview the Composable function in Android Studio.
// The Greeting function is being previewed here.

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabsTheme {
        Greeting("Android")
    }
}
