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

class ApiGatlingSimulationTest extends Simulation {

  val scn = scenario("AddAndFindPersons").repeat(1000, "n") {
        exec(
          http("AddPerson-API")
            .post("http://localhost:8090/persons")
            .header("Content-Type", "application/json")
            .body(StringBody("""{"firstName":"John${n}","lastName":"Smith${n}","birthDate":"1980-01-01", "address": {"country":"pl","city":"Warsaw","street":"Test${n}","postalCode":"02-200","houseNo":${n}}}"""))
            .check(status.is(200))
        ).pause(Duration.apply(5, TimeUnit.MILLISECONDS))    
  }.repeat(1000, "n") {
        exec(
          http("GetPerson-API")
            .get("http://localhost:8090/persons/${n}")
            .check(status.is(200))
        )   
  }
  
  setUp(scn.inject(atOnceUsers(30))).maxDuration(FiniteDuration.apply(10, "minutes"))
  
}