package edu.austral.dissis.chess.chess.createValidationEngine
import edu.austral.dissis.chess.chess.validationEngine.ValidationEngine
interface Engine {
    fun pawnEngine(): ValidationEngine
    fun horseEngine(): ValidationEngine
    fun bishopEngine(): ValidationEngine
    fun rookEngine(): ValidationEngine
    fun queenEngine(): ValidationEngine
    fun kingEngine(): ValidationEngine
}