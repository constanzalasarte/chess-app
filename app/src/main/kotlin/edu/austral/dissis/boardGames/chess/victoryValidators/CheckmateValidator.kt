package edu.austral.dissis.boardGames.chess.victoryValidators

import edu.austral.dissis.boardGames.common.MyPiece
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

data class InitialState(val kingSquare: Square, val opponentSquares: List<Square>)

data class Move(val from: Square, val to: Square)

class CheckmateValidator(val maxRow: Int, val maxCol: Int) : VictoryValidator {

    override fun validateVictory(pieces: Map<Square, Piece>, currentPlayer: PieceColor): VictoryResult {
        val allMoves = getAllPossiblesMoves(pieces, currentPlayer)
        for((_, moves) in allMoves){
            val list = moves.toMutableList()
            for(move in moves){
                val map : Map<Square, Piece> = makeMove(move, pieces)
                if(inCheck(map, currentPlayer))
                    list.remove(move)
            }
            if (list.isNotEmpty()) return ContinueResult()
        }
        return CheckmateResult()
    }

    // check if the other player has you in check
    private fun inCheck(pieces: Map<Square, Piece>, currentPlayer: PieceColor): Boolean {
        var map = pieces
        val state = getInitialState(pieces, currentPlayer) ?: return true
        for(oppSquare in state.opponentSquares){
            val typeResult = getValidatorResult(oppSquare, state.kingSquare, map)
            if (typeResult.isValid()) map = makeMove(Move(oppSquare, state.kingSquare), map)
            if (kingIsGone(map, state, currentPlayer)) return true
        }
        return false
    }

    private fun kingIsGone(
        map: Map<Square, Piece>,
        state: InitialState,
        currentPlayer: PieceColor
    ) = map[state.kingSquare]!!.color != currentPlayer


    private fun makeMove(move: Move, pieces: Map<Square, Piece>): Map<Square, Piece> {
        val map : MutableMap<Square, Piece> = pieces.toMutableMap()
        val piece = map[move.from]
        map.remove(move.from)
        map.remove(move.to)
        map.put(move.to, piece!!)
        return map.toMap()
    }

    private fun getSquaresByColor(pieces: Map<Square, Piece>, color: PieceColor, condition: (Piece) -> Boolean): List<Square> {
        return pieces
            .filter{ it.value.color == color && condition(it.value)}
            .map { it.key }
    }

    private fun getOpponentColor(currentPlayer: PieceColor): PieceColor {
        if(colorIsBlack(currentPlayer)) return PieceColor.WHITE
        return PieceColor.BLACK
    }

    private fun colorIsBlack(currentPlayer: PieceColor) = currentPlayer == PieceColor.BLACK
    private fun getValidatorResult(from: Square, to: Square, pieces:Map<Square, Piece>): ValidatorResult {
        return pieces[from]!!.move(from, to, pieces)
    }
    
    private fun getInitialState(pieces: Map<Square, Piece>, currentPlayer: PieceColor): InitialState? {
        val squares = getSquaresByColor(pieces, currentPlayer){ piece -> piece.chessPiece == MyPiece.KING }
        val opponentSquares = getSquaresByColor(pieces, getOpponentColor(currentPlayer)) { _ -> true }
        if(squares.isNotEmpty())
            return InitialState(squares[0], opponentSquares)
        return null
    }

//    get a list of all possibles moves given a piece
    private fun getAllPossiblesMoves(pieces: Map<Square, Piece>, pieceColor: PieceColor): Map<Piece, List<Move>>{
        val map = HashMap<Piece, List<Move>>()
        for ((square, piece) in pieces.filter { it.value.color == pieceColor}){
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