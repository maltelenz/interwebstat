package models
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import collection.JavaConversions._

case class PageStatistic(hash: String, pageURI: String) {
  val url = PageStatistic.handlePageURI(pageURI)
  var doc: Document = Jsoup.connect(url).get()
  val strLen = doc.outerHtml().length()
  val urlLen = url.length()
  val title = doc.title()
  val nrA = doc.getElementsByTag("a").size();
  val nrTags = doc.getAllElements().size();
  val tagHistogram = for(tag <- (doc.getAllElements().map(s => s.tagName()) groupBy identity))
	  						yield (tag._1, tag._2.length)
}

object PageStatistic {
  def handlePageURI(url:String) = { 
    val httppattern = "http(?:s)?://(.*)".r
    url match {
        case httppattern(x) => url
        case y:String => ("http://" + y)
    }
  }
}