package graph

import scala.collection.mutable

object BFSAlgorithm {
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

}
