import graph.{DFSAlgorithm, DirectedEdge, DirectedGraph, Graph, UndirectedEdge, UnDirectedGraph}
import org.scalatest.flatspec.AnyFlatSpec

class DFSAlgorithmTest extends AnyFlatSpec {
  "DFSAlgorithm" should "perform DFS correctly on a directed graph" in {
    val vertices = Set("0", "1", "2", "3")
    val edges = Set(
      DirectedEdge("0", "1"), DirectedEdge("0", "2"),
      DirectedEdge("1", "2"),
      DirectedEdge("2", "0"), DirectedEdge("2", "3"),
      DirectedEdge("3", "3")
    )
    val graphy = new DirectedGraph(vertices, edges)

    val result = DFSAlgorithm.dfs(graphy, "1")
    assert(result == List("1", "2", "0", "3"))
  }

  it should "perform DFS correctly on a directed graph (2)" in {
    val vertices = Set("0", "1", "2", "3")
    val edges = Set(
      DirectedEdge("2", "0"),
      DirectedEdge("0", "2"),
      DirectedEdge("1", "2"),
      DirectedEdge("0", "1"),
      DirectedEdge("3", "3"),
      DirectedEdge("1", "3")
    )
    val graphy = new DirectedGraph(vertices, edges)

    val result = DFSAlgorithm.dfs(graphy, "2")
    assert(result == List("2", "0", "1", "3"))
  }

  it should "perform DFS correctly on an undirected graph" in {
    val vertices = Set("0", "1", "2", "3", "4")
    val edges = Set(
      UndirectedEdge("0", "1"), UndirectedEdge("0", "2"), UndirectedEdge("0", "3"),
      UndirectedEdge("2", "3"), UndirectedEdge("2", "4")
    )
    val graphy = new UnDirectedGraph(vertices, edges)

    val result = DFSAlgorithm.dfs(graphy, "0")
    assert(result == List("0", "1", "2", "4", "3"))
  }
}
