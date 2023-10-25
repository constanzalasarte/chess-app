package edu.austral.dissis.chess.chess.validators.obstacle

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.MovementValidator

abstract class ObstaclePiece: MovementValidator{
    fun isObstacle(square: Square, pieces: Map<Square, Piece>): Boolean{
        return pieces.keys.any{it == square && pieces[it] is Piece}
    }
    fun quantitySquaresBtw(from: Int, to: Int): Int {
        val difference = to - from
        return if(difference<0) difference + 1
        else difference - 1
    }
}