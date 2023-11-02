package edu.austral.dissis.chess.chess.validators.obstacle
import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.common.validators.result.InvalidResult
import edu.austral.dissis.chess.common.validators.result.ValidResult
import edu.austral.dissis.chess.common.validators.result.ValidatorResult
import edu.austral.dissis.chess.common.validators.obstacle.ObstaclePiece
import kotlin.math.abs

class ObsVerticalMove: ObstaclePiece() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        var vertical = quantitySquaresBtw(from.vertical, to.vertical)
        var square: Square
        while (abs(vertical) > 0) {
            square = Square(from.vertical + vertical, from.horizontal)

            if(isObstacle(square, pieces)) return InvalidResult()

            if(vertical < 0) vertical++
            else vertical--
        }
        return ValidResult()
    }
}