package com.example.voisfinalproject.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.voisfinalproject.viewmodel.DetailsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun DetailsScreen(
    username: String,
    navController: NavController, // Add NavController parameter
    detailsViewModel: DetailsViewModel = viewModel()
) {
    val userDetails by detailsViewModel.userDetails
    val primaryColor = Color(0xFFd03a3b) // Define the color

    LaunchedEffect(username) {
        detailsViewModel.fetchUserDetails(username)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Details") },
                backgroundColor = primaryColor, // Set color for AppBar
                contentColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp), // Reduced padding
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (userDetails != null) {
                userDetails?.let { user ->
                    // Scrollable content
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), // Reduced padding
                        verticalArrangement = Arrangement.spacedBy(12.dp) // Reduced spacing
                    ) {
                        item {
                            Column(
                                modifier = Modifier.padding(8.dp), // Reduced padding
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Rounded Avatar Image
                                Image(
                                    painter = rememberImagePainter(user.avatar_url),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(80.dp) // Smaller size
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(12.dp)) // Reduced space

                                // Displaying user details
                                UserDetailRow(icon = Icons.Default.Person, label = "Login:", value = user.login)
                                UserDetailRow(icon = Icons.Default.Info, label = "ID:", value = (user.id ?: "N/A").toString())
                                UserDetailRow(icon = Icons.Default.Info, label = "URL:", value = user.url ?: "N/A")
                                UserDetailRow(icon = Icons.Default.AccountBox, label = "Followers URL:", value = user.followers_url)
                                UserDetailRow(icon = Icons.Default.AccountBox, label = "Following URL:", value = user.following_url)
                                UserDetailRow(icon = Icons.Default.Menu, label = "Repos URL:", value = user.repos_url)
                            }
                        }
                    }
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}

// Custom composable for displaying user details with an icon
@Composable
fun UserDetailRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // Reduced vertical padding
    ) {

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold) // Smaller text size
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2 // Smaller text size
        )
    }
}
