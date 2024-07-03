package graph

case class WeightedEdge[V](from: V, to: V, weight: Double)

final case class WeightedGraph[V](val vertices: Set[V], val edges: Set[WeightedEdge[V]]) extends Graph[V, WeightedEdge[V]] {
  def neighbors(vertex: V): Set[V] = edges.collect { case WeightedEdge(`vertex`, to, _) => to }

  def addEdge(edge: WeightedEdge[V]): WeightedGraph[V] = new WeightedGraph(vertices ++ Set(edge.from, edge.to), edges + edge)

  def removeEdge(edge: WeightedEdge[V]): WeightedGraph[V] = new WeightedGraph(vertices, edges - edge)
}