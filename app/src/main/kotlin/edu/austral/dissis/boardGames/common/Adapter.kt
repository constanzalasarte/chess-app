package edu.austral.dissis.boardGames.common

import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.victoryValidators.VictoryValidator
import edu.austral.dissis.boardGames.common.victoryValidators.result.ContinueResult
import edu.austral.dissis.boardGames.common.victoryValidators.result.VictoryResult
import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.dissis.chess.gui.MoveResult
import edu.austral.dissis.chess.gui.PlayerColor

class Adapter(
    private val nextColor: GameNextColor,
    private val victoryValidators: List<VictoryValidator>,
    private val maxCol: Int,
    private val maxRow: Int,
    private val map: Map<Square, Piece>,
    private val oldMap : Map<Square, Piece> = map) {
    fun getMaxCol(): Int = maxCol
    fun getMaxRow(): Int = maxRow
    fun getMap(): Map<Square, Piece> = map
    fun movePiece(from: Square, to: Square): Adapter {
        val newMap = nextColor.movePiece(map, from, to)
        return Adapter(nextColor, victoryValidators, maxCol, maxRow, newMap, map)
    }
    fun getNextColor(currentPlayer: PlayerColor): PlayerColor{
        return if(checkMapSizes()) pieceToPlayerColor(nextColor.getNextColor(map, playerToPieceColor(currentPlayer)))
        else opponentPlayerColor(currentPlayer)
    }

    private fun checkMapSizes() = oldMap.size != map.size

    private fun pieceToPlayerColor(color: PieceColor) : PlayerColor {
        if(color == PieceColor.WHITE) return PlayerColor.WHITE
        return PlayerColor.BLACK
    }
    private fun playerToPieceColor(color: PlayerColor) : PieceColor {
        if(checkWhiteColor(color)) return PieceColor.WHITE
        return PieceColor.BLACK
    }

    private fun opponentPlayerColor(color: PlayerColor) : PlayerColor {
        if(checkWhiteColor(color)) return PlayerColor.BLACK
        return PlayerColor.WHITE
    }

    private fun checkWhiteColor(color: PlayerColor) = color == PlayerColor.WHITE
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