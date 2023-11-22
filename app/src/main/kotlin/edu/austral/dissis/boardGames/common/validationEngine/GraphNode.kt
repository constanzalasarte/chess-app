package edu.austral.dissis.boardGames.common.validationEngine

import edu.austral.dissis.boardGames.chess.validators.result.ValidWExecutionResult
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.MovementValidator
import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult


class GraphNode(
    private val movementValidator: MovementValidator,
    private val graphNodes: List<
            ValidationEngine>
    ): ValidationEngine {

    override fun move(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val validatorResult = movementValidator.validateMove(from, to, pieces)
        if (validatorResult.isValid()) {
            val result = if (graphNodes.isNotEmpty()) checkNodes(from, to, pieces, validatorResult)
            else validatorResult
            return if(!result.isValid()) result
            else if(validWExecution(result, validatorResult)) validatorResult
            else result
        }
        return InvalidResult()
    }

    private fun checkNodes(from: Square, to: Square, pieces: Map<Square, Piece>, validatorResult: ValidatorResult): ValidatorResult {
        var result : ValidatorResult = InvalidResult()
        for (graph in graphNodes) {
            val valResult = graph.move(from, to, pieces)
            if(checkValid(result, valResult)) result = valResult
        }
        return result
    }

    private fun checkValid(result: ValidatorResult, valResult: ValidatorResult): Boolean {
        return valResult.isValid() && result !is ValidWExecutionResult
    }

    private fun validWExecution(result: ValidatorResult, valResult: ValidatorResult): Boolean {
        return result !is ValidWExecutionResult && valResult is ValidWExecutionResult
    }
}