package edu.austral.dissis.chess.chess.validators

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
import edu.austral.dissis.chess.chess.validators.result.ValidResult
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult


class EdgeSquare(private val maxRow: Int, private val maxColumn: Int): MovementValidator {
    
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        if(validateVertical(to.vertical) && validateHorizontal(to.horizontal)) return ValidResult()
        return InvalidResult()
    }
    private fun validateVertical(vertical: Int) : Boolean{
        return (vertical >= 0) && (vertical < maxRow)
    }
    private fun validateHorizontal(horizontal: Int) : Boolean{
        return (horizontal >= 0) && (horizontal < maxColumn)
    }
}