import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.voisfinalproject.data.GitHubUser
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var selectedUser by remember { mutableStateOf<GitHubUser?>(null) }

    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GITHUB Users") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Make the screen scrollable
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier
                        .weight(.5f)
                        .border(BorderStroke(2.dp, Color.Blue))
                        .padding(16.dp),
                    textStyle = LocalTextStyle.current.copy(color = Color.Black)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        viewModel.searchUsers(query.text)
                        focusManager.clearFocus() // Hide the keyboard
                    },
                    modifier = Modifier
                        .width(120.dp) // Decrease width of the button
                ) {
                    Text("Search")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display cards in a vertical column
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                viewModel.users.value.forEach { user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth() // Make each card take full width
                            .padding(vertical = 4.dp) // Add spacing between cards
                            .clickable { selectedUser = user },
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
                            Text(text = user.login, style = MaterialTheme.typography.subtitle2)
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
