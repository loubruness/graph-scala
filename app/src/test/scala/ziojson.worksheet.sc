import zio._
import graph._
import zio.json._
import app.JsonCodecs._


// Encode graph to JSON
// Directed graph
val vertices = Set("0", "1", "2", "3")
val edges = Set(
  DirectedEdge("0", "1"), DirectedEdge("0", "2"),
  DirectedEdge("1", "2"),
  DirectedEdge("2", "0"), DirectedEdge("2", "3"),
  DirectedEdge("3", "3"))
val graphy = DirectedGraph(vertices, edges)

// Test JSON encoding and decoding
val json = graphy.toJson
println(json)

val decodedGraph = json.fromJson[DirectedGraph[String]]
println(decodedGraph)

// Undirected graph
val vertices2 = Set("0", "1", "2", "3")
val edges2 = Set(
  UndirectedEdge("0", "1"), UndirectedEdge("0", "2"),
  UndirectedEdge("1", "2"),
  UndirectedEdge("2", "0"), UndirectedEdge("2", "3"),
  UndirectedEdge("3", "3"))
val graphy2 = UndirectedGraph(vertices2, edges2)

// Test JSON encoding and decoding
val json2 = graphy2.toJson
println(json2)

val decodedGraph2 = json2.fromJson[UndirectedGraph[String]]
println(decodedGraph2)

// Weighted graph
val vertices3 = Set("0", "1", "2", "3")
val edges3 = Set(
  WeightedEdge("0", "1", 5), WeightedEdge("0", "2", 4),
  WeightedEdge("1", "2", 3),
  WeightedEdge("2", "0", 2), WeightedEdge("2", "3", 1),
  WeightedEdge("3", "3", 0))
val graphy3 = WeightedGraph(vertices3, edges3)

// Test JSON encoding and decoding
val json3 = graphy3.toJson
println(json3)

val decodedGraph3 = json3.fromJson[WeightedGraph[String]]
println(decodedGraph3)






