import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.MovementValidator
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
import edu.austral.dissis.chess.chess.validators.result.ValidResult
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult

class CheckDestinationSquare : MovementValidator {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        if(pieces[from]?.color != pieces[to]?.color) return ValidResult()
        return InvalidResult()
    }
}