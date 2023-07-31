// The application's main activity is responsible for requesting
// the POST_NOTIFICATIONS permission from the user, showing a rationale dialog if needed,
// and displaying a message when the permission is granted.
// Resources:
//https://developer.android.com/develop/ui/views/notifications/build-notification

package com.ics342.labs

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.ics342.labs.ui.theme.LabsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define mutable state variables to track
        // permission status and whether to show rationale dialog
        setContent {
            var hasPermission by remember { mutableStateOf(false) } // Permission granted status
            var showPermissionRationale by remember { mutableStateOf(false) } // Rationale dialog status
            val context = LocalContext.current    //Retrieves the current context

            // Defines a launcher for a permission request.
            val launcher = rememberLauncherForActivityResult(
                RequestPermission()
            ) { isGranted ->
                if (isGranted) { // If permission is granted, update the state
                    hasPermission = true
                } else {      // Check if rationale dialog should be shown
                    showPermissionRationale = shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            // Define the app theme and layout
            LabsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        if (hasPermission) {
                            LocationView()   // Show view if permission is granted
                        } else {
                            Button(onClick = {
                                // Check or request permission on button click
                                checkOrRequestPermission(
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS,
                                    launcher
                                ) { hasPermission = true }
                            }) {
                                Text("Enable Notifications")
                            }
                        }
                        // Show rationale dialog if required
                        if (showPermissionRationale) {
                            PermissionRationaleDialog(
                                onConfirm = { launcher.launch(Manifest.permission.POST_NOTIFICATIONS) },
                                onCancel = { showPermissionRationale = false }
                            )
                        }
                    }
                }
            }
        }
    }
}
// View for displaying a text when permission is granted
@Composable
fun LocationView() {
    Text("You have enabled notifications. Thank you!")
}
// Rationale dialog component
@Composable
fun PermissionRationaleDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text("Yes, Allow")
            }
        },
        dismissButton = {
            Button(onClick = { onCancel() }) {
                Text("No, Thanks")
            }
        },
        title = {
            Text("Enable Notifications?")
        },
        text = {
            Text("Would you like to enable notifications for our app? This allows you to stay updated with our latest content.")
        }
    )
}
// Function to check or request a permission
private fun checkOrRequestPermission(
    context: Context,
    permission: String,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    permissionGranted: () -> Unit
) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(context, permission) -> {
            permissionGranted()  // Call callback if permission is already granted
        }
        else -> {
            launcher.launch(permission)  // Request permission if not granted
        }
    }
}
/*// Lab 8: Notifications

package com.ics342.labs

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.ics342.labs.ui.theme.LabsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var hasPermission by remember { mutableStateOf(false) }
            val context = LocalContext.current
            val launcher = rememberLauncherForActivityResult(RequestPermission()) {
                if (it) {
                    startNotificationService(context)
                }
            }
            LabsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Button(
                            onClick = {
                                checkOrRequestPermission(
                                    context,
                                    launcher
                                ) { startNotificationService(context) }
                            }
                        ) {
                            Text("Show Notification")
                        }
                    }
                }
            }
        }
    }
}

fun startNotificationService(context: Context) {
    val intent = Intent(context, NotificationService::class.java)
    ContextCompat.startForegroundService(context, intent)
}


private fun checkOrRequestPermission(
    context: Context,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    permissionGranted: () -> Unit
) {
    if (
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED) {
        permissionGranted()
    } else {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}*/
