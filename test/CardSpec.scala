import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._

/**
 * Test specification for [[models.Card]] class.
 */
@RunWith(classOf[JUnitRunner])
class CardSpec extends Specification {

  "Card" should {

    "be even with value 22" in { 
    	models.Card(22).isEven must equalTo(true) 
    }
    
    "be odd with value 33" in { 
    	models.Card(33).isOdd must equalTo(true) 
    }
    
    "not be even with value 9" in { 
    	models.Card(9).isEven must equalTo(false) 
    }
    
    "be black with value 8" in { 
    	models.Card(8).color must equalTo("black") 
    }
    
    "be red with value 7" in { 
    	models.Card(7).color must equalTo("red") 
    }
    
  }
}
