package services

/**
 * Provides timestamp utilities.
 */
object TimestampService {
  
  /**
   * Gets current epoch timestamp.
   * 
   * @return total elapsed milliseconds from 1/1/1970
   */
  def now: Long = {
    System.currentTimeMillis
  }

}