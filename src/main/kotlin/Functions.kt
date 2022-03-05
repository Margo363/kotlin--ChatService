fun List<User>.userInUsers(usersId: Int): Boolean = this.any{ it.idUser == usersId }
fun List<User>.userDeleted(usersId: Int): Boolean = this.any { !it.del && it.idUser == usersId }

fun List<Chat>.userInChats(usersId: Int): Boolean = this.any{ it.userId == usersId }
fun List<Chat>.userChatDeleted(usersId: Int, chatId: Int): Boolean =
    this.filter { it.userId == usersId } .any { it.idChat == chatId }

fun List<Message>.messageInChats(chatId: Int): Boolean = this.any { it.chatId == chatId }
fun List<Message>.filteredMessagesForDeleteChat(chatId: Int) =
    this.filter { it.chatId == chatId } .forEach { it.delMessage }
fun List<Message>.filteredMessagesForDeleteUser(usersId: Int, chatId: Int, messageId: Int): Boolean =
    this.filter { it.userId == usersId && it.chatId == chatId && it.idMessage == messageId } .any { !it.delMessage }
fun List<Message>.countAllMessagesInChat(chatId: Int): Int =
    this.filter { it.chatId == chatId } .count { !it.delMessage }
fun List<Message>.doAllMessagesInChatRead(chatId: Int) =
    this.filter { it.chatId == chatId } .filter { !it.delMessage } .forEach { it.read }
fun List<Message>.printAllMessagesInChat(chatId: Int) =
    this.filter { it.chatId == chatId } .forEach { println("${it.text}") }
fun List<Message>.maxIdMessageInChat(chatId: Int): Int? =
    this.filter { it.chatId == chatId } .filter { !it.delMessage } .maxOfOrNull { it.idMessage }
fun List<Message>.unreadListLastMessages() = this.any { !it.delMessage && !it.read }
fun List<Message>.messageNotDeleted(idMessage: Int): Boolean = this.any { it.idMessage == idMessage && !it.delMessage }