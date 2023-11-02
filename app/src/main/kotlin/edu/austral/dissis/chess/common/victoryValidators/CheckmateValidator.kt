package edu.austral.dissis.chess.common.victoryValidators

import edu.austral.dissis.chess.common.ChessPiece
import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.PieceColor
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult
import edu.austral.dissis.chess.common.victoryValidators.result.CheckmateResult
import edu.austral.dissis.chess.common.victoryValidators.result.ContinueResult
import edu.austral.dissis.chess.common.victoryValidators.result.NoMoreOpponentPieces
import edu.austral.dissis.chess.common.victoryValidators.result.VictoryResult

/*
* A validator that checks if the current player has won by checkmate
* it should be run after each move
*/

data class InitialState(val squares: List<Square>, val opponentSquares: List<Square>)

class CheckmateValidator : VictoryValidator {
    override fun validateVictory(pieces: Map<Square, Piece>, currentPlayer: PieceColor): VictoryResult {
        val initialState = getInitialState(pieces, currentPlayer)
        var state = getInitialState(pieces, currentPlayer)

        var oppIndex : Int
        var typeResult : ValidatorResult
        for (square in state.squares){
            oppIndex = 0
            while (checkSizeOfOppSquares(state, oppIndex)){
                typeResult = getValidatorResult(square, state.opponentSquares[oppIndex], pieces)
                if(typeResult.isValid())
                    state = changeState(state, oppIndex)
                else oppIndex++
            }
        }
        if(state.opponentSquares != initialState.opponentSquares) return CheckmateResult()
        return ContinueResult()
    }

    private fun checkSizeOfOppSquares(
        state: InitialState,
        oppIndex: Int
    ) = state.opponentSquares.isNotEmpty() && oppIndex < state.opponentSquares.size

    private fun changeState(
        state: InitialState,
        oppIndex: Int
    ) = InitialState(state.squares, state.opponentSquares.filterIndexed { index, _ -> index != oppIndex })

    private fun getSquaresByColor(pieces: Map<Square, Piece>, color: PieceColor, condition: (Piece) -> Boolean): List<Square> {
        return pieces
            .filter{ it.value.color == color && condition(it.value)}
            .map { it.key }
    }
    private fun getOpponentColor(currentPlayer: PieceColor): PieceColor {
        if(currentPlayer == PieceColor.BLACK) return PieceColor.WHITE
        return PieceColor.BLACK
    }
    private fun getValidatorResult(from: Square, to: Square, pieces:Map<Square, Piece>): ValidatorResult{
        return pieces[from]!!.validationMapper.move(from, to, pieces)
    }
    
    private fun getInitialState(pieces: Map<Square, Piece>, currentPlayer: PieceColor): InitialState{
        val squares = getSquaresByColor(pieces, currentPlayer) { _ -> true }
        val opponentSquares = getSquaresByColor(pieces, getOpponentColor(currentPlayer)
        ) { piece -> piece.chessPiece == ChessPiece.KING }
        return InitialState(squares, opponentSquares)

    }
}