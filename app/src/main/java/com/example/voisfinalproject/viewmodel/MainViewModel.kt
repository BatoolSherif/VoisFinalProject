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
    private var currentPage = 1
    private var isLoading = false
    private var hasMorePages = true

    fun searchUsers(query: String) {
        if (isLoading || !hasMorePages) return

        isLoading = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchUsers(query, currentPage)
                _users.value = _users.value + response.items
                currentPage++
                hasMorePages = response.items.isNotEmpty()
            } catch (e: HttpException) { //handle error
            } finally {
                isLoading = false
            }
        }
    }
    fun resetPagination() {
        currentPage = 1
        hasMorePages = true
        _users.value = emptyList()
    }
}

