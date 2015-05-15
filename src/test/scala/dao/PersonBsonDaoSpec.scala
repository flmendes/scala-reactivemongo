package dao

import model.Person
import org.scalatest._
import org.scalatest.time._
import org.scalatest.concurrent.ScalaFutures
import reactivemongo.api.DefaultDB
import reactivemongo.bson.BSONObjectID
import reactivemongo.extensions.dao.BsonDao
import reactivemongo.extensions.dsl.BsonDsl

import reactivemongo.extensions.fixtures.BsonFixtures

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import reactivemongo.extensions.Implicits._

/**
 * Created by flavio on 5/15/15.
 */
class PersonBsonDao(_db: DefaultDB) extends BsonDao[Person, BSONObjectID](_db, "persons") with BsonDsl {

  def findByName(name: String): Future[Option[Person]] = {
    findOne("name" $eq name)
  }

}

class PersonBsonDaoSpec extends FlatSpec with Matchers with ScalaFutures with BeforeAndAfter {

  override implicit def patienceConfig = PatienceConfig(timeout = Span(20, Seconds), interval = Span(1, Seconds))

  val db = MongoContext.db
  val fixtures = BsonFixtures(db)
  val personDao = new PersonBsonDao(db)

  after {
    db.drop()
  }

  "A PersonDao" should "load persons" in {

    val futureCount = for {
      remove <- fixtures.removeAll("bson/persons.conf")
      beforeCount <- personDao.count()
      insert <- fixtures.load("bson/persons.conf")
      afterCount <- personDao.count()
      person1 <- ~personDao.findByName("Ali")
    } yield (beforeCount, afterCount, person1)

    whenReady(futureCount) {
      case (beforeCount, afterCount, person1) =>
        beforeCount shouldBe 0
        afterCount shouldBe 2
        person1.name shouldBe "Ali"
    }
  }

  "A PersonDao" should "save person" in {
    val p1 = Person(name = "Test1", surname = "T", age = 36)
    val p2 = Person(name = "Test2", surname = "E", age = 33)

    val futurePerson = for {
      beforeCount <- personDao.count()
      res1 <- personDao.insert(p1)
      res2 <- personDao.insert(p2)
      afterCount <- personDao.count()
      person1 <- ~personDao.findByName("Test1")
    } yield (beforeCount, afterCount, person1)

    whenReady(futurePerson) {
      case (beforeCount, afterCount, person1) =>
        beforeCount shouldBe 0
        afterCount shouldBe 2
        person1.name shouldBe "Test1"
        person1.age shouldBe 36
    }
  }

}
