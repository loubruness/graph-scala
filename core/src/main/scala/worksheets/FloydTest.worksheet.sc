import graph.*

import scala.collection.mutable


def floyd[V](graph: WeightedGraph[V]): Map[(V, V), Double] = {
  // Define our default distance
  val dist = scala.collection.mutable.Map[(V, V), Double]().withDefaultValue(Double.PositiveInfinity)
  
  // Fill in the distances that we know
  graph.edges.foreach { case WeightedEdge(from, to, weight) => dist((from, to)) = weight }
  
  //Check if the distances we have until now are the smallest
  for {k <- graph.vertices ; i <- graph.vertices ; j <- graph.vertices} 
  {
    if (dist((i, j)) > dist((i, k)) + dist((k, j)))
      dist((i, j)) = dist((i, k)) + dist((k, j))
  }

  // Make sure that the distance between a node and itself is 0
  graph.vertices.foreach(v => dist((v, v)) = 0.0)

  dist.toMap
}

// TEST
val vertices = Set("A", "B", "C", "D")
val edges = Set(
  WeightedEdge("A", "B", 1), 
  WeightedEdge("A", "C",2),
  WeightedEdge("B", "C",1),
  WeightedEdge("B", "D", 3), 
  WeightedEdge("C", "D", 2),
  WeightedEdge("D", "D", 5))
val graphy = new WeightedGraph(vertices, edges)

println(floyd(graphy))
graphy.toGraphVizWeight
// HashMap((C,D) -> 2.0, (A,C) -> 2.0, (A,D) -> 4.0, (B,C) -> 1.0, (B,B) -> 0.0, (D,D) -> 0.0, (C,C) -> 0.0, (A,B) -> 1.0, (A,A) -> 0.0, (B,D) -> 3.0)

val verticesx = Set("A", "B", "C", "D")
val edgesx = Set(
  WeightedEdge("A", "B", 1),
  WeightedEdge("A", "C", 4),
  WeightedEdge("B", "C", 2),
  WeightedEdge("B", "D", 5),
  WeightedEdge("C", "D", 1)
)
val graphx = WeightedGraph(verticesx, edgesx)

println(floyd(graphx)) 
graphx.toGraphVizWeight
// HashMap((C,D) -> 1.0, (A,C) -> 3.0, (A,D) -> 4.0, (B,C) -> 2.0, (B,B) -> 0.0, (D,D) -> 0.0, (C,C) -> 0.0, (A,B) -> 1.0, (A,A) -> 0.0, (B,D) -> 3.0)