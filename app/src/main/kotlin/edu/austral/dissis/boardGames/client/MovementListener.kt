package edu.austral.dissis.boardGames.client

import edu.austral.dissis.chess.gui.GameEventListener
import edu.austral.dissis.chess.gui.Move

class MovementListener(val client: Client): GameEventListener {

    override fun handleMove(move: Move) {
        client.sendMove(move)
    }

}