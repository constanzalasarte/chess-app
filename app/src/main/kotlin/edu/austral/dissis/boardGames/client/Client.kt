package edu.austral.dissis.boardGames.client

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.chess.gui.*
import edu.austral.ingsis.clientserver.Client
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder
import java.net.InetSocketAddress

class Client {
    private lateinit var client: Client
    private lateinit var gameView: GameView
    fun start(newGameView: GameView) {
        gameView = newGameView
        client = build()
        client.connect()
        client.send(Message("init", Unit))
        gameView.addListener(MovementListener(this))
    }

    private fun build(): Client {
        return NettyClientBuilder.createDefault()
            .withAddress(InetSocketAddress(8080))
            .addMessageListener(
                "init",
                object : TypeReference<Message<InitialState>>() {},
                InitListener(this)
            )
            .addMessageListener(
                "invalidMove",
                object : TypeReference<Message<InvalidMove>>() {},
                InvalidMoveListener(this)
            )
            .addMessageListener(
                "newGameState",
                object : TypeReference<Message<NewGameState>>() {},
                NewGameStateListener(this)
            )
            .addMessageListener(
                "gameOver",
                object : TypeReference<Message<GameOver>>() {},
                GameOverListener(this)
            )
            .build()
    }

    fun handleInit(state: InitialState) {
        gameView.handleInitialState(state)
    }

    fun sendMove(move: Move) {
        client.send(Message("move", move))
        Thread.sleep(200)
    }

    fun handleMoveResult(moveResult: InvalidMove) {
        gameView.handleMoveResult(moveResult)
    }
    fun handleMoveResult(moveResult: GameOver) {
        gameView.handleMoveResult(moveResult)
        disconnect()
    }
    fun handleMoveResult(moveResult: NewGameState) {
        gameView.handleMoveResult(moveResult)
    }

    fun disconnect() {
        client.closeConnection()
    }


}