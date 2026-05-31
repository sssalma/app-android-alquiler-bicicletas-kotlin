package cat.deim.asm40.pedalean2.data.datasource.api.model


import com.google.gson.annotations.SerializedName

data class TokenApiModel(
    @SerializedName("access") val access: String,
    @SerializedName("refresh") val refresh: String
)

data class TokenRequestApiModel(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
