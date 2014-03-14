package models

import errors.PlayerCantBid
import akka.actor.Actor

/**
 * Represents single player in the game.
 *
 * @constructor Creates new player
 * @param name player's name
 * @param chips number of player's chips
 */
class Player(val name: String, var chips: Int) {

  /** Cards already collected by the player. */
  var cards = scala.collection.mutable.Set[Card]()

  /**
   * Takes new card.
   *
   * @param card card to take
   */
  def take(card: Card) = {
    println(s"Player $name took $card")
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
      println(s"Player $name bids")
    } else {
      throw PlayerCantBid(this)
    }
  }
}