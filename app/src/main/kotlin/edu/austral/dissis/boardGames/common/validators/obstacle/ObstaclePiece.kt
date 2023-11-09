package edu.austral.dissis.boardGames.common.validators.obstacle

import edu.austral.dissis.boardGames.common.validators.MovementValidator
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square

abstract class ObstaclePiece: MovementValidator {
    fun isObstacle(square: Square, pieces: Map<Square, Piece>): Boolean{
        return pieces.keys.any{it == square && pieces[it] is Piece }
    }
    fun quantitySquaresBtw(from: Int, to: Int): Int {
        val difference = to - from
        return if(difference<0) difference + 1
        else difference - 1
    }
}