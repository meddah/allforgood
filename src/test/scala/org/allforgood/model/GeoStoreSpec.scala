package org.allforgood
package model

import org.specs._
import org.specs.runner._

import net.liftweb.util._

class GeoStoreTest extends Runner(new GeoStoreSpec) with JUnit with Console

class GeoStoreSpec extends Specification {
  lazy val geoStore: GeoStore = new MemoryGeoStore

  "Geo Store" should {
    "Associate the GUID and a geo location" in {
      val guid = GUID.create()
      val geoLoc1 = GeoLocation(45.0,1.0)
      val geoLoc2 = GeoLocation(46.0,2.0)
      val geoLoc3 = GeoLocation(47.0,3.0)
      geoStore.add( guid, geoLoc1 :: geoLoc2 :: geoLoc3 :: Nil)
      
      val found = geoStore.find( geoLoc1, 2.0,
                                x => true,
                                0, 50)

      found.map(_._1).filter(_ == guid) must_== List(guid)
    }
  }
}


