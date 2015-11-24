package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Tres(tree recommendation system) is ready to use. Love your software. Treat it well!!!"))
  }

}