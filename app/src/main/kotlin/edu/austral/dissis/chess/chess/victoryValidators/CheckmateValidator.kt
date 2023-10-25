package edu.austral.dissis.chess.chess.victoryValidators

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.PieceColor
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.TypeResult
import edu.austral.dissis.chess.chess.victoryValidators.TypeVictoryResult
import edu.austral.dissis.chess.chess.victoryValidators.VictoryResult
import edu.austral.dissis.chess.chess.victoryValidators.VictoryValidator

/*
* A validator that checks if the current player has won by checkmate
* it should be run after each move
*/

class CheckmateValidator : VictoryValidator {
    override fun validateVictory(pieces: Map<Square, Piece>, currentPlayer: PieceColor): VictoryResult {
        val squares = getSquaresByColor(pieces, currentPlayer)
        val opponentSquares = getSquaresByColor(pieces, getOpponentColor(currentPlayer))
        if(opponentSquares.isEmpty()) return VictoryResult(TypeVictoryResult.NO_MORE_OPPONENT_PIECES)

        var oppIndex : Int
        var typeResult : TypeResult
        for (square in squares){
            oppIndex = 0
            while (opponentSquares.isNotEmpty() && oppIndex < opponentSquares.size){
                typeResult = getTypeResult(square, opponentSquares[oppIndex], pieces)
                if(typeResult == TypeResult.VALID || typeResult == TypeResult.VALID_WITH_EXECUTION)
                    opponentSquares.removeAt(oppIndex)
                else oppIndex++
            }
        }
        if(opponentSquares.isEmpty()) return VictoryResult(TypeVictoryResult.CHECKMATE)
        return VictoryResult(TypeVictoryResult.CONTINUE)
    }
    private fun getSquaresByColor(pieces: Map<Square, Piece>, color: PieceColor): ArrayList<Square> {
        val squares = ArrayList<Square>()
        for ((square, piece) in pieces)
            if(piece.color == color) squares.add(square)
        return squares
    }
    private fun getOpponentColor(currentPlayer: PieceColor): PieceColor{
        if(currentPlayer == PieceColor.BLACK) return PieceColor.WHITE
        return PieceColor.BLACK
    }
    private fun getTypeResult(from: Square, to: Square, pieces:Map<Square, Piece>): TypeResult{
        return pieces[from]!!.validationMapper.move(from, to, pieces).typeResult
    }
}