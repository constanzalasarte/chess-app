package edu.austral.dissis.chess.chess.validationEngine

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.MovementValidator
import edu.austral.dissis.chess.chess.validators.TypeResult
import edu.austral.dissis.chess.chess.validators.ValidatorResult


class GraphNode(
    private val movementValidator: MovementValidator,
    private val graphNodes: List<
            ValidationEngine>
    ): ValidationEngine {

    override fun move(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val validatorResult = movementValidator.validateMove(from, to, pieces)
        if(checkValidatorResult(validatorResult)){
            return if(graphNodes.isNotEmpty()) checkNodes(from, to, pieces)
            else validatorResult
        }
        return ValidatorResult(TypeResult.INVALID)
    }
    private fun checkValidatorResult(validatorResult: ValidatorResult): Boolean{
        return validatorResult.typeResult == TypeResult.VALID || validatorResult.typeResult == TypeResult.VALID_WITH_EXECUTION
    }
    private fun checkNodes(from: Square, to: Square , pieces: Map<Square, Piece>): ValidatorResult {
        var result = ValidatorResult(TypeResult.INVALID)
        var validatorResult : ValidatorResult
        for (graph in graphNodes) {
            validatorResult = graph.move(from, to, pieces)
            if (checkValidatorResult(validatorResult)) result = validatorResult
        }
        return result
    }
}