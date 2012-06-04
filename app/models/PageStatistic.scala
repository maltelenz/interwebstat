package models
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import collection.JavaConversions._

case class PageStatistic(hash: String, pageURI: String) {
  val url = PageStatistic.handlePageURI(pageURI)
  val urlDomainPart = url.take(url.indexOf("/", url.indexOf("://") + 3) match {
	    case -1 => url.length
	    case s => s
	  })
  val domain = urlDomainPart.drop(urlDomainPart.indexOf("://") + 3)
  var doc: Document = Jsoup.connect(url).get()
  val strLen = doc.outerHtml().length()
  val urlLen = url.length()
  val title = doc.title()
  val nrA = doc.getElementsByTag("a").size();
  val nrTags = doc.getAllElements().size();
  val tagHistogram = ((for(tag <- (doc.getAllElements().map(s => s.tagName()) groupBy identity))
	  						yield (tag._1, tag._2.length)).toList sortWith (_._2 > _._2) take 10) reverse
  val charHistogram = ((for(tag <- (doc.outerHtml() groupBy identity))
	  						yield (tag._1, tag._2.length)).toList sortWith (_._2 > _._2) take 10) reverse
  val nrAExternal = doc.getElementsByTag("a").filter (s => s.attr("href").contains("://") && !s.attr("href").startsWith(urlDomainPart)) length
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