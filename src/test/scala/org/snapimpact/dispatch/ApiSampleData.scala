package org.snapimpact.dispatch

/**
 * Created by IntelliJ IDEA.
 * User: flipper
 * Date: May 5, 2010
 * Time: 9:24:15 PM
 * To change this template use File | Settings | File Templates.
 */


import _root_.org.specs._
import _root_.org.specs.runner._
import _root_.org.specs.Sugar._

import net.liftweb.http.testing._
import net.liftweb.common._
import net.liftweb.util.Helpers._
import net.liftweb.json._
import JsonParser._
import JsonAST._
import specification.Expectable
import _root_.org.specs.execute._
import org.joda.time._
import org.joda.time.format._
import scala.xml.XML

import org.snapimpact.util._

class ApiSampleDataTest extends Runner(new ApiSampleDataSpec) with JUnit with Console
//
class ApiSampleDataSpec extends Specification with ApiSubmitTester with RequestKit
{
  def baseUrl = "http://localhost:8989"
  RunWebApp.start()

  "Sample loaded api" should
  {
    
    // Upload the sample file, that holds the test data
    "Accept valid xml upload" in {
      post("/api/upload?key=somekey", XML.loadFile("src/test/resources/sampleData0.1.r1254.xml")).map(_.code) must_== Full(200)
    }
    
    
    "not return events for something that is not available in the data " in
    {
      testParams("q" -> "zx_NotThere_xz" ) {
        _ must_== 0
      }
    }

    "return events from description MicroMentor " in {
      SkipHandler.pendingUntilFixed{
        testParams("q" -> "MicroMentor" ) {
          _ must be >= 1
        }
      }
    }

    "return events from description dodgeball " in
    {
      testParams("q" -> "dodgeball" ) {
        _ must be >= 1
      }
    }

    "return events from description volunteer" in
    {
      SkipHandler.pendingUntilFixed{
        testParams("q" -> "volunteer" ) {
          _ must be >= 5
        }
      }
    }

    "return events from locaiton 97232 and Micro*" in
    {
      SkipHandler.pendingUntilFixed{
        testParams("q" -> "Micro*", "vol_loc" -> "97232" ) {
          _ must be >= 1
        }
      }
    }

    "return events from locaiton Portland,OR and Micro*" in
    {
      SkipHandler.pendingUntilFixed{
        testParams("q" -> "Micro*", "vol_loc" -> "Portland,OR" ) {
          _ must be >= 1
        }
      }
    }

    "return events from locaiton Portland,OR and MicroMentor" in
    {
      SkipHandler.pendingUntilFixed{
        testParams("q" -> "MicroMentor", "vol_loc" -> "Portland,OR" ) {
          _ must be >= 1
        }
      }
    }

    "return events from locaiton CA and In" in
    {
      SkipHandler.pendingUntilFixed{
        testParams("q" -> "In", "vol_loc" -> "CA" ) {
          _ must be >= 2
        }
      }
    }

    "return events from Hunger category" in
    {
      SkipHandler.pendingUntilFixed{
        testParams("q" -> "Hunger" ) {
          _ must be >= 1
        }
      }
    }

    "return events from community category and descriptions" in
    {
      SkipHandler.pendingUntilFixed{
        testParams("q" -> "community" ) {
          _ must be >= 6
        }
      }
    }

    // hunger cats
    "return events for hunger category" in {
      SkipHandler.pendingUntilFixed{
        testParams("q" -> "category:Hunger" ) {
          _ must be > 0
        }
      }
    }
  }  //  "api" should

}   // ApiSampleDataSpec

