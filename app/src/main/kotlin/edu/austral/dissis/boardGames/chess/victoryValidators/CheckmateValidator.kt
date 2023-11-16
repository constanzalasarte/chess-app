package edu.austral.dissis.boardGames.chess.victoryValidators

import edu.austral.dissis.boardGames.common.ChessPiece
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.PieceColor
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.victoryValidators.VictoryValidator
import edu.austral.dissis.boardGames.common.victoryValidators.result.ContinueResult
import edu.austral.dissis.boardGames.common.victoryValidators.result.VictoryResult

/*
* A validator that checks if the current player has won by checkmate
* it should be run after each move
*/

data class InitialState(val squares: List<Square>, val opponentSquares: List<Square>)

data class Move(val from: Square, val to: Square)

class CheckmateValidator(val maxRow: Int, val maxCol: Int) : VictoryValidator {
//    fun validate(pieces: Map<Square, Piece>, currentPlayer: PieceColor): VictoryResult {
//        val allMoves = getAllPossiblesMoves(pieces)
//        var state = getInitialState(pieces, currentPlayer)
//        for((piece, moves) in allMoves){
//            for(move in moves){
////                haces el movimiento
//                val map : Map<Square, Piece> = makeMove(move, pieces)
//
//            }
//        }
//    }
    override fun validateVictory(pieces: Map<Square, Piece>, currentPlayer: PieceColor): VictoryResult {
        val initialState = getInitialState(pieces, currentPlayer)
        var state = getInitialState(pieces, currentPlayer)

        for (square in state.squares){
            state = checkmate(state, square, pieces)
        }
        if(state.opponentSquares != initialState.opponentSquares) {
            return CheckmateResult()
        }
        return ContinueResult()
    }
    private fun makeMove(move: Move, pieces: Map<Square, Piece>): Map<Square, Piece> {
        val map : MutableMap<Square, Piece> = HashMap(pieces)
        return pieces.filter { (square, _) -> !square.equals(move.from)}
    }

    private fun checkmate(
        state: InitialState,
        square: Square,
        pieces: Map<Square, Piece>
    ): InitialState {
        var state1 = state
        var typeResult1: ValidatorResult
        var oppIndex = 0
        while (checkSizeOfOppSquares(state1, oppIndex)) {
            typeResult1 = getValidatorResult(square, state1.opponentSquares[oppIndex], pieces)
            if (typeResult1.isValid())
                state1 = removeOppSquare(state1, oppIndex)
            else oppIndex++
        }
        return state1
    }

    private fun checkSizeOfOppSquares(
        state: InitialState,
        oppIndex: Int
    ) = state.opponentSquares.isNotEmpty() && oppIndex < state.opponentSquares.size

    private fun removeOppSquare(
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
    private fun getValidatorResult(from: Square, to: Square, pieces:Map<Square, Piece>): ValidatorResult {
        return pieces[from]!!.validationMapper.move(from, to, pieces)
    }
    
    private fun getInitialState(pieces: Map<Square, Piece>, currentPlayer: PieceColor): InitialState {
        val squares = getSquaresByColor(pieces, currentPlayer) { _ -> true }
        val opponentSquares = getSquaresByColor(pieces, getOpponentColor(currentPlayer)
        ) { piece -> piece.chessPiece == ChessPiece.KING }
        return InitialState(squares, opponentSquares)
    }

//    get a list of all possibles moves given a piece
    private fun getAllPossiblesMoves(pieces: Map<Square, Piece>): Map<Piece, List<Move>>{
        val map = HashMap<Piece, List<Move>>()
        for ((square, piece) in pieces){
            val list = ArrayList<Move>()
            for (i: Int in 0..maxRow)
                for (j: Int in 0..maxCol){
                    val result = piece.move(square, Square(i, j), pieces)
                    if (result.isValid()) list.add(Move(square, Square(i, j)))
                }
            map[piece] = list
        }
        return map
    }
}