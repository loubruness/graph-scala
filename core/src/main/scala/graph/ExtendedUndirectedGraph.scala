package graph

case class ExtUndirectedEdge[V](v1: V, v2: V) {
  def toDirectedEdges: Set[DirectedEdge[V]] = Set(DirectedEdge(v1, v2), DirectedEdge(v2, v1))
}

final class ExtUndirectedGraph[V](vertices: Set[V], edges: Set[ExtUndirectedEdge[V]]) extends DirectedGraph[V](vertices, edges.flatMap(_.toDirectedEdges)) {
  override def neighbors(vertex: V): Set[V] = edges.collect {
    case ExtUndirectedEdge(`vertex`, other) => other
    case ExtUndirectedEdge(other, `vertex`) => other
  }

  def addEdge(edge: ExtUndirectedEdge[V]): ExtUndirectedGraph[V] = {
    val newVertices = vertices ++ Set(edge.v1, edge.v2)
    val newEdges = edges + edge
    new ExtUndirectedGraph(newVertices, newEdges)
  }

  def removeEdge(edge: ExtUndirectedEdge[V]): ExtUndirectedGraph[V] = {
    val newEdges = edges - edge
    new ExtUndirectedGraph(vertices, newEdges)
  }
}
