package edu.austral.dissis.boardGames.common.boarderReader

import edu.austral.dissis.boardGames.common.ChessPiece
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.PieceColor
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validationEngine.ValidationEngine
import java.io.File
import java.lang.Exception

class BoarderReader(private val enginePieces: Map<ChessPiece, ValidationEngine>){
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
                if(checkSize(string)) continue
                map[Square(indexList, indexString)] = createPiece(string, indexString, indexList)
            }
        }
        return map
    }

    private fun checkSize(string: String) = string.length != 2
    private fun createPiece(string: String, indexString: Int, indexList: Int): Piece {

        val piece : List<ChessPiece> = enginePieces.keys.filter { string[0] == it.toString()[0] }
        if (piece.isEmpty()) throw Exception("INCORRECT CHESS PIECE")
        val chessPiece = piece[0]

        val colorPiece: PieceColor = when (string[1]){
            'W' -> PieceColor.WHITE
            'B' -> PieceColor.BLACK
            else -> throw Exception("INCORRECT COLOR PIECE")
        }

        val validationEngine: ValidationEngine =
            enginePieces.get(piece[0]) ?: throw Exception("INCORRECT CHESS PIECE")
        return Piece(string + indexString.toString() + indexList.toString(), colorPiece, chessPiece, 0, validationEngine)
    }
}