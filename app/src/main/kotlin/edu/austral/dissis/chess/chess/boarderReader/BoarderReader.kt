package edu.austral.dissis.chess.chess.boarderReader

import edu.austral.dissis.chess.common.ChessPiece
import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.PieceColor
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.chess.createValidationEngine.Engine
import edu.austral.dissis.chess.common.validationEngine.ValidationEngine
import java.io.File
import java.lang.Exception

class BoarderReader(private val posiblePieces: List<ChessPiece>, private val engine: Engine){
    fun getMap(fileName: String) : MutableMap<Square, Piece>{
        val inputString : String = readFile(fileName)

        val listString : List<List<String>> = stringToList(inputString)

        return mapSquarePiece(listString)
    }
    private fun readFile(fileName: String): String{
        return File(fileName)
            .inputStream()
            .bufferedReader()
            .use { it.readText() }
    }
    private fun stringToList(inputString: String): List<List<String>>{
        return inputString
            .split("\n")
            .reversed()
            .map { it.split("|")}
            .map { it.filter { it.isNotEmpty() } }
    }

    private fun mapSquarePiece(listString: List<List<String>>): MutableMap<Square, Piece>{
        val map: MutableMap<Square, Piece> = mutableMapOf<Square, Piece>()
        for ((indexList, list) in listString.withIndex()){
            for((indexString, string) in list.withIndex()){
                if(string.length != 2) continue
                map[Square(indexList, indexString)] = createPiece(string, indexString, indexList)
            }
        }
        return map
    }
    private fun createPiece(string: String, indexString: Int, indexList: Int): Piece {

        val piece = posiblePieces.filter { string[0] == it.toString()[0] }
        if (piece.isEmpty()) throw Exception("INCORRECT CHESS PIECE")
        val chessPiece = piece[0]

        val colorPiece: PieceColor = when (string[1]){
            'W' -> PieceColor.WHITE
            'B' -> PieceColor.BLACK
            else -> throw Exception("INCORRECT COLOR PIECE")
        }
//        PODES HACER UN MAPA
        val validationEngine: ValidationEngine = when (string[0]){
            'P' -> engine.pawnEngine()
            'H' -> engine.horseEngine()
            'B' -> engine.bishopEngine()
            'R' -> engine.rookEngine()
            'Q' -> engine.queenEngine()
            'K' -> engine.kingEngine()
            else -> throw Exception("INCORRECT CHESS PIECE")
        }
        return Piece(string + indexString.toString() + indexList.toString(), colorPiece, chessPiece, 0, validationEngine)
    }
}