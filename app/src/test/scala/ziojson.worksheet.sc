
import zio._
import graph._
import zio.json._
import JsonCodecs._



val vertices = Set("0", "1", "2", "3")
val edges = Set(
  DirectedEdge("0", "1"), DirectedEdge("0", "2"),
  DirectedEdge("1", "2"),
  DirectedEdge("2", "0"), DirectedEdge("2", "3"),
  DirectedEdge("3", "3"))
val graphy = DirectedGraph(vertices, edges)

// Test toGraphViz (suppose that you have defined this method)
println(graphy.toGraphViz)

// Test JSON encoding and decoding
val json = graphy.toJson
println(json)

val decodedGraph = json.fromJson[DirectedGraph[String]]
println(decodedGraph)
