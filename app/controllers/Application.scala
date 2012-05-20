package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models.PageStatistic

object Application extends Controller {

  val newForm = Form(
    "label" -> nonEmptyText
  )
  
  def index = Action {
    Ok(views.html.index(newForm))
  }
  
  def getPage = Action { implicit request =>
    newForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(errors)),
      label => {
        PageStatistic.create(label)
        //TODO: replace 1 with real value
        Redirect(routes.Application.showResult(1))
      }
    )
  }

  def showResult(id: Long) = TODO

}