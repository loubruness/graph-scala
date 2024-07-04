package graph

import scala.collection.mutable

object TopologicalSortAlgorithm {
  def topologicalSort[V](graph: DirectedGraph[V] | WeightedGraph[V]): List[V] = {
    val visited = mutable.Stack[V]()

    def visit(vertex: V): Unit =
      if (!visited.contains(vertex)) {
        graph.neighbors(vertex).foreach(visit)
        visited.push(vertex) // Add this vertex to the visited stack
      }

    graph.vertices.foreach(visit) // DFS for each vertex
    visited.toList // Return the visited stack
  }

}
