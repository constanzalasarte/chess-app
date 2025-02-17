/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package edu.austral.dissis.boardGames

import edu.austral.dissis.boardGames.checkers.CheckersNextColor
import edu.austral.dissis.boardGames.common.Game
import edu.austral.dissis.boardGames.chess.boarderReader.BoarderReader
import edu.austral.dissis.boardGames.chess.createValidationEngine.ClassicEngine
import edu.austral.dissis.boardGames.common.validators.EdgeSquare
import edu.austral.dissis.boardGames.common.ChessPiece
import edu.austral.dissis.boardGames.common.validationEngine.GraphNode
import edu.austral.dissis.boardGames.common.validationEngine.ValidationEngine
import edu.austral.dissis.boardGames.chess.victoryValidators.CheckmateValidator
import edu.austral.dissis.boardGames.checkers.victoryValidators.NoOppPiecesValidator
import edu.austral.dissis.boardGames.checkers.victoryValidators.createValidationEngine.ClassicEngineCheckers
import edu.austral.dissis.boardGames.chess.ChessNextColor
import edu.austral.dissis.chess.gui.*
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.stage.Stage


fun main() {
//    launch(edu.austral.dissis.boardGames.ChessGameApplication::class.java)
    launch(CheckersGameApplication::class.java)
}

class ChessGameApplication : Application() {
    private val maxRow = 8
    private val maxCol = 8
    private val classicEngine = ClassicEngine(maxRow, maxCol)
    private val enginePieces: Map<ChessPiece, ValidationEngine> = mapOf(
        ChessPiece.KING to classicEngine.kingEngine(),
        ChessPiece.QUEEN to classicEngine.queenEngine(),
        ChessPiece.ROOK to classicEngine.rookEngine(),
        ChessPiece.BISHOP to classicEngine.bishopEngine(),
        ChessPiece.HORSE to classicEngine.horseEngine(),
        ChessPiece.PAWN to classicEngine.pawnEngine(),
        ChessPiece.CHANCELLOR to GraphNode(EdgeSquare(maxRow, maxCol), listOf( classicEngine.horseEngine(), classicEngine.rookEngine())),
        ChessPiece.ARCHBISHOP to GraphNode(EdgeSquare(maxRow, maxCol), listOf( classicEngine.horseEngine(), classicEngine.bishopEngine())),
        )
    private val boarderReader = BoarderReader(enginePieces)
//    private val checkmatePieces = boarderReader.boarderReader("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/chess/chess/checkmate.txt", ClassicEngine(), chessPieces)
//    private val noMoreOpponentPieces = boarderReader.boarderReader("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/chess/chess/NoMoreOppPieces", ClassicEngine(), chessPieces)
    private val pieces = boarderReader.getMap("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/boardGames/chess/beginning")
    private val adapter = Game(pieces, PlayerColor.BLACK, maxCol, listOf(CheckmateValidator(), NoOppPiecesValidator()), ChessNextColor())
    private val imageResolver = CachedImageResolver(DefaultImageResolver())

    companion object {
        const val GameTitle = "Chess"
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = GameTitle

        val root = GameView(adapter, imageResolver)
        primaryStage.scene = Scene(root)

        primaryStage.show()
    }
}

class CheckersGameApplication : Application() {
    private val classicEngine = ClassicEngineCheckers()
    private val enginePieces: Map<ChessPiece, ValidationEngine> = mapOf(
        ChessPiece.PAWN to classicEngine.basicEngine(),
    )
    private val boarderReader = BoarderReader(enginePieces)
    //    private val checkmatePieces = boarderReader.boarderReader("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/chess/checkmate.txt", ClassicEngine(), chessPieces)
//    private val noMoreOpponentPieces = boarderReader.boarderReader("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/chess/NoMoreOppPieces", ClassicEngine(), chessPieces)
    private val pieces = boarderReader.getMap("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/boardGames/checkers/beginning")
    private val adapter = Game(pieces, PlayerColor.BLACK, 8, listOf(NoOppPiecesValidator()), CheckersNextColor())
    private val imageResolver = CachedImageResolver(DefaultImageResolver())

    companion object {
        const val GameTitle = "Chess"
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = GameTitle

        val root = GameView(adapter, imageResolver)
        primaryStage.scene = Scene(root)

        primaryStage.show()
    }
}