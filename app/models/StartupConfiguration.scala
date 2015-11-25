package models

import com.google.inject.Inject
import play.api.Application
import play.api.inject.ApplicationLifecycle
import play.api.libs.concurrent.Akka

import scala.concurrent.duration
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
/**
  * Created by aandelie on 25/11/15.
  */
sealed trait StartupConfiguration {}


class Startup extends StartupConfiguration {

  initialize()

  def initialize() = {
    Akka.system.scheduler.schedule(Duration(0,duration.SECONDS),Duration(3,duration.SECONDS))(println("CIAONE"))
    Akka.system.dispatcher
  }
}