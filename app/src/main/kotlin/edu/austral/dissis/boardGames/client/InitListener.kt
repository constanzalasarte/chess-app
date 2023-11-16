package edu.austral.dissis.boardGames.client

import edu.austral.dissis.chess.gui.InitialState
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class InitListener(private val client: Client) : MessageListener<InitialState>{
    override fun handleMessage(message: Message<InitialState>){
        client.handleInit(message.payload)
    }

}