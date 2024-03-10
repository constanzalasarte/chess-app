package clientserver.netty

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.boardGames.Adapter
import edu.austral.ingsis.clientserver.*
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder
import edu.austral.ingsis.clientserver.util.MessageCollectorListener
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.net.InetSocketAddress
import kotlin.test.Test
import kotlin.test.assertEquals


class NettyServerTransmissionTest {

    companion object {
        private const val HOST = "localhost"
        const val PORT = 10_000

        val ADDRESS = InetSocketAddress(HOST, PORT)

        private val DATA_MESSAGE_TYPE = "data-type"
        private val RAW_MESSAGE_TYPE = "raw-type"
        private val MOVE_MESSAGE_TYPE = "move-type"
    }

    // Collectors
//    private val serverDataCollector = MessageCollectorListener<S>()
//    private val client1DataCollector = MessageCollectorListener<Game>()
//    private val client2DataCollector = MessageCollectorListener<Game>()
//    private val client3DataCollector = MessageCollectorListener<Game>()

    private val serverRawCollector = MessageCollectorListener<String>()
    private val client1RawCollector = MessageCollectorListener<String>()
    private val client2RawCollector = MessageCollectorListener<String>()


    private var typeReferenceChess: TypeReference<Message<Adapter>> =
        object : TypeReference<Message<Adapter>>() {}

    private var reference: TypeReference<Message<String>> =
        object : TypeReference<Message<String>>() {}

    // Server
    private val server: Server = createServerBuilder()
//        .addMessageListener(DATA_MESSAGE_TYPE, typeReferenceChess, serverDataCollector)
        .addMessageListener(RAW_MESSAGE_TYPE, reference, serverRawCollector)
        .build()

    // Clients
    private val client1: Client = createClientBuilder()
//        .addMessageListener(DATA_MESSAGE_TYPE, typeReferenceChess, client1DataCollector)
        .addMessageListener(RAW_MESSAGE_TYPE, reference, client1RawCollector)
        .build()

    private val client2: Client = createClientBuilder()
        .addMessageListener(RAW_MESSAGE_TYPE, reference, client2RawCollector)
        .build()
//
//    private val client3: Client = createClientBuilder()
//        .addMessageListener(DATA_MESSAGE_TYPE, typeReferenceChess, client3DataCollector)
//        .build()

    private fun createServerBuilder(): ServerBuilder =
        NettyServerBuilder.createDefault()
            .withPort(NettyServerConnectionTest.PORT)

    private fun createClientBuilder(): ClientBuilder =
        NettyClientBuilder.createDefault()
            .withAddress(ADDRESS)

    @BeforeEach
    fun init() {
        // Start server
        server.start()

        // Connect clients
        client1.connect()
        client2.connect()
//        client3.connect()

        // Clear collectors
//        serverDataCollector.clear()
//        client1DataCollector.clear()
//        client2DataCollector.clear()
//        client3DataCollector.clear()

        serverRawCollector.clear()
        client1RawCollector.clear()
        client2RawCollector.clear()

    }

    @AfterEach
    fun closeConnections() {
        client1.closeConnection()
//        client2.closeConnection()
//        client3.closeConnection()

        server.stop()
    }

    @Test
    fun `send string message from client to server`() {
        // Send message
        val message = Message(RAW_MESSAGE_TYPE, "Hello!")
        server.broadcast(message)

        // Waits for message to arrive
        Thread.sleep(200)

        assertEquals(1, client2RawCollector.messages.size)

        assertEquals(1, client1RawCollector.messages.size)
    }

//    @Test
//    fun `send object message from client to server`() {
//        val message = Message(
//            DATA_MESSAGE_TYPE, Game(PlayerColor.BLACK,Adapter(ChessNextColor(), listOf(
//            CheckmateValidator(maxRow, maxCol), NoOppPiecesValidator()
//        ), maxCol, maxRow, pieces)))
//        server.broadcast(message)
//
//        Thread.sleep(200)
//
////        print(serverDataCollector.messages.first())
//
////        server.broadcast(Message(DATA_MESSAGE_TYPE, messageReceived))
//        // Waits for message to arrive
//
//        assertEquals(0, serverDataCollector.messages.size)
//        assertEquals(1, client1DataCollector.messages.size)
////        assertEquals(message.payload, serverDataCollector.messages.first())
//
////        assertEquals(message.payload, client2DataCollector.messages.first())
//    }

//    @Test
//    fun `send object broadcast to clients`() {
//        // Send message
//        val message = Message(DATA_MESSAGE_TYPE, Data("Hello!"))
//        server.broadcast(message)
//
//        // Waits for message to arrive
//        Thread.sleep(200)
//
//        assertEquals(0, serverDataCollector.messages.size)
//
//        assertEquals(1, client1DataCollector.messages.size)
//        assertEquals(message.payload, client1DataCollector.messages.first())
//        assertEquals(1, client2DataCollector.messages.size)
//        assertEquals(message.payload, client2DataCollector.messages.first())
//        assertEquals(1, client3DataCollector.messages.size)
//        assertEquals(message.payload, client3DataCollector.messages.first())
//    }
//
//    @Test
//    fun `send several messages between client and sever`() {
//        val message1 = Message(DATA_MESSAGE_TYPE, Data("message-1"))
//        val message2 = Message(DATA_MESSAGE_TYPE, Data("message-2"))
//        val message3 = Message(DATA_MESSAGE_TYPE, Data("message-3"))
//        val message4 = Message(DATA_MESSAGE_TYPE, Data("message-4"))
//
//        // Send message
//        client1.send(message1)
//        server.broadcast(message2)
//        client1.send(message3)
//        server.broadcast(message4)
//
//        // Waits for message to arrive
//        Thread.sleep(300)
//
//        assertEquals(2, serverDataCollector.messages.size)
//        assertContains(serverDataCollector.messages, message1.payload)
//        assertContains(serverDataCollector.messages, message3.payload)
//
//        assertEquals(2, client1DataCollector.messages.size)
//        assertContains(client1DataCollector.messages, message2.payload)
//        assertContains(client1DataCollector.messages, message4.payload)
//    }
//
//    @Test
//    fun `send several raw message between client and server`(){
//        val message1 = Message(RAW_MESSAGE_TYPE, "message-1")
//        val message2 = Message(RAW_MESSAGE_TYPE, "message-2")
//        client1.send(message1)
//        server.broadcast(message2)
//        Thread.sleep(300)
//
//        assertEquals(1, serverRawCollector.messages.size)
//        assertContains(serverRawCollector.messages, message1.payload)
//
//        assertEquals(1, client1RawCollector.messages.size)
//        assertContains(client1RawCollector.messages, message2.payload)
//
//        assertEquals(0, client2DataCollector.messages.size)
//    }
}
