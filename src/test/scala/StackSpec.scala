import graph.{DirectedGraph, DirectedEdge}
import org.scalatest.flatspec.AnyFlatSpec

class GraphSpec extends AnyFlatSpec {
  "A DirectedGraph" should "add edges correctly" in {
    val graph = new DirectedGraph(Set("A", "B"), Set(DirectedEdge("A", "B")))
    val newGraph = graph.addEdge(DirectedEdge("B", "C"))
    assert(newGraph.edges == Set(DirectedEdge("A", "B"), DirectedEdge("B", "C")))
  }

  it should "remove edges correctly" in {
    val graph = new DirectedGraph(Set("A", "B"), Set(DirectedEdge("A", "B")))
    val newGraph = graph.removeEdge(DirectedEdge("A", "B"))
    assert(newGraph.edges.isEmpty)
  }

  it should "have correct neighbors" in {
    val graph = new DirectedGraph(Set("A", "B", "C"), Set(DirectedEdge("A", "B"), DirectedEdge("B", "C"), DirectedEdge("C", "A")));
    val neighbors = graph.neighbors("A");
    assert(neighbors == Set("B"))
  }

  it should "be empty" in {
    val graph = new DirectedGraph(Set(), Set())
    assert(graph.isEmpty())
  }
}