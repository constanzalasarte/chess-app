package edu.austral.dissis.chess.chess.boarderReader

import edu.austral.dissis.chess.chess.ChessPiece
import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.PieceColor
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.createValidationEngine.Engine
import edu.austral.dissis.chess.chess.validationEngine.ValidationEngine
import java.io.File
import java.lang.Exception

class BoarderReader{
    fun boarderReader(fileName: String, engine: Engine) : MutableMap<Square, Piece>{
        val inputString : String = readFile(fileName)

        val listString : List<List<String>> = stringToList(inputString)

        return mapSquarePiece(listString, engine)
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

    private fun mapSquarePiece(listString: List<List<String>>, engine: Engine): MutableMap<Square, Piece>{
        val map: MutableMap<Square, Piece> = mutableMapOf<Square, Piece>()
        for ((indexList, list) in listString.withIndex()){
            for((indexString, string) in list.withIndex()){
                if(string.length != 2) continue
                map[Square(indexList, indexString)] = createPiece(string, indexString, indexList, engine)
            }
        }
        return map
    }
    private fun createPiece(string: String, indexString: Int, indexList: Int, engine: Engine): Piece{
        val chessPiece: ChessPiece = when (string[0]){
            'P' -> ChessPiece.PAWN
            'H' -> ChessPiece.HORSE
            'B' -> ChessPiece.BISHOP
            'R' -> ChessPiece.ROOK
            'Q' -> ChessPiece.QUEEN
            'K' -> ChessPiece.KING
            else -> throw Exception("INCORRECT CHESS PIECE")
        }

        val colorPiece: PieceColor = when (string[1]){
            'W' -> PieceColor.WHITE
            'B' -> PieceColor.BLACK
            else -> throw Exception("INCORRECT COLOR PIECE")
        }
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