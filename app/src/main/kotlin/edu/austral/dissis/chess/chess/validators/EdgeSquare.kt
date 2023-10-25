package edu.austral.dissis.chess.chess.validators

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square


class EdgeSquare(private val maxRow: Int, private val maxColumn: Int): MovementValidator {
    
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>):ValidatorResult{
        if(validateVertical(to.vertical) && validateHorizontal(to.horizontal)) return ValidatorResult(TypeResult.VALID)
        return ValidatorResult(TypeResult.INVALID)
    }
    private fun validateVertical(vertical: Int) : Boolean{
        return (vertical >= 0) && (vertical < maxRow)
    }
    private fun validateHorizontal(horizontal: Int) : Boolean{
        return (horizontal >= 0) && (horizontal < maxColumn)
    }
}