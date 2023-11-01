package edu.austral.dissis.chess.chess.validationEngine

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.MovementValidator
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
import edu.austral.dissis.chess.chess.validators.result.ValidResult
import edu.austral.dissis.chess.chess.validators.result.ValidWExecutionResult
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult


class GraphNode(
    private val movementValidator: MovementValidator,
    private val graphNodes: List<
            ValidationEngine>
    ): ValidationEngine {

    override fun move(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val validatorResult = movementValidator.validateMove(from, to, pieces)
        if (validatorResult.isValid()) {
            return if (graphNodes.isNotEmpty()) checkNodes(from, to, pieces)
            else validatorResult
        }
        return InvalidResult()
    }

    private fun checkNodes(from: Square, to: Square , pieces: Map<Square, Piece>): ValidatorResult {
        var result : ValidatorResult = InvalidResult()
        for (graph in graphNodes) {
            val validatorResult = graph.move(from, to, pieces)
            if (validatorResult.isValid()) result = graph.move(from, to, pieces)
        }
        return result
    }
}