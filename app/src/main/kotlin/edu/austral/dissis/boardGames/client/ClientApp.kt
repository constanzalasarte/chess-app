package edu.austral.dissis.boardGames.client

import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import edu.austral.dissis.chess.gui.GameView
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.stage.Stage

fun main(){
    launch(ClientApp::class.java)
}

class ClientApp : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())

    companion object {
        const val GameTitle = "Game"
        val client = Client()
    }
    override fun start(primaryStage: Stage) {
        primaryStage.title = GameTitle
        val root = GameView(imageResolver)
        primaryStage.scene = Scene(root)
        client.start(root)
        primaryStage.show()
    }
}