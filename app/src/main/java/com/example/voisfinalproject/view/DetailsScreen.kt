package com.example.voisfinalproject.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.voisfinalproject.viewmodel.DetailsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
@Composable
fun DetailsScreen(
    username: String,
    navController: NavController,
    detailsViewModel: DetailsViewModel = viewModel()
) {
    val userDetails by detailsViewModel.userDetails
    val primaryColor = Color(0xFFd03a3b)
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val boxHeight = screenHeight / 6

    LaunchedEffect(username) {
        detailsViewModel.fetchUserDetails(username)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Details", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                backgroundColor = primaryColor,
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
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (userDetails != null) {
                userDetails?.let { user ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            // Avatar Section
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            )
                            {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(),

                                    contentAlignment = Alignment.Center
                                )
                                {
                                    Image(
                                        painter = rememberImagePainter(user.avatar_url),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                        // Table Section
                        item {
                            UserDetailRow(label = "Login:", value = user.login, backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "ID:", value = user.id.toString(), backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Node ID:", value = user.node_id, backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Avatar URL:", value = user.avatar_url, backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "URL:", value = user.url, backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "HTML URL:", value = user.html_url, backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Followers URL:", value = user.followers_url, backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Following URL:", value = user.following_url, backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Gists URL:", value = user.gists_url, backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Starred URL:", value = user.starred_url, backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Subscriptions URL:", value = user.subscriptions_url, backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Organizations URL:", value = user.organizations_url, backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Repos URL:", value = user.repos_url, backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Events URL:", value = user.events_url, backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Received Events URL:", value = user.received_events_url, backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Type:", value = user.type, backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Site Admin:", value = user.site_admin.toString(), backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Name:", value = user.name ?: "N/A", backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Company:", value = user.company ?: "N/A", backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Blog:", value = user.blog ?: "N/A", backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Location:", value = user.location ?: "N/A", backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Email:", value = user.email ?: "N/A", backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Hireable:", value = user.hireable?.toString() ?: "N/A", backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Bio:", value = user.bio ?: "N/A", backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Twitter Username:", value = user.twitter_username ?: "N/A", backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Public Repos:", value = user.public_repos.toString(), backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Public Gists:", value = user.public_gists.toString(), backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Followers:", value = user.followers.toString(), backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Following:", value = user.following.toString(), backgroundColor = Color(0xFFf2f2f2))
                            UserDetailRow(label = "Created At:", value = user.created_at, backgroundColor = Color(0xFFffffff))
                            UserDetailRow(label = "Updated At:", value = user.updated_at, backgroundColor = Color(0xFFf2f2f2))
                        }
                    }
                }
            } else {
                CircularProgressIndicator(color = primaryColor)
            }
        }
    }
}

@Composable
fun UserDetailRow(label: String, value: String, backgroundColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .border(1.dp, Color(0xFFdee2e6))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            modifier = Modifier.weight(2f)
        )
    }
}
