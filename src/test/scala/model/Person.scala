package model

import reactivemongo.bson.{ Macros, BSONObjectID }

/**
 * Created by flavio on 5/15/15.
 */
case class Person(_id: BSONObjectID = BSONObjectID.generate, name: String, surname: String, age: Int)

object Person {
  implicit val personHandler = Macros.handler[Person]
}
