package cat.deim.asm40.pedalean2.data.datasource.api
import cat.deim.asm40.pedalean2.data.datasource.api.model.BikeApiModel
import cat.deim.asm40.pedalean2.data.datasource.api.model.RentApiModel
import cat.deim.asm40.pedalean2.data.datasource.api.model.RentStartRequestApiModel
import cat.deim.asm40.pedalean2.data.datasource.api.model.RentStopRequestApiModel
import cat.deim.asm40.pedalean2.data.datasource.api.model.TokenApiModel
import cat.deim.asm40.pedalean2.data.datasource.api.model.TokenRequestApiModel
import cat.deim.asm40.pedalean2.data.datasource.api.model.UserApiModel
import cat.deim.asm40.pedalean2.data.datasource.api.model.BikeApiResponse
import cat.deim.asm40.pedalean2.data.datasource.api.model.RentApiResponse
import cat.deim.asm40.pedalean2.data.datasource.api.model.UserApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    /**
     * Obtiene los tokens de acceso y refresco a partir de las credenciales del usuario.
     * No requiere Authorization header (es el primer paso de autenticación).
     */
    @POST("endpoints/v2/token/")
    suspend fun getToken(
        @Header("server-token") serverToken: String,
        @Body credentials: TokenRequestApiModel
    ): Response<TokenApiModel>

    /**
     * Obtiene la información del usuario autenticado.
     */
    @GET("endpoints/v2/user")
    suspend fun getUser(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authorization: String
    ): Response<UserApiResponse>

    /**
     * Obtiene la lista de bicicletas disponibles.
     */
    @GET("endpoints/v2/bike")
    suspend fun getBikes(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authorization: String
    ): Response<BikeApiResponse>
    /**
     * Obtiene los alquileres anteriores del usuario.
     */
    @GET("endpoints/v2/rent")
    suspend fun getRents(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authorization: String
    ): Response<RentApiResponse>

    /**
     * Inicia un alquiler de bicicleta.
     */
    @POST("endpoints/v2/rent/start")
    suspend fun startRent(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authorization: String,
        @Body request: RentStartRequestApiModel
    ): Response<RentApiModel>

    /**
     * Finaliza un alquiler de bicicleta activo.
     */
    @POST("endpoints/v2/rent/stop")
    suspend fun stopRent(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authorization: String,
        @Body request: RentStopRequestApiModel
    ): Response<RentApiModel>
}
