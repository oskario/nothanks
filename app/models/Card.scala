package models

/**
 * Represents single card in the game.
 *
 * @param value card value (must be 3-35)
 */
case class Card(value: Int) {

  /**
   * True if card is even, otherwise false.
   */
  val isEven: Boolean = {
    value%2 match {
      case 0 => true
      case _ => false
    }
  }
  
  /**
   * True if card is odd, otherwise false.
   */
  val isOdd: Boolean = !isEven

  /**
   * Card color:
   *  - even: black,
   *  - odd: red.
   */
  val color: String = {
    value % 2 match {
      case 0 => "black"
      case _ => "red"
    }
  }

  /**
   * Card color in hex:
   *  - even: #000000,
   *  - odd: #FF0000.
   */
  val colorHex: String = {
    value % 2 match {
      case 0 => "#000000"
      case _ => "#FF0000"
    }
  }
}