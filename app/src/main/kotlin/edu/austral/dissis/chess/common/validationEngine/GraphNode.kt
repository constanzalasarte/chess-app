package edu.austral.dissis.chess.common.validationEngine

import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.chess.validators.MovementValidator
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
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

    private fun checkNodes(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        var result : ValidatorResult = InvalidResult()
        for (graph in graphNodes) {
            val validatorResult = graph.move(from, to, pieces)
            if (validatorResult.isValid()) result = graph.move(from, to, pieces)
        }
        return result
    }
}