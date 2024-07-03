package graph

case class DirectedEdge[V](from: V, to: V)

final case class DirectedGraph[V](val vertices: Set[V], val edges: Set[DirectedEdge[V]]) extends Graph[V, DirectedEdge[V]] {
  def neighbors(vertex: V): Set[V] = edges.collect { case DirectedEdge(`vertex`, to) => to }

  def addEdge(edge: DirectedEdge[V]): DirectedGraph[V] = new DirectedGraph(vertices ++ Set(edge.from, edge.to), edges + edge)

  def removeEdge(edge: DirectedEdge[V]): DirectedGraph[V] = new DirectedGraph(vertices, edges - edge)
}

//rajouter objet companion pour créer un objet usuel (pour initier graphe vide)