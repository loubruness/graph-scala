import graph.{WeightedGraph, WeightedEdge}
import org.scalatest.flatspec.AnyFlatSpec

class DFSSpec extends AnyFlatSpec {
  "A WeightedGraph" should "add edges correctly" in {
    val graph = new WeightedGraph(Set("A", "B"), Set(WeightedEdge("A", "B", 8.0)))
    val newGraph = graph.addEdge(WeightedEdge("B", "C", 3.0))
    assert(newGraph.edges == Set(WeightedEdge("A", "B", 8.0), WeightedEdge("B", "C", 3.0)))
  }

  it should "remove edges correctly" in {
    val graph = new WeightedGraph(Set("A", "B"), Set(WeightedEdge("A", "B", 8.0)))
    val newGraph = graph.removeEdge(WeightedEdge("A", "B", 8.0))
    assert(newGraph.edges.isEmpty)
  }

  it should "have correct neighbors" in {
    val graph = new WeightedGraph(Set("A", "B", "C"), Set(WeightedEdge("A", "B", 8.0), WeightedEdge("B", "C", 3.0), WeightedEdge("C", "A", 5.0)));
    val neighbors = graph.neighbors("A");
    assert(neighbors == Set("B"))
  }

  it should "be empty" in {
    val graph = new WeightedGraph(Set(), Set())
    assert(graph.isEmpty())
  }
}