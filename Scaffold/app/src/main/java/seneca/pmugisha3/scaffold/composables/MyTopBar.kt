package com.example.jan6_project2.Scaffold

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    val context = LocalContext.current
    TopAppBar(
        title = { Text("Scaffold Example") },
        navigationIcon = {
            IconButton(onClick = {
                Toast.makeText(context, "Menu Clicked", Toast.LENGTH_LONG).show()
            }) {
                Icon(Icons.Default.Menu, contentDescription = "menu")
            }
        }, actions = {
            IconButton(onClick = {
                Toast.makeText(context, "Done Clicked", Toast.LENGTH_LONG).show()
            }) {
                Icon(Icons.Default.Done, contentDescription = "menu")
            }
            IconButton(onClick = {
                Toast.makeText(context, "Edit Clicked", Toast.LENGTH_LONG).show()
            }) {
                Icon(Icons.Default.Edit, contentDescription = "menu")
            }
        })
}