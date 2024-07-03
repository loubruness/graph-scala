import graph.{Directed_Graph, DirectedEdge}
import org.scalatest.flatspec.AnyFlatSpec

class GraphSpec extends AnyFlatSpec {
  "A DirectedGraph" should "add edges correctly" in {
    val graph = new Directed_Graph(Set("A", "B"), Set(DirectedEdge("A", "B")))
    val newGraph = graph.addEdge(DirectedEdge("B", "C"))
    assert(newGraph.edges == Set(DirectedEdge("A", "B"), DirectedEdge("B", "C")))
  }

  it should "remove edges correctly" in {
    val graph = new Directed_Graph(Set("A", "B"), Set(DirectedEdge("A", "B")))
    val newGraph = graph.removeEdge(DirectedEdge("A", "B"))
    assert(newGraph.edges.isEmpty)
  }
}