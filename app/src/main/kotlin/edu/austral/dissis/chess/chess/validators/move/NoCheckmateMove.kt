package edu.austral.dissis.chess.chess.validators.move

import edu.austral.dissis.chess.chess.validators.MovementValidator
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
import edu.austral.dissis.chess.chess.validators.result.ValidResult
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult
import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.PieceColor
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.common.victoryValidators.CheckmateValidator

class NoCheckmateMove : MovementValidator {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val checkmateValidator = CheckmateValidator()
        val result = checkmateValidator.validateVictory(applyMove(from, to, pieces), getOpponentColor(pieces.get(from)!!.color))
        if( result.isOver()) return InvalidResult()
        return ValidResult()
    }

    private fun applyMove(from: Square, to: Square, pieces: Map<Square, Piece>): Map<Square, Piece> {
        val piece : Piece = pieces[from] ?: return emptyMap()
        return pieces.filter { (square, _) ->  square != from} + (to to piece)
    }

    private fun getOpponentColor(currentColor: PieceColor): PieceColor{
        return if(currentColor == PieceColor.WHITE) PieceColor.BLACK
        else PieceColor.WHITE
    }
}