package edu.austral.dissis.boardGames.chess.validators.obstacle

import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.obstacle.ObstaclePiece
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

            vertical = incrementCoordinate(vertical)
            horizontal = incrementCoordinate(horizontal)
        }
        return ValidResult()
    }
    private fun incrementCoordinate(coordinate: Int): Int{
        if (coordinate < 0) return coordinate + 1
        return coordinate - 1

    }
}