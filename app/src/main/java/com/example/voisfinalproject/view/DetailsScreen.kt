package com.example.voisfinalproject.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

//
                                UserDetailRow(label = "Login:", value = user.login)
                                UserDetailRow(label = "ID:", value = user.id.toString())
                                UserDetailRow(label = "Node ID:", value = user.node_id)
                                UserDetailRow(label = "Avatar URL:", value = user.avatar_url)
                                UserDetailRow(label = "URL:", value = user.url)
                                UserDetailRow(label = "HTML URL:", value = user.html_url)
                                UserDetailRow(label = "Followers URL:", value = user.followers_url)
                                UserDetailRow(label = "Following URL:", value = user.following_url)
                                UserDetailRow(label = "Gists URL:", value = user.gists_url)
                                UserDetailRow(label = "Starred URL:", value = user.starred_url)
                                UserDetailRow(label = "Subscriptions URL:", value = user.subscriptions_url)
                                UserDetailRow(label = "Organizations URL:", value = user.organizations_url)
                                UserDetailRow(label = "Repos URL:", value = user.repos_url)
                                UserDetailRow(label = "Events URL:", value = user.events_url)
                                UserDetailRow(label = "Received Events URL:", value = user.received_events_url)
                                UserDetailRow(label = "Type:", value = user.type)
                                UserDetailRow(label = "Site Admin:", value = user.site_admin.toString())
                                UserDetailRow(label = "Name:", value = user.name ?: "N/A")
                                UserDetailRow(label = "Company:", value = user.company ?: "N/A")
                                UserDetailRow(label = "Blog:", value = user.blog ?: "N/A")
                                UserDetailRow(label = "Location:", value = user.location ?: "N/A")
                                UserDetailRow(label = "Email:", value = user.email ?: "N/A")
                                UserDetailRow(label = "Hireable:", value = user.hireable?.toString() ?: "N/A")
                                UserDetailRow(label = "Bio:", value = user.bio ?: "N/A")
                                UserDetailRow(label = "Twitter Username:", value = user.twitter_username ?: "N/A")
                                UserDetailRow(label = "Public Repos:", value = user.public_repos.toString())
                                UserDetailRow(label = "Public Gists:", value = user.public_gists.toString())
                                UserDetailRow(label = "Followers:", value = user.followers.toString())
                                UserDetailRow(label = "Following:", value = user.following.toString())
                                UserDetailRow(label = "Created At:", value = user.created_at)
                                UserDetailRow(label = "Updated At:", value = user.updated_at)
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
fun UserDetailRow(label: String, value: String) {
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
