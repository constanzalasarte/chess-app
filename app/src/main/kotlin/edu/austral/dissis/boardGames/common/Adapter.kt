package edu.austral.dissis.boardGames.common

import edu.austral.dissis.boardGames.chess.victoryValidators.CheckmateValidator
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.victoryValidators.VictoryValidator
import edu.austral.dissis.boardGames.common.victoryValidators.result.ContinueResult
import edu.austral.dissis.boardGames.common.victoryValidators.result.VictoryResult
import edu.austral.dissis.chess.gui.GameOver
import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.dissis.chess.gui.MoveResult
import edu.austral.dissis.chess.gui.PlayerColor

class Adapter(
    private val nextColor: GameNextColor,
    private val victoryValidators: List<VictoryValidator>,
    private val maxCol: Int,
    private val maxRow: Int,
    private val map: Map<Square, Piece>,) {
    fun getMaxCol(): Int = maxCol
    fun getMaxRow(): Int = maxRow
    fun getMap(): Map<Square, Piece> = map
    fun movePiece(from: Square, to: Square): Adapter{
        val newMap = nextColor.movePiece(map, from, to)
        return Adapter(nextColor, victoryValidators, maxCol, maxRow, newMap)
    }
    fun getNextColor(currentPlayer: PlayerColor): PlayerColor = pieceToPlayerColor(nextColor.getNextColor(map, playerToPieceColor(currentPlayer)))
    private fun pieceToPlayerColor(color: PieceColor) : PlayerColor {
        if(color== PieceColor.WHITE) return PlayerColor.WHITE
        return PlayerColor.BLACK
    }
    private fun playerToPieceColor(color: PlayerColor) : PieceColor {
        if(color== PlayerColor.WHITE) return PieceColor.WHITE
        return PieceColor.BLACK
    }
    private fun playerToPieceOpponentColor(color: PlayerColor) : PieceColor {
        if(color== PlayerColor.WHITE) return PieceColor.BLACK
        return PieceColor.WHITE
    }
    private fun opponentPlayerColor(color: PlayerColor) : PlayerColor {
        if(color== PlayerColor.WHITE) return PlayerColor.BLACK
        return PlayerColor.WHITE
    }
    fun getResult(color: PlayerColor): VictoryResult {
        var result : VictoryResult = ContinueResult()
        victoryValidators.forEach{
            result = it.validateVictory(map, playerToPieceColor(color))
            if(result.isOver()) return result
        }
        return result
    }

    fun getSquares(currentColor: PlayerColor): List<Square> {
        return map.filter{ it.value.color == playerToPieceColor(currentColor) }
            .map { it.key }
    }

    private fun winByCheckmate(color: PlayerColor): MoveResult? {
        for(validator in victoryValidators){
            if(validator is CheckmateValidator){
                val result = validator.validateVictory(map, playerToPieceColor(color))
                if(result.isOver()) return GameOver(color)
                val resultOpp = validator.validateVictory(map, playerToPieceOpponentColor(color))
                if(resultOpp.isOver()) return GameOver(opponentPlayerColor(color))
            }
        }
        return null
    }

    fun checkPiece(from: Square, to: Square, currentColor: PlayerColor): MoveResult? {
        val piece = map.get(from)
        if(from == to) return InvalidMove("Invalid move")
        if(piece == null) return InvalidMove("No piece in (" + from.horizontal + ", " + from.vertical + ')')
        else if (pieceToPlayerColor(piece.color) != currentColor)
            return InvalidMove("Piece does not belong to current player")
        return null
    }

    fun checkMove(from: Square, to: Square): ValidatorResult {
        val piece = map[from]
        return piece!!.move(from, to, map)
    }
}