package graph

case class WeightedEdge[V](from: V, to: V, weight: Double)

class Weighted_Graph[V](val vertices: Set[V], val edges: Set[WeightedEdge[V]]) extends Graph[V, WeightedEdge[V]] {
  def neighbors(vertex: V): Set[V] = edges.collect { case WeightedEdge(`vertex`, to, _) => to }

  def addEdge(edge: WeightedEdge[V]): Weighted_Graph[V] = new Weighted_Graph(vertices ++ Set(edge.from, edge.to), edges + edge)

  def removeEdge(edge: WeightedEdge[V]): Weighted_Graph[V] = new Weighted_Graph(vertices, edges - edge)
}