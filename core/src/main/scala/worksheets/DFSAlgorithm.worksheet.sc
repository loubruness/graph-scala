import graph.{DirectedEdge, DirectedGraph, Graph, GraphViz, UndirectedEdge, UndirectedGraph}

import scala.collection.mutable

// Easier to understand version
def dfs[V](graph: Graph[V, DirectedEdge[V]] | Graph[V, UndirectedEdge[V]], start: V): List[V] = {
  var visited = mutable.Stack[V]()

  def visit(vertex: V): List[V] =
    var result = List[V]()
    val neighbors = graph.neighbors(vertex)

    // Visit the current vertex
    if !visited.contains(vertex) then
      visited = visited :+ vertex
      result = result :+ vertex

    // For all neighbors, visit them if they were not visited yet
    for
      n <- neighbors
      if !visited.contains(n)
    do
      visited = visited :+ n
      result = (result :+ n) ++ visit(n)
    result

  visit(start)
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
println(dfs(graphy, "1"))

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
println(dfs(graphy2, "2"))

// TEST ON UNDIRECTED GRAPH
val ugvert = Set("0", "1", "2", "3", "4")
val ugedge = Set(
  UndirectedEdge("0", "1"), UndirectedEdge("0", "2"), UndirectedEdge("0", "3"),
  UndirectedEdge("2", "3"), UndirectedEdge("2", "4")
)
val ug = UndirectedGraph(ugvert, ugedge)
println(ug.vertices)
println(ug.edges)
println(dfs(ug, "0"))
