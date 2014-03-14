import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._

/**
 * Test specification for [[models.Player]] class.
 */
@RunWith(classOf[JUnitRunner])
class PlayerSpec extends Specification {

  "Player" should {

    "have correct name" in {
      new models.Player("Oskar", 0).name must equalTo("Oskar") 
    }
    
    "have correct number of chips" in {
      new models.Player("", 123).chips must equalTo(123)
    }
    
    "have correct number of chips after bidding twice" in {
      val player = new models.Player("", 11)
      player.bid()
      player.bid()
      
      player.chips must equalTo(9)
    }
    
    "have initially no cards" in {
      new models.Player("", 123).cards.size must equalTo(0)
    }
    
    "have 2 cards after taking card twice" in {
      val player = new models.Player("", 0)
      player.take(models.Card(1))
      player.take(models.Card(2))
      
      player.cards.size must equalTo(2)
      player.cards(0) must equalTo(models.Card(1))
      player.cards(1) must equalTo(models.Card(2))
    }
  }
}
