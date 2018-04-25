package pl.piomin.services.gatling

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import pl.piomin.services.gatling.model.Person
import java.util.Date
import pl.piomin.services.gatling.model.Address
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import scala.util.Random
import scala.collection.Iterator

class ApiGatlingSimulationTest extends Simulation {

  val rCustomer = Iterator.continually(Map("customer" -> List("5aa8f5deb44f3f188896f56f", "5aa8f5ecb44f3f188896f570", "5aa8f5fbb44f3f188896f571", "5aa8f620b44f3f188896f572").lift(Random.nextInt(4)).get))
  val rProduct = Iterator.continually(Map("product" -> List("5aa8fad2b44f3f18f8856ac9","5aa8fad8b44f3f18f8856aca","5aa8fadeb44f3f18f8856acb","5aa8fae3b44f3f18f8856acc","5aa8fae7b44f3f18f8856acd","5aa8faedb44f3f18f8856ace","5aa8faf2b44f3f18f8856acf").lift(Random.nextInt(7)).get))

  
  val scn = scenario("AddAndConfirmOrder").feed(rCustomer).feed(rProduct).repeat(500, "n") {
        exec(
          http("AddOrder-API")
            .post("http://localhost:8090/order")
            .header("Content-Type", "application/json")
            .body(StringBody("""{"productIds":["${product}"],"customerId":"${customer}","status":"NEW"}"""))
            .check(status.is(200), jsonPath("$.id").saveAs("orderId"))
        )
        .
        exec(
          http("ConfirmOrder-API")
            .put("http://localhost:8090/order/${orderId}")
            .header("Content-Type", "application/json")
            .check(status.is(200))
        )
  }
  
  setUp(scn.inject(atOnceUsers(20))).maxDuration(FiniteDuration.apply(10, "minutes"))
  
}