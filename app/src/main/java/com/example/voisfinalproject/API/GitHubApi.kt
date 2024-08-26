import com.example.voisfinalproject.data.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.voisfinalproject.data.GitHubUser
import com.example.voisfinalproject.data.GitHubUserDetails
import retrofit2.Retrofit
import retrofit2.http.Path

interface GitHubApi {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String, @Query("page") page: Int = 1): UserResponse
    @GET("users/{username}")
    suspend fun getUserDetails(@Path("username") username: String): GitHubUserDetails
}

