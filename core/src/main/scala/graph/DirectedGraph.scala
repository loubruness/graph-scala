package graph

case class DirectedEdge[V](from: V, to: V)

case class DirectedGraph[V](vertices: Set[V], edges: Set[DirectedEdge[V]]) extends Graph[V, DirectedEdge[V]] {
  def neighbors(vertex: V): Set[V] = edges.collect { case DirectedEdge(`vertex`, to) => to }

  def addEdge(edge: DirectedEdge[V]): DirectedGraph[V] = new DirectedGraph(vertices ++ Set(edge.from, edge.to), edges + edge)

  def removeEdge(edge: DirectedEdge[V]): DirectedGraph[V] = new DirectedGraph(vertices, edges - edge)
}