package edu.austral.dissis.chess.chess.validators.result

class InvalidResult : ValidatorResult {
    override fun isValid(): Boolean {
        return false
    }
}