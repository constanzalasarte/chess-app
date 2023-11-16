package edu.austral.dissis.boardGames.server

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.boardGames.common.Game
import edu.austral.dissis.chess.gui.*
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.Server
import edu.austral.ingsis.clientserver.ServerBuilder
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder

class Server(
    val game: Game,
    val serverBuilder: ServerBuilder = NettyServerBuilder.createDefault()) {
    private var server : Server
    init {
        server = build()
        server.start()
    }

    private fun build(): Server {
        return serverBuilder
            .withPort(8080)
            .addMessageListener(
                "init",
                object : TypeReference<Message<Unit>>() {},
                InitListener(this)
            )
            .addMessageListener(
                "move",
                object : TypeReference<Message<Move>>() {},
                MoveListener(this))
            .build()
    }

    fun handleMove(move: Move) {
        val result : MoveResult = game.applyMove(move)
        when (result) {
            is InvalidMove -> server.broadcast(Message("invalidMove", result))
            is NewGameState -> server.broadcast(Message("newGameState", result))
            is GameOver -> server.broadcast(Message("gameOver", result))
        }
    }

    fun handleInit() {
        server.broadcast(Message("init", game.init()))
    }
    fun stop() = server.stop()
}