package graph

import scala.collection.mutable

object DijkstraAlgorithm {
  def dijkstra[V](graph: WeightedGraph[V], source: V): Map[V, Double] = {
    // Initialize distances to all vertices as infinity, except the source
    val distances = mutable.Map(graph.vertices.toSeq.map(v => v -> Double.PositiveInfinity)*)
    distances(source) = 0
    // Initial vertices
    val M = mutable.Set(graph.vertices.toSeq*)
    // Final shortest path
    val CC = mutable.Set[V]()

    while (M.nonEmpty) {
      val (j, jDistance) = M.map(v => (v, distances(v))).minBy(_._2)
      M -= j
      CC += j
      // Update distances
      for (i <- graph.neighbors(j) if M.contains(i)) {
        val edgeWeight = graph.edgeWeight(j, i).getOrElse(Double.PositiveInfinity)
        distances(i) = math.min(distances(i), jDistance + edgeWeight)
      }
    }

    distances.toMap
  }
}
