// the website used as reference to complete the lab5:
// https://developer.android.com/jetpack/compose/navigation

package com.ics342.labs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import com.ics342.labs.data.DataItem
import com.ics342.labs.ui.theme.LabsTheme

//  enum class: defines routes for two screens: MainScreen and DetailsScreen
enum class Screen(val route: String) {
    MainScreen("main_screen"),
    DetailsScreen("details_screen/{itemId}");

    fun createRoute(itemId: Int): String {
        return if (this == DetailsScreen) "details_screen/$itemId" else route
    }
}


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
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // NavHost is a container: holds all navigation destinations
                    // uses a NavGraph that contains routes for all screens
                    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
                        // composable defines a navigation destination
                        composable(Screen.MainScreen.route) {
                            // Displays a list of dataItems on MainScreen
                            DataItemList(dataItems = dataItems) { dataItem ->
                                // On item click, navigates to DetailsScreen with the id of clicked item
                                navController.navigate(Screen.DetailsScreen.createRoute(dataItem.id))
                            }
                        }
                        composable("details_screen/{itemId}") { backStackEntry ->
                            // Retrieve the clicked item's id from the arguments
                            val itemId = backStackEntry.arguments?.getString("itemId")?.toInt()
                            // Find the dataItem with the provided id
                            val dataItem = dataItems.find { it.id == itemId }

                            if (dataItem != null) {
                                DetailsScreen(dataItem)
                            } else {
                                Text(text = "Item not found")
                            }
                        }
                    }
                }
            }
        }
    }
}

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

@Composable
fun DataItemList(dataItems: List<DataItem>, onItemClicked: (DataItem) -> Unit) {
    LazyColumn() {
        items(items = dataItems) { dataItem ->
            DataItemView(dataItem = dataItem) { onItemClicked(dataItem) }
        }
    }
}

@Composable
fun DetailsScreen(dataItem: DataItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "ID: ${dataItem.id}", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Name: ${dataItem.name}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Description: ${dataItem.description}", fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabsTheme {
        // update this to preview any screen you like
        DataItemView(dataItem = DataItem(1, "Preview Item", "Preview Description")) {}
    }
}
