package edu.austral.dissis.boardGames.chess.createValidationEngine

import com.sun.javafx.geom.Edge
import edu.austral.dissis.boardGames.common.validators.CheckOriginSquare
import edu.austral.dissis.boardGames.common.validationEngine.GraphNode
import edu.austral.dissis.boardGames.common.validationEngine.ValidationEngine
import edu.austral.dissis.boardGames.chess.validators.*
import edu.austral.dissis.boardGames.chess.validators.move.*
import edu.austral.dissis.boardGames.chess.validators.obstacle.ObsDiagonalMove
import edu.austral.dissis.boardGames.chess.validators.obstacle.ObsHorizontalMove
import edu.austral.dissis.boardGames.chess.validators.obstacle.ObsVerticalMove
import edu.austral.dissis.boardGames.common.validators.*
import edu.austral.dissis.boardGames.common.validators.move.DiagonalMove
import edu.austral.dissis.boardGames.common.validators.move.MoveType
import edu.austral.dissis.boardGames.common.validators.move.QuantityOfMoves

class ClassicEngine(val maxRow: Int, val maxCol: Int): Engine {
    override fun pawnEngine(): ValidationEngine {
        val diagonalMove1 : ValidationEngine = GraphNode(QuantityOfMoves(1, MoveType.VERTICAL), listOf(finalEngine()))
        val checkEnemy: ValidationEngine = GraphNode(CheckEnemy(), listOf(diagonalMove1))
        val obsDiagonalMove: ValidationEngine = GraphNode(ObsDiagonalMove(), listOf(checkEnemy))
        val diagonalMove: ValidationEngine = GraphNode(DiagonalMove(), listOf(obsDiagonalMove))

        val pieceMove0 : ValidationEngine = GraphNode(QuantityOfPieceMove(0), listOf(finalEngine()))
        val verticalMove2 : ValidationEngine = GraphNode(QuantityOfMoves(2, MoveType.VERTICAL), listOf(pieceMove0))
        val verticalMove1 : ValidationEngine = GraphNode(QuantityOfMoves(1, MoveType.VERTICAL), listOf(finalEngine()))
        val checkDestination: ValidationEngine = GraphNode(EmptyDestinationSquare(), listOf(verticalMove1, verticalMove2))
        val obsVerticalMove: ValidationEngine = GraphNode(ObsVerticalMove(), listOf(checkDestination))
        val verticalMove: ValidationEngine = GraphNode(VerticalMove(), listOf(obsVerticalMove))

        return initialEngine(listOf(verticalMove, diagonalMove))
    }
    override fun horseEngine(): ValidationEngine{
        val noFriendInDestinationSquare : ValidationEngine = GraphNode(CheckDestinationSquare(), listOf(finalEngine()))
        val LMove : ValidationEngine = GraphNode(LMove(), listOf(noFriendInDestinationSquare))
        return initialEngine(listOf(LMove))
    }
    override fun bishopEngine(): ValidationEngine{
        val noFriendInDestinationSquare : ValidationEngine = GraphNode(CheckDestinationSquare(), listOf(finalEngine()))
        val obsDiagonalMove : ValidationEngine = GraphNode(ObsDiagonalMove(), listOf(noFriendInDestinationSquare))
        val diagonalMove : ValidationEngine = GraphNode(DiagonalMove(), listOf(obsDiagonalMove))
        return initialEngine(listOf(diagonalMove))
    }
    override fun rookEngine(): ValidationEngine {
        val obsHorizontalMove: ValidationEngine = GraphNode(ObsHorizontalMove(), listOf(finalEngine()))
        val horizontalMove: ValidationEngine = GraphNode(HorizontalMove(), listOf(obsHorizontalMove))

        val obsVerticalMove: ValidationEngine = GraphNode(ObsVerticalMove(), listOf(finalEngine()))
        val verticalMove: ValidationEngine = GraphNode(VerticalMove(), listOf(obsVerticalMove))
        val noFriendInDestinationSquare: ValidationEngine = GraphNode(CheckDestinationSquare(), listOf(verticalMove, horizontalMove))

        return initialEngine(listOf(noFriendInDestinationSquare))
    }
    override fun queenEngine(): ValidationEngine {
        val obsHorizontalMove: ValidationEngine = GraphNode(ObsHorizontalMove(), listOf(finalEngine()))
        val horizontalMove: ValidationEngine = GraphNode(HorizontalMove(), listOf(obsHorizontalMove))

        val obsVerticalMove: ValidationEngine = GraphNode(ObsVerticalMove(), listOf(finalEngine()))
        val verticalMove: ValidationEngine = GraphNode(VerticalMove(), listOf(obsVerticalMove))

        val obsDiagonalMove : ValidationEngine = GraphNode(ObsDiagonalMove(), listOf(finalEngine()))
        val diagonalMove : ValidationEngine = GraphNode(DiagonalMove(), listOf(obsDiagonalMove))
        val noFriendInDestinationSquare : ValidationEngine = GraphNode(CheckDestinationSquare(), listOf(diagonalMove, verticalMove, horizontalMove  ))
        return initialEngine(listOf(noFriendInDestinationSquare))
    }
    override fun kingEngine(): ValidationEngine {
        val verticalMove1 = getQuantityMoveGraph(1, MoveType.VERTICAL)
        val verticalMove: ValidationEngine = GraphNode(VerticalMove(), listOf(verticalMove1))
        val diagonalMove: ValidationEngine = GraphNode(DiagonalMove(), listOf(verticalMove1))

        val validateCastle : ValidationEngine = GraphNode(ValidateCastle(), listOf(finalEngine()))
        val checkPieceMove0 : ValidationEngine = GraphNode(QuantityOfPieceMove(0), listOf(validateCastle))
        val horizontalMove1 = getQuantityMoveGraph(1, MoveType.HORIZONTAL)
        val horizontalMove2 = getQuantityMoveGraph(2, MoveType.HORIZONTAL, list = listOf(checkPieceMove0))
        val horizontalMove: ValidationEngine = GraphNode(HorizontalMove(), listOf(horizontalMove1, horizontalMove2))

        val noFriendInDestinationSquare : ValidationEngine = GraphNode(CheckDestinationSquare(), listOf(verticalMove, diagonalMove, horizontalMove))
        return initialEngine(listOf(noFriendInDestinationSquare))
    }

    private fun initialEngine(validationEngines: List<ValidationEngine>): ValidationEngine {
        val edgeSquare = GraphNode(EdgeSquare(8, 8), validationEngines)
        return GraphNode(CheckOriginSquare(), listOf(edgeSquare))
    }

    private fun finalEngine(): ValidationEngine{
//        return GraphNode(NoCheckmateMove(), listOf())
        return GraphNode(CheckOriginSquare(), listOf())
    }

    private fun getQuantityMoveGraph(quantity: Int, moveType: MoveType, list: List<ValidationEngine> = listOf(finalEngine())): ValidationEngine{
        return GraphNode(QuantityOfMoves(quantity, moveType), list)
    }

}