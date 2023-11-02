package edu.austral.dissis.chess.checkers.victoryValidators.createValidationEngine

import edu.austral.dissis.chess.checkers.validators.obstacle.ObsDiagonalMoveCheckers
import edu.austral.dissis.chess.common.validationEngine.GraphNode
import edu.austral.dissis.chess.common.validationEngine.ValidationEngine
import edu.austral.dissis.chess.common.validators.*
import edu.austral.dissis.chess.common.validators.move.DiagonalMove
import edu.austral.dissis.chess.common.validators.move.MoveType
import edu.austral.dissis.chess.common.validators.move.QuantityOfMoves

class ClassicEngineCheckers {
    fun basicEngine(): ValidationEngine {
        val obsDiagonalMove: ValidationEngine = GraphNode(ObsDiagonalMoveCheckers(), listOf())
        val diagonalMove2 : ValidationEngine = GraphNode(QuantityOfMoves(2, MoveType.VERTICAL), listOf(obsDiagonalMove))
        val diagonalMove1 : ValidationEngine = GraphNode(QuantityOfMoves(1, MoveType.VERTICAL), listOf())
        val checkDestinationSquare: ValidationEngine = GraphNode(EmptyDestinationSquare(), listOf(diagonalMove1, diagonalMove2))
        val diagonalMove: ValidationEngine = GraphNode(DiagonalMove(), listOf(checkDestinationSquare))
        return initialEngine(listOf(diagonalMove))
    }
    private fun initialEngine(validationEngines: List<ValidationEngine>): ValidationEngine {
        val edgeSquare = GraphNode(EdgeSquare(8, 8), validationEngines)
        return GraphNode(CheckOriginSquare(), listOf(edgeSquare))
    }
}