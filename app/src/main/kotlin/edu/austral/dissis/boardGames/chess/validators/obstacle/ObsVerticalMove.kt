package edu.austral.dissis.boardGames.chess.validators.obstacle
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.validators.obstacle.ObstaclePiece
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