import graph.*

import scala.collection.mutable


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


// TEST
val vertices = Set("A", "B", "C", "D")
val edges = Set(
  WeightedEdge("A", "B", 1), WeightedEdge("A", "C",2),
  WeightedEdge("B", "C",1),
  WeightedEdge("C", "A", 3), WeightedEdge("C", "D", 2),
  WeightedEdge("D", "D", 5))
val graphy = new WeightedGraph(vertices, edges)

println(dijkstra(graphy, "A"))
// Map(A -> 0.0, B -> 1.0, C -> 2.0, D -> 4.0)

val verticesx = Set("A", "B", "C", "D")
val edgesx = Set(
  WeightedEdge("A", "B", 1),
  WeightedEdge("A", "C", 4),
  WeightedEdge("B", "C", 2),
  WeightedEdge("B", "D", 5),
  WeightedEdge("C", "D", 1)
)

val graphx = WeightedGraph(verticesx, edgesx)

println(dijkstra(graphx, "A")) 
// Map(A -> 0.0, B -> 1.0, C -> 3.0, D -> 4.0)