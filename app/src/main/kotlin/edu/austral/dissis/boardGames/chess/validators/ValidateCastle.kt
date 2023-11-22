package edu.austral.dissis.boardGames.chess.validators

import edu.austral.dissis.boardGames.common.ChessPiece
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.chess.validators.result.ValidWExecutionResult
import edu.austral.dissis.boardGames.common.validators.MovementValidator
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import kotlin.math.abs

class ValidateCastle : MovementValidator {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val squares = getRookSquares(from, pieces)
        if(squares.isEmpty()) return InvalidResult()
        return minSquareDistance(to, squares, pieces)
    }

    private fun getRookSquares(from: Square, pieces: Map<Square, Piece>): ArrayList<Square> {
        val availablePieces = ArrayList<Square>()

        for ((square, piece) in pieces.entries)
            if (checkPieceRookColorAndMoves(piece, pieces[from]!!))
                availablePieces.add(square)
        return availablePieces
    }
    private fun checkPieceRookColorAndMoves(piece: Piece, other: Piece): Boolean{
        return checkPieceRook(piece.chessPiece) && checkPieceColor(piece, other) && piece.moves == 0
    }
    private fun checkPieceRook(chessPiece: ChessPiece): Boolean{
        return chessPiece == ChessPiece.ROOK
    }
    private fun checkPieceColor(piece: Piece, other: Piece?): Boolean{
        return piece.color == other?.color
    }
    private fun minSquareDistance(to: Square, squares: List<Square>, map: Map<Square, Piece>): ValidatorResult {
        var minDistance = to.horizontal - squares[0].horizontal
        var minSquareDistance = squares[0]
        for(square in squares) {
            if(checkMinDistance(minDistance, to, square)){
                minDistance = to.horizontal - square.horizontal
                minSquareDistance = square
            }
        }
        val toSquare = Square(to.vertical, horizontalToSquare(minDistance, minSquareDistance))
        return ValidWExecutionResult(pieceId = map[minSquareDistance]!!.id, fromSquare = minSquareDistance, toSquare = toSquare)
    }

    private fun checkMinDistance(
        minDistance: Int,
        to: Square,
        square: Square
    ) = abs(minDistance) > abs(to.horizontal - square.horizontal)

    private fun horizontalToSquare(minDistance: Int, square: Square): Int {
        if(isPositive(minDistance)) return minDistance + 1
        return square.horizontal + minDistance - 1
    }

    private fun isPositive(minDistance: Int) = minDistance > 0
}