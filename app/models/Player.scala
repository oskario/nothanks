package models

import errors.PlayerCantBid

/**
 * Represents single player in the game.
 *
 * @constructor Creates new player
 * @param name player's name
 * @param chips number of player's chips
 */
class Player(val name: String, var chips: Int = 11) {

  /** Cards already collected by the player. */
  val cards = scala.collection.mutable.Buffer[Card]()

  /**
   * Takes new card.
   *
   * @param card card to take
   */
  def take(card: Card) = {
    cards += card
  }

  /**
   * Bids.
   * 
   * Throws [[errors.PlayerCantBid]] when player have no chips left to bid.
   */
  def bid() = {
    if (chips > 0) {
      chips -= 1
    } else {
      throw PlayerCantBid(this)
    }
  }
}