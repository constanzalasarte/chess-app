package edu.austral.dissis.boardGames.client

import edu.austral.dissis.chess.gui.GameOver
import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.dissis.chess.gui.NewGameState
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class InvalidMoveListener(val client: Client): MessageListener<InvalidMove> {
    override fun handleMessage(message: Message<InvalidMove>) {
        client.handleMoveResult(message.payload)
    }
}

class NewGameStateListener(val client: Client): MessageListener<NewGameState> {
    override fun handleMessage(message: Message<NewGameState>) {
        client.handleMoveResult(message.payload)
    }
}

class GameOverListener(val client: Client): MessageListener<GameOver> {
    override fun handleMessage(message: Message<GameOver>) {
        client.handleMoveResult(message.payload)
    }
}