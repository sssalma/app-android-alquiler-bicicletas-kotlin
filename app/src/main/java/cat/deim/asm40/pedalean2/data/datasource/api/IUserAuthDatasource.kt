package cat.deim.asm40.pedalean2.data.datasource.api

interface IUserAuthDatasource {
    suspend fun login(username: String, password: String): Boolean
}