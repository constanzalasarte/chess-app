package edu.austral.dissis.chess.chess.validators.obstacle


import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
import edu.austral.dissis.chess.chess.validators.result.ValidResult
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult
import kotlin.math.abs

class ObsDiagonalMove: ObstaclePiece(){
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        var horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        var vertical = quantitySquaresBtw(from.vertical, to.vertical)
        var square: Square

        // I only check if horizontal is greater that 0, because vertical should be the same number
        while (abs(horizontal) > 0) {
            square = Square(from.vertical + vertical, from.horizontal + horizontal)

            if(isObstacle(square, pieces)) return InvalidResult()

            if(horizontal < 0){
                horizontal++
                vertical++
            }
            else{
                horizontal--
                vertical--
            }
        }
        return ValidResult()
    }
}