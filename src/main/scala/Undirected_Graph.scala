package graph

case class UndirectedEdge[V](v1: V, v2: V)

final case class Undirected_Graph[V](val vertices: Set[V], val edges: Set[UndirectedEdge[V]]) extends Graph[V, UndirectedEdge[V]] {
  def neighbors(vertex: V): Set[V] = edges.collect { case UndirectedEdge(`vertex`, other) => other } ++ edges.collect { case UndirectedEdge(other, `vertex`) => other }

  def addEdge(edge: UndirectedEdge[V]): Undirected_Graph[V] = new Undirected_Graph(vertices ++ Set(edge.v1, edge.v2), edges + edge)

  def removeEdge(edge: UndirectedEdge[V]): Undirected_Graph[V] = new Undirected_Graph(vertices, edges - edge)
}