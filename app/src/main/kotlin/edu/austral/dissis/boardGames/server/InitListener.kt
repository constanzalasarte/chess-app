package edu.austral.dissis.boardGames.server

import edu.austral.dissis.chess.gui.InitialState
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class InitListener(private val server: Server) : MessageListener<Unit>{
    override fun handleMessage(message: Message<Unit>) {
        server.handleInit()
    }
}