// Lab 7: how to request a permission

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
        setContent {
            // defines a mutable state for whether the app has the required permission or not.
            // initially set to false.
            var hasPermission by remember { mutableStateOf(false) }
            // defines a mutable state for whether to show the permission rationale dialog.
            // initially set to false.
            var showPermissionRationale by remember { mutableStateOf(false) }

            //  gets the current context
            val context = LocalContext.current

            // sets up a request for permission and defines what to do when the result is returned
            val launcher = rememberLauncherForActivityResult(RequestPermission()) { isGranted: Boolean ->
                hasPermission = isGranted
                showPermissionRationale = !isGranted
            }

            LabsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        //  If the app has permission, it shows a view with a text indicating that.
                        //  Otherwise, it shows a button to request permission.
                        if (hasPermission) {
                            LocationView()
                        } else {
                            Button(onClick = {
                                checkOrRequestPermission(
                                    context,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    launcher
                                ) {
                                    hasPermission = true
                                }
                            }) {
                                Text("Request Location Permission")
                            }
                        }
                        // If showPermissionRationale is true, it shows a dialog
                        // explaining why the app needs the permission.

                        if (showPermissionRationale) {
                            PermissionRationaleDialog(
                                onConfirm = { launcher.launch(Manifest.permission.ACCESS_COARSE_LOCATION) },
                                onCancel = { showPermissionRationale = false }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationView() {
    Text("Has Location Permission")
}
// displays an AlertDialog explaining why the app needs the permission.
// the dialog has an OK button and a No button with respective click handlers.
@Composable
private fun PermissionRationaleDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = @Composable {
            Button(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        dismissButton = @Composable {
            Button(onClick = { onCancel() }) {
                Text("No")
            }
        },
        title = @Composable {
            Text("Permissions Required")
        },
        text = @Composable {
            Text("This app requires this permission to use this feature.")
        }
    )
}
// function checks if the app has the specified permission.
// If it does, it calls the permissionGranted function.
// If it doesn't, it launches a request for the permission.
private fun checkOrRequestPermission(
    context: Context,
    permission: String,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    permissionGranted: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
        permissionGranted()
    } else {
        launcher.launch(permission)
    }
}


