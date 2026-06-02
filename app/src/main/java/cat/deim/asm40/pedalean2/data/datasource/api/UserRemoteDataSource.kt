package cat.deim.asm40.pedalean2.data.datasource.api

import android.util.Log
import cat.deim.asm40.pedalean2.data.datasource.api.model.TokenRequestApiModel
import cat.deim.asm40.pedalean2.data.datasource.api.model.UserApiModel
import com.pedalean2.common.datasource.local.model.UserModel
import com.pedalean2.common.interfaces.IDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class UserRemoteDatasource(
    private val apiService: ApiService,
    private val tokenStorage: TokenStorage,
    private val serverToken: String
) : IDatasource<UserModel> {

    override fun getAll(): List<UserModel> = runBlocking {
        withContext(Dispatchers.IO) {
            try {
                val bearer = tokenStorage.getBearerToken() ?: return@withContext emptyList()
                val response = apiService.getUser(serverToken, bearer)
                if (response.isSuccessful) {
                    val user = response.body()?.user
                    if (user?.uuid != null) listOf(user.toUserModel()) else emptyList()
                } else emptyList()
            } catch (e: Exception) {
                Log.e("UserRemoteDatasource", "getAll error: ${e.message}")
                emptyList()
            }
        }
    }

    override fun getById(uuid: String): UserModel? = getAll().find { it.uuid == uuid }
    override fun insert(dataModel: UserModel): Boolean = false
    override fun update(dataModel: UserModel): Boolean = false
    override fun delete(uuid: String): Boolean = false

    suspend fun login(username: String, password: String): Boolean {
        return try {
            val response = apiService.getToken(
                serverToken = serverToken,
                credentials = TokenRequestApiModel(username = username, password = password)
            )
            Log.d("UserRemoteDatasource", "login response code: ${response.code()}")
            Log.d("UserRemoteDatasource", "login response body: ${response.body()}")
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    tokenStorage.saveTokens(body.access, body.refresh)
                    tokenStorage.saveUserEmail(username)
                    true
                } else false
            } else false
        } catch (e: Exception) {
            Log.e("UserRemoteDatasource", "login exception: ${e.message}")
            false
        }
    }

    private fun UserApiModel.toUserModel(): UserModel = UserModel(
        uuid = uuid ?: "", name = name ?: "", userName = userName ?: "",
        email = email ?: "", courseGroup = courseGroup ?: "",
        phoneNumber = phoneNumber ?: "", birthDate = birthDate ?: "",
        isInRenting = isInRenting, totalRentingTime = totalRentingTime,
        totalRents = totalRents, creditCardNumber = creditCardNumber ?: "",
        creditCardCvv = creditCardCvv,
        creditCardExpirationDateMonth = creditCardExpirationDateMonth,
        creditCardExpirationDateYear = creditCardExpirationDateYear
    )
}