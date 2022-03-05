object ChatService : ChatInterface<Chat, Message, User>{
    var chats = mutableListOf<Chat>()
    var users = mutableListOf<User>()
    var messages = mutableListOf<Message>()

    override fun addUser(user: User): User {
        val newUser = user.copy(idUser = users.size + 1, del = false)
        users.plusAssign(newUser)
        return users.last()
    }

    override fun addChat(chat: Chat, userId: Int): Boolean {
        if (users.userInUsers(userId)) {
            if (!chats.userInChats(userId)) {
                val newChat = chat.copy(idChat = chats.size + 1, userId = userId)
                chats.plusAssign(newChat)
                return true
            }
            throw ChatFoundException("chat with this user exists")
        }
        throw UserNotFoundException("User is not found")
    }

    override fun addMessage(message: Message, idUser: Int, chatId: Int): Message {
        if (users.userInUsers(idUser)) {
            if (chats.userChatDeleted(idUser, chatId)) {
                val newMessage = message.copy(idMessage = messages.size + 1, chatId = chatId, userId = idUser)
                messages.plusAssign(newMessage)
                return messages.last()
            }
            throw IncorrectlyChatUserException("you cannot send a message to a user")
        }
        throw UserNotFoundException("User is not found")
    }

    override fun deleteUser(user: User, userId: Int): Boolean {
        if (users.userDeleted(userId)) {
            users[userId - 1] = user.copy(idUser = userId, del = true)
            return true
        }
        throw UserDelException("user has been deleted or does not exist")
    }

    override fun deleteChat(chat: Chat, chatId: Int, idUser: Int): Boolean {
        if (users.userInUsers(idUser)) {
            if (chats.userChatDeleted(idUser, chatId)) {
                chats[chatId - 1] = chat.copy(idChat = chatId, userId = idUser, delete = true)
                if (messages.messageInChats(chatId)) {
                    messages.filteredMessagesForDeleteChat(chatId)
                }
                return true
            }
            throw IncorrectlyChatUserException("you cannot send a message to a user")
        }
        throw UserNotFoundException("User is not found")
    }

    override fun deleteMessage(chat: Chat, message: Message, idMessage: Int, idUser: Int, chatId: Int): Boolean {
        if (messages.filteredMessagesForDeleteUser(idUser, chatId, idMessage)) {
            messages[idMessage - 1] = message.copy(delMessage = true)
            return true
        }
        throw MessageNotFind("message not found")
    }

    override fun editMessage(newMessage: Message, idMessage: Int): Boolean {
        if (messages.messageNotDeleted(idMessage)) {
            for ((index, message) in messages.withIndex()) {
                messages[index] = message.copy(idMessage = newMessage.idMessage, text = newMessage.text)
                return true
            }
        }
        throw MessageNotFind("message not found")
    }

    override fun receiveUnreadMessagesInChat() {
        for (chat in chats) {
            if (!chat.delete) {
                if (messages.unreadListLastMessages()) {
                    println("no unread messages")
                }
                println("have unread messages")
            }
        }
    }

    override fun receiveAllMessagesInChat(chatId: Int) {
        val countMessages = messages.countAllMessagesInChat(chatId)
        println("Total messages in chat: $countMessages.")
        messages.doAllMessagesInChatRead(chatId)
        messages.printAllMessagesInChat(chatId)
        println("Last messageID: ${messages.maxIdMessageInChat(chatId)}")
    }
}

class UserNotFoundException(message: String) : RuntimeException("User is not found")
class UserDelException(message: String) : RuntimeException("user has been deleted or does not exist")
class ChatFoundException(message: String) : RuntimeException("chat with this user exists")
class IncorrectlyChatUserException(message: String) : RuntimeException("you cannot send a message to a user")
class MessageNotFind(message: String) : RuntimeException("message not found")
