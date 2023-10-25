package edu.austral.dissis.chess.chess.validators.move
import edu.austral.dissis.chess.chess.validators.MovementValidator

abstract class MoveValidator: MovementValidator {
    fun quantitySquaresBtw(from: Int, to: Int): Int {
        return to - from
    }

}