package models
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

case class PageStatistic(hash: String, pageURI: String) {
  val url = pageURI
  var doc: Document = Jsoup.connect(url).get()
  val strLen = doc.outerHtml().length()
}