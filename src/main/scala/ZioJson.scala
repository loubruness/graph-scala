import graph.{Directed_Graph, DirectedEdge}
import zio._
import zio.json._

implicit val directedEdgeEncoder: JsonEncoder[DirectedEdge[String]] = DeriveJsonEncoder.gen[DirectedEdge[String]]
implicit val directedEdgeDecoder: JsonDecoder[DirectedEdge[String]] = DeriveJsonDecoder.gen[DirectedEdge[String]]

// implicit val directedGraphEncoder: JsonEncoder[Directed_Graph[String]] = DeriveJsonEncoder.gen[Directed_Graph[String]]
// implicit val directedGraphDecoder: JsonDecoder[Directed_Graph[String]] = DeriveJsonDecoder.gen[Directed_Graph[String]]
