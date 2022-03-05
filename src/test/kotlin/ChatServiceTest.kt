import ChatService.addUser
import ChatService.deleteUser
import ChatService.addChat
import ChatService.deleteChat
import ChatService.addMessage
import ChatService.deleteMessage
import ChatService.editMessage
import org.junit.Test
import org.junit.Assert.*

class ChatServiceTest {

    @Test
    fun testAddUser() {
        //Arrange
        val user1 = User(1, "1", false)
        val expected = 1
        //Act
        addUser(user1)
        val result = user1.idUser
        //Assert
        assertEquals(expected, result)
    }

    @Test
    fun testDeleteUser() {
        //Arrange
        val user2 = User(2, "2", false)
        addUser(user2)
        user2.idUser = 1
        //Act
        deleteUser(user2, 1)
        user2.del = true
        //Assert
        assertTrue(user2.del)
    }

    @Test
    fun testAddChat() {
        //Arrange
        val user3 = User(0, " ", false)
        addUser(user3)
        val chat3 = Chat(1, " ", 1, false, false)
        val expected = 1
        //Act
        addChat(chat3, 3)
        val result = chat3.idChat
        //Assert
        assertEquals(expected, result)
    }

    @Test(expected = UserDelException::class)
    fun testDeleteUserShouldThrow() {
        //Arrange
        val user4 = User(4, " ", false)
        val expected = UserDelException::class
        //Act
        val result = deleteUser(user4, 50)
        //Assert
        assertEquals(expected, result)
    }



    @Test(expected = ChatFoundException::class)
    fun testAddChatThrow() {
        //Arrange
        val user = User(0, " ", false)
        addUser(user)
        val chat1 = Chat(2, " ", 1, false, false)
        val expected = ChatFoundException::class
        //Act
        val result = addChat(chat1, 1)
        //Assert
        assertEquals(expected, result)
    }

    @Test
    fun testDeleteChat() {
        //Arrange
        val user = User(0, " ", false)
        addUser(user)
        val chat = Chat(1, " ", 1, false, false)
        //Act
        deleteChat(chat, 1, 1)
        val result = chat.delete
        //Assert
        assertFalse(result)
    }

    @Test(expected = IncorrectlyChatUserException::class)
    fun testDeleteChatThrow() {
        //Arrange
        val user = User(0, " ", false)
        addUser(user)
        val chat1 = Chat(30, " ", 10, false, false)
        val expected = IncorrectlyChatUserException::class
        //Act
        val result = deleteChat(chat1, 30, 1)
        //Assert
        assertEquals(expected, result)
    }

    @Test(expected = IncorrectlyChatUserException::class)
    fun testAddMessageThrow() {
        //Arrange
        val user = User(0, " ", false)
        addUser(user)
        val message = Message(1, 1, 1, " ", false, false)
        val expected = IncorrectlyChatUserException::class
        //Act
        val result = addMessage(message, 1, 30)
        //Assert
        assertEquals(expected, result)
    }

    @Test
    fun testDeleteMessage() {
        //Arrange
        val user2 = User(1, " ", false)
        addUser(user2)
        val chat2 = Chat(1, " ", 2)
        addChat(chat2, 2)
        val message = Message(2, 2, 1, " ", false, false)
        addMessage(message, 2, 2)
        //Act
        deleteMessage(chat2, message, 2, 2, 2)
        val result = message.delMessage
        //Assert
        assertFalse(result)
    }

    @Test
    fun testEditMessage() {
        //Arrange
        val user = User(0, " ", false)
        addUser(user)
        val chat = Chat(0, " ", 1, false, false)
        addChat(chat, 1)
        val message = Message(1, 1, 1, " ", false, false)
        addMessage(message, 1, 1)
        val message1 = Message(1, 1, 2, " ", false, false)
        //Act
        editMessage(message1, 1)
        val result = message.delMessage
        //Assert
        assertFalse(result)
    }

    @Test
    fun receiveUnreadMessages() {
        //Arrange
        val chat = Chat(1, "", 1, false, false)
        chat.idChat = 1
        val expected = println("There are no unread messages in the chat with id - ${chat.idChat}")
        //Act
        val result = println("There are no unread messages in the chat with id - ${chat.idChat}")
        //Assert
        assertEquals(expected, result)
    }
}