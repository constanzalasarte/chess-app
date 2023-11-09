package edu.austral.dissis.boardGames.chess.validators.obstacle


import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.validators.obstacle.ObstaclePiece
import kotlin.math.abs

class ObsHorizontalMove: ObstaclePiece(){
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        var horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        var square: Square
        while (abs(horizontal) > 0) {
            square = Square(from.vertical, from.horizontal + horizontal)

            if(isObstacle(square, pieces)) return InvalidResult()

            if(horizontal < 0) horizontal++
            else horizontal--
        }
        return ValidResult()
    }
}