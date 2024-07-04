import graph.{CycleDetectionAlgorithm, DirectedEdge, DirectedGraph, Graph, UndirectedEdge, UndirectedGraph, WeightedEdge, WeightedGraph, ExtUndirectedEdge, ExtUndirectedGraph}
import org.scalatest.flatspec.AnyFlatSpec

class CycleDetectionAlgorithmTest extends AnyFlatSpec {
  "CycleDetectionAlgorithm" should "detect a cycle correctly on a directed graph" in {
    val directedGraphCyclic = DirectedGraph(
      vertices = Set(1, 2, 3, 4),
      edges = Set(DirectedEdge(1, 2), DirectedEdge(2, 3), DirectedEdge(3, 4), DirectedEdge(4, 2))
    )

    val result = CycleDetectionAlgorithm.detectCycle(directedGraphCyclic)
    assert(result == true)
  }

  it should "detect the absence of cycle correctly on a directed graph" in {
    val directedGraphAcyclic = DirectedGraph(
      vertices = Set(1, 2, 3, 4),
      edges = Set(DirectedEdge(1, 2), DirectedEdge(2, 3), DirectedEdge(3, 4))
    )

    val result = CycleDetectionAlgorithm.detectCycle(directedGraphAcyclic)
    assert(result == false)
  }

  it should "detect a cycle correctly on an undirected graph" in {
    val undirectedGraphCyclic = ExtUndirectedGraph(
      vertices = Set(1, 2, 3, 4),
      edges = Set(ExtUndirectedEdge(1, 2), ExtUndirectedEdge(2, 3), ExtUndirectedEdge(3, 4), ExtUndirectedEdge(4, 1))
    )

    val result = CycleDetectionAlgorithm.detectCycle(undirectedGraphCyclic)
    assert(result == true)
  }

  it should "detect the absence of cycle correctly on an undirected graph" in {
    val undirectedGraphAcyclic = ExtUndirectedGraph(
      vertices = Set(1, 2, 3, 4),
      edges = Set(ExtUndirectedEdge(1, 2), ExtUndirectedEdge(2, 3), ExtUndirectedEdge(3, 4))
    )

    val result = CycleDetectionAlgorithm.detectCycle(undirectedGraphAcyclic)
    assert(result == false)
  }

  it should "detect a cycle correctly on a weighted graph" in {
    val weightedGraphCyclic = WeightedGraph(
      vertices = Set(1, 2, 3, 4),
      edges = Set(WeightedEdge(1, 2, 1.0), WeightedEdge(2, 3, 2.0), WeightedEdge(3, 4, 3.0), WeightedEdge(4, 2, 4.0))
    )

    val result = CycleDetectionAlgorithm.detectCycle(weightedGraphCyclic)
    assert(result == true)
  }

  it should "detect the absence of cycle correctly on a weighted graph" in {
    val weightedGraphAcyclic = WeightedGraph(
      vertices = Set(1, 2, 3, 4),
      edges = Set(WeightedEdge(1, 2, 1.0), WeightedEdge(2, 3, 2.0), WeightedEdge(3, 4, 3.0))
    )

    val result = CycleDetectionAlgorithm.detectCycle(weightedGraphAcyclic)
    assert(result == false)
  }
}
