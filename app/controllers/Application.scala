package controllers

import play.api.data.Forms.nonEmptyText
import play.api.data.Form
import play.api.mvc.Action
import play.api.mvc.Controller
import utils.Hash
import models.PageStatistic
import play.api.cache.Cache
import play.api.Play.current

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
        val hash = Hash.md5(label)
        Cache.set(hash, label)
        Redirect(routes.Application.showResult(hash))
      }
    )
  }

  def showResult(hash: String) = Action {
	val pageURI = Cache.getAs[String](hash)
    var pageStat = new PageStatistic(hash, pageURI.get)
    Ok(views.html.result(pageStat))
  }

}