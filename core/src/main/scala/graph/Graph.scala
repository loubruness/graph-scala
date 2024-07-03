package graph

trait Graph [V, E] {
  def vertices: Set[V]
  def edges: Set[E]
  def neighbors(vertex: V): Set[V]
  def addEdge(edge: E): Graph[V, E]
  def removeEdge(edge: E): Graph[V, E]
  def isEmpty(): Boolean = vertices.isEmpty && edges.isEmpty
}
