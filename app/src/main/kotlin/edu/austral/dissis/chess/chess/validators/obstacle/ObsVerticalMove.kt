package edu.austral.dissis.chess.chess.validators.obstacle
import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.TypeResult
import edu.austral.dissis.chess.chess.validators.ValidatorResult
import kotlin.math.abs

class ObsVerticalMove: ObstaclePiece() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        var vertical = quantitySquaresBtw(from.vertical, to.vertical)
        var square: Square
        while (abs(vertical) > 0) {
            square = Square(from.vertical + vertical, from.horizontal)

            if(isObstacle(square, pieces)) return ValidatorResult(TypeResult.INVALID)

            if(vertical < 0) vertical++
            else vertical--
        }
        return ValidatorResult(TypeResult.VALID)
    }
}