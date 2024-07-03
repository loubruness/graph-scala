// import graph.{DFSAlgorithm, WeightedEdge, WeightedGraph}
// import org.scalatest.flatspec.AnyFlatSpec

// class DijkstraAlgorithmTest extends AnyFlatSpec {
//   "DijkstraAlgorithm" should "perform Djisktra correctly on a weighted graph" in {
//     val vertices = Set("A", "B", "C", "D")
//     val edges = Set(
//       WeightedEdge("A", "B", 1), WeightedEdge("A", "C",2),
//       WeightedEdge("B", "C",1),
//       WeightedEdge("C", "A", 3), WeightedEdge("C", "D", 2),
//       WeightedEdge("D", "D", 5))
//     val graphy = new WeightedGraph(vertices, edges)

//     val result = DijkstraAlgorithm.dijkstra(graphy, "A")
//     assert(result == Map(A -> 0.0, B -> 1.0, C -> 2.0, D -> 4.0))
//   }

// }
