
package app
import zio.json._
import graph.{DirectedEdge, DirectedGraph, UndirectedEdge, UndirectedGraph, WeightedEdge, WeightedGraph}



object JsonCodecs {
  // Define JSON codecs for the graph types

  // Directed
  implicit val directedEdgeEncoder: JsonEncoder[DirectedEdge[String]] = DeriveJsonEncoder.gen[DirectedEdge[String]]
  implicit val directedEdgeDecoder: JsonDecoder[DirectedEdge[String]] = DeriveJsonDecoder.gen[DirectedEdge[String]]

  implicit val directedGraphEncoder: JsonEncoder[DirectedGraph[String]] = DeriveJsonEncoder.gen[DirectedGraph[String]]
  implicit val directedGraphDecoder: JsonDecoder[DirectedGraph[String]] = DeriveJsonDecoder.gen[DirectedGraph[String]]

  // Undirected
  implicit val undirectedEdgeEncoder: JsonEncoder[UndirectedEdge[String]] = DeriveJsonEncoder.gen[UndirectedEdge[String]]
  implicit val undirectedEdgeDecoder: JsonDecoder[UndirectedEdge[String]] = DeriveJsonDecoder.gen[UndirectedEdge[String]]

  implicit val undirectedGraphEncoder: JsonEncoder[UndirectedGraph[String]] = DeriveJsonEncoder.gen[UndirectedGraph[String]]
  implicit val undirectedGraphDecoder: JsonDecoder[UndirectedGraph[String]] = DeriveJsonDecoder.gen[UndirectedGraph[String]]

  // Weighted
  implicit val weightedEdgeEncoder: JsonEncoder[WeightedEdge[String]] = DeriveJsonEncoder.gen[WeightedEdge[String]]
  implicit val weightedEdgeDecoder: JsonDecoder[WeightedEdge[String]] = DeriveJsonDecoder.gen[WeightedEdge[String]]

  implicit val weightedGraphEncoder: JsonEncoder[WeightedGraph[String]] = DeriveJsonEncoder.gen[WeightedGraph[String]]
  implicit val weightedGraphDecoder: JsonDecoder[WeightedGraph[String]] = DeriveJsonDecoder.gen[WeightedGraph[String]]
}