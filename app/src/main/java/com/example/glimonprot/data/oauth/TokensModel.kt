package stud.gilmon.data.oauth

data class TokensModel(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)