import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voisfinalproject.data.GitHubUser
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel : ViewModel() {
    private val _users = mutableStateOf<List<GitHubUser>>(emptyList())
    val users: State<List<GitHubUser>> = _users

    fun searchUsers(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchUsers(query)
                _users.value = response.items
            } catch (e: HttpException) {
                // Handle error
            }
        }
    }
}
