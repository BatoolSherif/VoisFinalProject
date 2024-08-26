package com.example.voisfinalproject.ui

import MainViewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import com.example.voisfinalproject.data.GitHubUser
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavController
) {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var selectedUser by remember { mutableStateOf<GitHubUser?>(null) }
    val users by viewModel.users // Directly access the state

    val focusManager = LocalFocusManager.current
    val primaryColor = Color(0xFFd03a3b) // Define the color

    val scrollState = rememberLazyListState()

    LaunchedEffect(scrollState.firstVisibleItemIndex) {
        val isAtBottom = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == users.size - 1
        if (isAtBottom) {
            viewModel.searchUsers(query.text)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GITHUB Users") },
                backgroundColor = primaryColor, // Set color for AppBar
                contentColor = Color.White
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Enter GITHUB username you want to search for: ",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val buttonWidth = 140.dp
                    val textFieldWidth = 230.dp
                    val reducedHeight = 48.dp

                    BasicTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier
                            .width(textFieldWidth)
                            .height(reducedHeight)
                            .border(BorderStroke(2.dp, primaryColor), RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 18.sp)
                    )

                    Button(
                        onClick = {
                            viewModel.resetPagination()
                            viewModel.searchUsers(query.text)
                            focusManager.clearFocus()
                        },
                        modifier = Modifier
                            .width(buttonWidth)
                            .height(reducedHeight),
                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                        shape = RoundedCornerShape(12.dp) // Adjust the radius for roundness
                    ) {
                        Text("Search", color = Color.White)
                    }

                }
            }



            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(state = scrollState) {
                items(users) { user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate("details/${user.login}")
                            },
                        elevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter(data = user.avatar_url),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(50)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = user.id.toString(),
                                    style = MaterialTheme.typography.body2.copy(color = Color.Gray)
                                )
                                Text(
                                    text = user.login,
                                    style = MaterialTheme.typography.subtitle2
                                )
                            }
                        }
                    }
                }
            }

            selectedUser?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "User ID: ${it.id}")
                Image(
                    painter = rememberImagePainter(data = it.avatar_url),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
                Text(text = "Profile URL: ${it.url}")
            }
        }
    }
}

