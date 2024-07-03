package graph

case class DirectedEdge[V](from: V, to: V)

class Directed_Graph[V](val vertices: Set[V], val edges: Set[DirectedEdge[V]]) extends Graph[V, DirectedEdge[V]] {
  def neighbors(vertex: V): Set[V] = edges.collect { case DirectedEdge(`vertex`, to) => to }

  def addEdge(edge: DirectedEdge[V]): Directed_Graph[V] = new Directed_Graph(vertices ++ Set(edge.from, edge.to), edges + edge)

  def removeEdge(edge: DirectedEdge[V]): Directed_Graph[V] = new Directed_Graph(vertices, edges - edge)
}