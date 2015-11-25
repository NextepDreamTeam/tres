package controllers

import com.google.inject.AbstractModule
import models.{Startup, StartupConfiguration}
import play.api.libs.concurrent.AkkaGuiceSupport


/**
  * Created by aandelie on 25/11/15.
  */
class StartupConfigurationModule extends AbstractModule with AkkaGuiceSupport{



  def configure() = {
    bind(classOf[StartupConfiguration]).to(classOf[Startup]).asEagerSingleton()
  }
}
