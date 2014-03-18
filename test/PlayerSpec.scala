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
    
    "have exactly 11 chips at the beginning" in {
      new models.Player("").chips must equalTo(11)
    }
    
    "have 9 chips after bidding twice" in {
      val player = new models.Player("")
      player.bid()
      player.bid()
      
      player.chips must equalTo(9)
    }
    
    "have initially no cards" in {
      new models.Player("").cards.size must equalTo(0)
    }
    
    "have 2 cards after taking card twice" in {
      val player = new models.Player("")
      player.take(models.Card(1))
      player.take(models.Card(2))
      
      player.cards.size must equalTo(2)
      player.cards(0) must equalTo(models.Card(1))
      player.cards(1) must equalTo(models.Card(2))
    }
  }
}
