package graph

import scala.collection.mutable

object DFSAlgorithm {
  def dfs[V](graph: Graph[V, DirectedEdge[V]] | Graph[V, UndirectedEdge[V]], start: V): List[V] = {
    var visited = mutable.Stack[V]()

    def visit(vertex: V): List[V] = {
      var result = List[V]()
      val neighbors = graph.neighbors(vertex)

      // Visit the current vertex
      if (!visited.contains(vertex)) {
        visited = visited :+ vertex
        result = result :+ vertex
      }

      // For all neighbors, visit them if they were not visited yet
      for {
        n <- neighbors
        if !visited.contains(n)
      } {
        visited = visited :+ n
        result = (result :+ n) ++ visit(n)
      }
      result
    }

    visit(start)
  }
}
