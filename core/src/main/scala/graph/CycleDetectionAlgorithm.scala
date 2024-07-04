package graph

import scala.collection.mutable

object CycleDetectionAlgorithm {
  def detectCycle[V](graph: Graph[V, DirectedEdge[V]] | Graph[V, ExtUndirectedEdge[V]] | Graph[V, UndirectedEdge[V]] | Graph[V, WeightedEdge[V]]): Boolean = {

    // secondary function we use to establish the sets of potential routes that exist in the graph
    def visit(vertex: V, parent: Option[V], visited: Set[V]): Boolean = {

      //if the examined vertex is in one of the roads that have already been visited, there's a loop
      if (visited.contains(vertex)) true

      else {
        val newVisited = visited + vertex

        //continue for as long as there are neighbors left
        graph.neighbors(vertex).exists { neighbor =>
          // special precaution for undirected graphs, so we do not revisit the parent
          if (Some(neighbor) == parent) false
          else visit(neighbor, Some(vertex), newVisited)
        }
      }
    }

    graph.vertices.exists(vertex => visit(vertex, None, Set.empty[V]))
}

}
