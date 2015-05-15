package dao

import reactivemongo.api.{ DefaultDB, MongoDriver }

/**
 * Created by flavio on 5/15/15.
 */
object MongoContext {
  import scala.concurrent.ExecutionContext.Implicits.global

  val driver = new MongoDriver
  val connection = driver.connection(List("localhost"))
  def db: DefaultDB = connection("test-scala-reactivemongo")

}
