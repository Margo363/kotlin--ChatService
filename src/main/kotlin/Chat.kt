data class Chat(
    var idChat: Int,
    var nameChat: String = "Vasya",
    var userId: Int,
    var delUser: Boolean = false,
    var delete: Boolean = false
)

data class User(
    var idUser: Int = 0,
    val nameUser: String = "Vasya",
    var del: Boolean = false
) {
    override fun toString(): String {
        return """User: idUser = $idUser, name = '$nameUser', deleted = $del"""
    }
}

data class Message(
    var chatId: Int,
    var userId: Int,
    var idMessage: Int,
    val text: String?,
    var read: Boolean = false,
    var delMessage: Boolean = false
)
