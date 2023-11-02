package edu.austral.dissis.chess.chess.validators.move
import edu.austral.dissis.chess.chess.validators.MovementValidator
import edu.austral.dissis.chess.common.PieceColor

abstract class MoveValidator: MovementValidator {
    fun quantitySquaresBtw(from: Int, to: Int): Int {
        return to - from
    }
    fun checkIncrementByColor(vertical: Int, pieceColor: PieceColor): Boolean{
        if(pieceColor == PieceColor.BLACK) return vertical>0
        return vertical<0
    }

}