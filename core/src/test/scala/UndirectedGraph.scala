import graph.{UndirectedGraph, UndirectedEdge}
import org.scalatest.flatspec.AnyFlatSpec

class GraphUndirectedSpec extends AnyFlatSpec {
  "An UndirectedGraph" should "add edges correctly" in {
    val graph = new UndirectedGraph(Set("A", "B"), Set(UndirectedEdge("A", "B")))
    val newGraph = graph.addEdge(UndirectedEdge("B", "C"))
    assert(newGraph.edges == Set(UndirectedEdge("A", "B"), UndirectedEdge("B", "C")))
  }

  it should "remove edges correctly" in {
    val graph = new UndirectedGraph(Set("A", "B"), Set(UndirectedEdge("A", "B")))
    val newGraph = graph.removeEdge(UndirectedEdge("A", "B"))
    assert(newGraph.edges.isEmpty)
  }

  it should "have correct neighbors" in {
    val graph = new UndirectedGraph(Set("A", "B", "C"), Set(UndirectedEdge("A", "B"), UndirectedEdge("B", "C"), UndirectedEdge("C", "A")));
    val neighbors = graph.neighbors("A");
    assert(neighbors == Set("B", "C"))
  }

  it should "be empty" in {
    val graph = new UndirectedGraph(Set(), Set())
    assert(graph.isEmpty())
  }
}