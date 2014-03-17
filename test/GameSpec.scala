import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.test.Helpers._
import scala.util.Failure

/**
 * Test specification for [[models.Game]] class.
 */
@RunWith(classOf[JUnitRunner])
class GameSpec extends Specification {

  "Game" should {

    "have correct name" in {
      new models.Game("New game", null, 0, 0).name must equalTo("New game")
    }

    "have correct host" in {
      val host = new models.Player("Player 1", 0)
      new models.Game("New game", host, 0, 0).host must equalTo(host)
    }

    "not start with only one player" in {
      val player1 = new models.Player("Player 1", 0)

      val started = new models.Game("New game", player1, 2, 2).start()
      started must beFailedTry
    }

    "allow joining players" in {
      val player1 = new models.Player("Player 1", 0)
      val player2 = new models.Player("Player 2", 0)

      val game = new models.Game("New game", player1, 2, 2)
      game.join(player2) must beSuccessfulTry
    }

    "inform about already joined players" in {
      val player1 = new models.Player("Player 1", 0)

      val game = new models.Game("New game", player1, 2, 2)
      game.join(player1) must beSuccessfulTry.withValue(false)
    }

    "not allow joining too much players" in {
      val player1 = new models.Player("Player 1", 0)
      val player2 = new models.Player("Player 2", 0)
      val player3 = new models.Player("Player 3", 0)

      val game = new models.Game("New game", player1, 2, 2)
      game.join(player1) must beSuccessfulTry.withValue(false)
      game.join(player2) must beSuccessfulTry.withValue(true)
      game.join(player3) must beFailedTry
    }

    "start properly" in {
      val player1 = new models.Player("Player 1", 0)
      val player2 = new models.Player("Player 2", 0)
      val player3 = new models.Player("Player 3", 0)

      val game = new models.Game("New game", player1, 3, 5)
      game.join(player2) must beSuccessfulTry.withValue(true)
      game.join(player3) must beSuccessfulTry.withValue(true)

      game.start() must beSuccessfulTry
    }

    "inform when it has already started" in {
      val player1 = new models.Player("Player 1", 0)
      val player2 = new models.Player("Player 2", 0)
      val player3 = new models.Player("Player 3", 0)

      val game = new models.Game("New game", player1, 3, 5)
      game.join(player2) must beSuccessfulTry.withValue(true)
      game.join(player3) must beSuccessfulTry.withValue(true)

      game.start() must beSuccessfulTry.withValue(true)
      game.start() must beSuccessfulTry.withValue(false)
    }

    "allow active player to bid" in {
      val player1 = new models.Player("Player 1", 2)
      val game = new models.Game("New game", player1, 1, 5)
      game.start()

      game.bid(player1) must beSuccessfulTry
    }

    "block bidding by inactive player" in {
      val player1 = new models.Player("Player 1", 2)
      val player2 = new models.Player("Player 2", 2)
      val game = new models.Game("New game", player1, 2, 5)
      game.join(player2) must beSuccessfulTry.withValue(true)
      game.start() must beSuccessfulTry.withValue(true)

      if (game.activePlayer.get == player1) {
        game.bid(player2) must beFailedTry
      } else {
        game.bid(player1) must beFailedTry
      }
    }
    
    "have exactly 33 cards in total" in {
      val player1 = new models.Player("Player 1", 2)
      val game = new models.Game("New game", player1, 2, 5)
      
      game.allCards.size must equalTo(33)
    }
    
    "have exactly 9 cards rejected" in {
      val player1 = new models.Player("Player 1")
      val player2 = new models.Player("Player 2")
      val game = new models.Game("New game", player1, 2, 5)
      
      game.join(player2) must beSuccessfulTry.withValue(true)
      game.start() must beSuccessfulTry.withValue(true)
      
      game.rejectedCards.size must equalTo(9)
    }
    
    "have exactly 24 cards in play at the beginning" in {
      val player1 = new models.Player("Player 1")
      val player2 = new models.Player("Player 2")
      val game = new models.Game("New game", player1, 2, 5)
      
      game.join(player2) must beSuccessfulTry.withValue(true)
      game.start() must beSuccessfulTry.withValue(true)
      
      game.leftCards.size must equalTo(24)
    }
    
    "have different sets of cards for each game" in {
      val player1 = new models.Player("Player 1")
      val player2 = new models.Player("Player 2")
      val game1 = new models.Game("New game", player1, 2, 5)
      
      game1.join(player2) must beSuccessfulTry.withValue(true)
      game1.start() must beSuccessfulTry.withValue(true)
      
      val set1 = game1.leftCards
      
      val game2 = new models.Game("New game", player1, 2, 5)
      
      game2.join(player2) must beSuccessfulTry.withValue(true)
      game2.start() must beSuccessfulTry.withValue(true)
      
      val set2 = game2.leftCards
      
      set1 must not be equalTo(set2)
    }
  }
}
