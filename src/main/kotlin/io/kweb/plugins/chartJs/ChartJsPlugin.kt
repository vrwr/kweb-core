package io.kweb.plugins.chartJs

import io.kweb.Kweb
import io.kweb.dom.element.creation.tags.canvas
import io.kweb.dom.element.new
import io.kweb.plugins.KwebPlugin
import io.kweb.plugins.chartJs.ChartType.line
import org.jsoup.nodes.Document

fun main(args: Array<String>) {
    Kweb(port = 5252, plugins = listOf(chartJs), buildPage = {
        doc.body.new {
            Chart(canvas(400, 300), ChartConfig(
                    type = line,
                    data = ChartData(
                            labels = listOf("January", "February", "March", "April", "May", "June", "July"),
                            datasets = listOf(DataSet(
                                    label = "My first dataset",
                                    dataList = DataList.Numbers(0, 10, 5, 2, 20, 30, 45)
                            ))
                    ))
            )
        }
    })
    Thread.sleep(100000)
}

class ChartJsPlugin : KwebPlugin() {
    override fun decorate(doc : Document) {
        doc.head().appendElement("script")
                .attr("type", "text/javascript")
                .attr("src", "https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.min.js")
    }
}

val chartJs = ChartJsPlugin()

