import graph.{DirectedEdge, DirectedGraph, Graph, GraphViz, UndirectedEdge, UndirectedGraph}

import scala.collection.mutable

def bfs[V](graph: Graph[V, DirectedEdge[V]] | Graph[V, UndirectedEdge[V]], start: V): List[V] = {
  @scala.annotation.tailrec
  def visit(queue: List[V], visited: Set[V], result: List[V]): List[V] = queue match {
    case Nil => result.reverse
    // head is the current vertex
    case head :: tail if visited.contains(head) => visit(tail, visited, result)
    case head :: tail =>
      val newNeighbors = graph.neighbors(head).filterNot(visited)
      visit(tail ++ newNeighbors, visited + head, head :: result)
  }
  visit(List(start), Set.empty[V], Nil)
}

// TEST
val vertices = Set("0", "1", "2", "3")
val edges = Set(
  DirectedEdge("0", "1"), DirectedEdge("0", "2"),
  DirectedEdge("1", "2"),
  DirectedEdge("2", "0"), DirectedEdge("2", "3"),
  DirectedEdge("3", "3"))
val graphy = new DirectedGraph(vertices, edges)

println(graphy.toGraphViz)
println(bfs(graphy, "1"))

val vertices2 = Set("0", "1", "2", "3")
val edges2 = Set(
  DirectedEdge("2", "0"),
  DirectedEdge("0", "2"),
  DirectedEdge("1", "2"),
  DirectedEdge("0", "1"),
  DirectedEdge("3", "3"),
  DirectedEdge("1", "3")
)
val graphy2 = new DirectedGraph(vertices2, edges2)

println(graphy2.toGraphViz)
println(bfs(graphy2, "2"))

// TEST ON UNDIRECTED GRAPH
val ugvert = Set("0", "1", "2", "3", "4")
val ugedge = Set(
  UndirectedEdge("0", "1"), UndirectedEdge("0", "2"), UndirectedEdge("0", "3"),
  UndirectedEdge("2", "3"), UndirectedEdge("2", "4")
)
val ug = UndirectedGraph(ugvert, ugedge)
println(ug.vertices)
println(ug.edges)
println(bfs(ug, "0"))

val dgvert = Set("0", "1", "2", "3", "4")
val dgedge = Set(
  DirectedEdge("0", "1"), DirectedEdge("0", "2"),
  DirectedEdge("1", "3"), DirectedEdge("1", "4"),
  DirectedEdge("4", "2")
)
val dg = DirectedGraph(dgvert, dgedge)
println(bfs(dg, "0"))
