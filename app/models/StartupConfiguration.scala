package models

import javax.inject.Inject

import akka.actor.ActorSystem
import models.algorithm.Id3Service
import scala.concurrent.duration
import scala.concurrent.duration._
/**
  * Created by aandelie on 25/11/15.
  */
sealed trait StartupConfiguration {}


class Startup @Inject() (actorSystem: ActorSystem) extends StartupConfiguration {

  implicit val ec = actorSystem.dispatcher

  initialize()

  def initialize() = {
    actorSystem.scheduler.schedule(Duration(0,duration.SECONDS),Duration(24,duration.SECONDS))(Id3Service.start())
  }
}