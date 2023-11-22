package edu.austral.dissis.boardGames.checkers.validators.obstacle

import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.obstacle.ObstaclePiece
import kotlin.math.abs

class ObsDiagonalMoveCheckers: ObstaclePiece(){
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        var horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        var vertical = quantitySquaresBtw(from.vertical, to.vertical)
        var square: Square

        // I only check if horizontal is greater that 0, because vertical should be the same number
        while (abs(horizontal) > 0) {
            square = Square(from.vertical + vertical, from.horizontal + horizontal)

            if(checkSquare(square, pieces, from)) return InvalidResult()

            vertical = incrementCoordinate(vertical)
            horizontal = incrementCoordinate(horizontal)
        }
        return ValidResult()
    }

    private fun checkSquare(
        square: Square,
        pieces: Map<Square, Piece>,
        from: Square
    ) = !isObstacle(square, pieces) && !isOpponentColor(square, pieces, from)

    private fun isOpponentColor(square: Square, pieces: Map<Square, Piece>, from: Square): Boolean {
        val pieceSquare = pieces.get(square)?.color ?: return false
        val pieceFrom = pieces.get(from)?.color ?: return false
        return pieceSquare != pieceFrom
    }

    private fun incrementCoordinate(coordinate: Int): Int{
        if (coordinate < 0) return coordinate + 1
        return coordinate - 1

    }
}