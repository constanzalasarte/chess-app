package edu.austral.dissis.boardGames.server

import edu.austral.dissis.boardGames.checkers.CheckersNextColor
import edu.austral.dissis.boardGames.checkers.victoryValidators.NoOppPiecesValidator
import edu.austral.dissis.boardGames.checkers.victoryValidators.createValidationEngine.ClassicEngineCheckers
import edu.austral.dissis.boardGames.chess.ChessNextColor
import edu.austral.dissis.boardGames.chess.createValidationEngine.ClassicEngine
import edu.austral.dissis.boardGames.chess.victoryValidators.CheckmateValidator
import edu.austral.dissis.boardGames.common.Adapter
import edu.austral.dissis.boardGames.common.ChessPiece
import edu.austral.dissis.boardGames.common.Game
import edu.austral.dissis.boardGames.common.boarderReader.BoarderReader
import edu.austral.dissis.boardGames.common.validationEngine.GraphNode
import edu.austral.dissis.boardGames.common.validationEngine.ValidationEngine
import edu.austral.dissis.boardGames.common.validators.EdgeSquare
import edu.austral.dissis.chess.gui.PlayerColor

fun main(){
//    val setUp = classicChess()
    val setUp = classicCheckers()
    Server(setUp)
}

fun classicChess(): Game {
    val maxRow = 8
    val maxCol = 8
    val classicEngine = ClassicEngine(maxRow, maxCol)
    val enginePieces: Map<ChessPiece, ValidationEngine> = mapOf(
        ChessPiece.KING to classicEngine.kingEngine(),
        ChessPiece.QUEEN to classicEngine.queenEngine(),
        ChessPiece.ROOK to classicEngine.rookEngine(),
        ChessPiece.BISHOP to classicEngine.bishopEngine(),
        ChessPiece.HORSE to classicEngine.horseEngine(),
        ChessPiece.PAWN to classicEngine.pawnEngine(),
        ChessPiece.CHANCELLOR to GraphNode(EdgeSquare(maxRow, maxCol), listOf( classicEngine.horseEngine(), classicEngine.rookEngine())),
        ChessPiece.ARCHBISHOP to GraphNode(EdgeSquare(maxRow, maxCol), listOf( classicEngine.horseEngine(), classicEngine.bishopEngine())),
    )
    val boarderReader = BoarderReader(enginePieces)
//    private val checkmatePieces = boarderReader.boarderReader("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/chess/chess/checkmate.txt", ClassicEngine(), chessPieces)
    val pieces = boarderReader.getMap("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/boardGames/chess/NoMoreOppPieces")
//    val pieces = boarderReader.getMap("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/boardGames/chess/beginning")
//    val pieces = boarderReader.getMap("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/boardGames/chess/castle.txt")
    return Game(PlayerColor.BLACK,
        Adapter(ChessNextColor(), listOf(CheckmateValidator(maxRow, maxCol), NoOppPiecesValidator()), maxCol, maxRow, pieces)
    )
}

fun classicCheckers(): Game{
    val maxRow = 8
    val maxCol = 8
    val classicEngine = ClassicEngineCheckers()
    val enginePieces: Map<ChessPiece, ValidationEngine> = mapOf(
        ChessPiece.PAWN to classicEngine.basicEngine(),
    )
    val boarderReader = BoarderReader(enginePieces)
//    val pieces = boarderReader.getMap("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/boardGames/checkers/beginning")
    val pieces = boarderReader.getMap("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/boardGames/checkers/eat2Pieces")
    return Game(PlayerColor.WHITE, Adapter(CheckersNextColor(), listOf(NoOppPiecesValidator()), maxCol, maxRow, pieces))
}