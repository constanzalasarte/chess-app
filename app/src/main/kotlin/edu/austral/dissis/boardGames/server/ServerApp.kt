package edu.austral.dissis.boardGames.server

import edu.austral.dissis.boardGames.checkers.CheckersNextColor
import edu.austral.dissis.boardGames.checkers.victoryValidators.NoOppPiecesValidator
import edu.austral.dissis.boardGames.checkers.victoryValidators.createValidationEngine.ClassicEngineCheckers
import edu.austral.dissis.boardGames.chess.ChessNextColor
import edu.austral.dissis.boardGames.chess.createValidationEngine.ClassicEngine
import edu.austral.dissis.boardGames.chess.victoryValidators.CheckmateValidator
import edu.austral.dissis.boardGames.Game
import edu.austral.dissis.boardGames.common.MyPiece
import edu.austral.dissis.boardGames.Adapter
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


fun classicChess(): Adapter {
    val maxRow = 10
    val maxCol = 8
    val classicEngine = ClassicEngine(maxRow, maxCol)
    val enginePieces: Map<MyPiece, ValidationEngine> = getPiecesWEngine(classicEngine, maxRow, maxCol)
    val boarderReader = BoarderReader(enginePieces)
    val pieces = boarderReader.getMap("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/boardGames/chess/castle.txt")
    return Adapter(PlayerColor.BLACK,
        Game(ChessNextColor(), listOf(CheckmateValidator(maxRow, maxCol), NoOppPiecesValidator()), maxCol, maxRow, pieces)
    )
}

private fun getPiecesWEngine(classicEngine: ClassicEngine, maxRow: Int, maxCol: Int) = mapOf(
    MyPiece.KING to classicEngine.kingEngine(),
    MyPiece.QUEEN to classicEngine.queenEngine(),
    MyPiece.ROOK to classicEngine.rookEngine(),
    MyPiece.BISHOP to classicEngine.bishopEngine(),
    MyPiece.HORSE to classicEngine.horseEngine(),
    MyPiece.PAWN to classicEngine.pawnEngine(),
    MyPiece.CHANCELLOR to GraphNode(
        EdgeSquare(maxRow, maxCol),
        listOf(classicEngine.horseEngine(), classicEngine.rookEngine())
    ),
    MyPiece.ARCHBISHOP to GraphNode(
        EdgeSquare(maxRow, maxCol),
        listOf(classicEngine.horseEngine(), classicEngine.bishopEngine())
    ),
)

fun classicCheckers(): Adapter {
    val maxRow = 8
    val maxCol = 8
    val classicEngine = ClassicEngineCheckers()
    val enginePieces: Map<MyPiece, ValidationEngine> = mapOf(
        MyPiece.PAWN to classicEngine.basicEngine(),
    )
    val boarderReader = BoarderReader(enginePieces)
    //val pieces = boarderReader.getMap("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/boardGames/checkers/beginning")
    val pieces = boarderReader.getMap("/home/constanza/projects/facu/system_design/chess-app/app/src/main/kotlin/edu/austral/dissis/boardGames/checkers/eat2Pieces")
    return Adapter(PlayerColor.WHITE, Game(CheckersNextColor(), listOf(NoOppPiecesValidator()), maxCol, maxRow, pieces))
}