package graph
import zio._
import zio.json._

implicit val directedEdgeEncoder: JsonEncoder[DirectedEdge[String]] = DeriveJsonEncoder.gen[DirectedEdge[String]]
implicit val directedEdgeDecoder: JsonDecoder[DirectedEdge[String]] = DeriveJsonDecoder.gen[DirectedEdge[String]]

implicit val directedGraphEncoder: JsonEncoder[DirectedGraph[String]] = DeriveJsonEncoder.gen[DirectedGraph[String]]
implicit val directedGraphDecoder: JsonDecoder[DirectedGraph[String]] = DeriveJsonDecoder.gen[DirectedGraph[String]]
