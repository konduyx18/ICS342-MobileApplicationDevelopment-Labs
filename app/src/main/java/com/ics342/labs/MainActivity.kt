package com.ics342.labs

// importing all the necessary libraries for the classes,
// functions, and composables that will be used in the file

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button

// A list of DataItem objects is created as a source of data for the list.
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
// MainActivity: This is the main activity class that extends ComponentActivity
class MainActivity : ComponentActivity() {

    // onCreate: This is the first method that gets called when our activity is created
    // Here we define the UI for our activity.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This block is where you define the UI using Jetpack Compose.
        // Define two state variables: showDialog and currentDataItem.
        // showDialog is a Boolean that controls whether the dialog is shown,
        // and currentDataItem holds the DataItem that was clicked
        setContent {
            val showDialog = remember { mutableStateOf(false) }
            val currentDataItem = remember { mutableStateOf<DataItem?>(null) }

            // provide styling for the app.
            // Surface provides a background and other view configurations
            LabsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //  A function that displays a list of items. The items are created using the DataItemView function.
                    //  When an item is clicked, currentDataItem is set to the clicked item and showDialog is set to true

                    DataItemList(dataItems = dataItems) { dataItem ->
                        currentDataItem.value = dataItem
                        showDialog.value = true
                    }
                    if (showDialog.value) {
                        // This is a Composable function that shows a dialog.
                        // It is only shown if showDialog is true.
                        // The title and message of the dialog are taken from currentDataItem.
                        // The dialog is dismissed when the "Okay" button is clicked
                        AlertDialog(
                            onDismissRequest = { showDialog.value = false },
                            title = { Text(text = currentDataItem.value?.name ?: "") },
                            text = { Text(text = currentDataItem.value?.description ?: "") },
                            confirmButton = {
                                Button(onClick = { showDialog.value = false }) {
                                    Text("Okay")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
// displays a greeting message
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//  a Composable function that displays a DataItem
//  When the Row within this function is clicked, it triggers
//  the onItemClicked function passed as a parameter
@Composable
fun DataItemView(dataItem: DataItem, onItemClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .clickable { onItemClicked() },
        horizontalArrangement = Arrangement.Start
    ) {
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
}
// displays a list of DataItem objects
// For each item, a DataItemView is created
@Composable
fun DataItemList(dataItems: List<DataItem>, onItemClicked: (DataItem) -> Unit) {
    LazyColumn() {
        items(items = dataItems) { dataItem ->
            DataItemView(dataItem = dataItem, onItemClicked = { onItemClicked(dataItem) })
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabsTheme {
        Greeting("Android")
    }
}
