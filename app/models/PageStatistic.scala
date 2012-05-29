package models
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

case class PageStatistic(hash: String, pageURI: String) {
  val url = PageStatistic.handlePageURI(pageURI)
  var doc: Document = Jsoup.connect(url).get()
  val strLen = doc.outerHtml().length()
  val urlLen = url.length()
  val title = doc.title()
}

object PageStatistic {
  def handlePageURI(url:String) = { 
    val httppattern = "http(?:s)+://(.*)".r
    url match {
        case httppattern(x) => url
        case y:String => ("http://" + y)
    }
  }
}