package edu.austral.dissis.chess.chess.victoryValidators

import edu.austral.dissis.chess.chess.ChessPiece
import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.PieceColor
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult

/*
* A validator that checks if the current player has won by checkmate
* it should be run after each move
*/

data class InitialState(val squares: List<Square>, val opponentSquares: List<Square>)

class CheckmateValidator : VictoryValidator {
    override fun validateVictory(pieces: Map<Square, Piece>, currentPlayer: PieceColor): VictoryResult {
        var state = getInitialState(pieces, currentPlayer);
        if(noMoreOpponentPieces(pieces, currentPlayer)) return NoMoreOpponentPieces()
        var oppIndex : Int
        var typeResult : ValidatorResult
        for (square in state.squares){
            oppIndex = 0
            while (state.opponentSquares.isNotEmpty() && oppIndex < state.opponentSquares.size){
                typeResult = getValidatorResult(square, state.opponentSquares[oppIndex], pieces)
                if(typeResult.isValid())
                    state = InitialState(state.squares, state.opponentSquares.filterIndexed { index, _ -> index != oppIndex })
                else oppIndex++
            }
        }
        if(state.opponentSquares.isEmpty()) return CheckmateResult()
        return ContinueResult()
    }

    private fun noMoreOpponentPieces(pieces: Map<Square, Piece>, currentPlayer: PieceColor): Boolean {
        return pieces
            .filter{ it.value.color == currentPlayer}
            .map { it.key }
            .isEmpty()
    }

    private fun getSquaresByColor(pieces: Map<Square, Piece>, color: PieceColor, condition: (Piece) -> Boolean): List<Square> {
        return pieces
            .filter{ it.value.color == color && condition(it.value)}
            .map { it.key }
    }
    private fun getOpponentColor(currentPlayer: PieceColor): PieceColor{
        if(currentPlayer == PieceColor.BLACK) return PieceColor.WHITE
        return PieceColor.BLACK
    }
    private fun getValidatorResult(from: Square, to: Square, pieces:Map<Square, Piece>): ValidatorResult{
        return pieces[from]!!.validationMapper.move(from, to, pieces)
    }
    
    private fun getInitialState(pieces: Map<Square, Piece>, currentPlayer: PieceColor): InitialState{
        val squares = getSquaresByColor(pieces, currentPlayer) { _ -> true }
        var opponentSquares = getSquaresByColor(pieces, getOpponentColor(currentPlayer)
        ) { piece -> piece.chessPiece == ChessPiece.KING }
        return InitialState(squares, opponentSquares)

    }
}