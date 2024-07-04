import graph.*
import scala.collection.mutable.HashMap
import org.scalatest.flatspec.AnyFlatSpec

class TopologicalSortAlgorithmTest extends AnyFlatSpec {
  "TopologicalSortAlgorithm" should "perform TopologicalSort correctly on a directed acyclic graph" in {
    val vertices = Set("0", "1", "2", "3", "4", "5")
    val edges = Set(
      DirectedEdge("5", "2"), DirectedEdge("5", "0"),
      DirectedEdge("4", "1"), DirectedEdge("4", "0"),
      DirectedEdge("2", "3"),
      DirectedEdge("3", "1"))
    val graphy = DirectedGraph(vertices, edges)
    
    val result = TopologicalSortAlgorithm.topologicalSort(graphy)
    assert(result == List("5", "2", "3", "4", "0", "1"))
  }

  it should "perform TopologicalSort correctly on a directed acyclic graph (2)" in {
    val vertices = Set("0", "1", "2", "3", "4", "5", "6", "7")
    val edges = Set(
      DirectedEdge("0", "3"), DirectedEdge("0", "4"),
      DirectedEdge("1", "5"),
      DirectedEdge("2", "5"), DirectedEdge("2", "6"),
      DirectedEdge("3", "7"),
      DirectedEdge("5", "6"), DirectedEdge("5", "7")
    )
    val graphy = DirectedGraph(vertices, edges)
    
    val result = TopologicalSortAlgorithm.topologicalSort(graphy)
    assert(result == List("1", "2", "0", "3", "5", "7", "6", "4"))
  }
}
