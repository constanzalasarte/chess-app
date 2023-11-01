package edu.austral.dissis.chess.chess.validators.obstacle


import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
import edu.austral.dissis.chess.chess.validators.result.ValidResult
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult
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