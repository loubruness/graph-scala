import graph.*
import scala.collection.mutable.HashMap
import org.scalatest.flatspec.AnyFlatSpec

class FloydAlgorithmTest extends AnyFlatSpec {
  "FloydAlgorithm" should "perform Floyd correctly on a weighted graph" in {
    val vertices = Set("A", "B", "C", "D")
    val edges = Set(
      WeightedEdge("A", "B", 1), 
      WeightedEdge("A", "C",2),
      WeightedEdge("B", "C",1),
      WeightedEdge("B", "D", 3), 
      WeightedEdge("C", "D", 2),
      WeightedEdge("D", "D", 5))
    val graphy = new WeightedGraph(vertices, edges)
    
    val result = FloydAlgorithm.floyd(graphy)
    assert(result == HashMap(("C","D") -> 2.0, ("A","C") -> 2.0, ("A","D") -> 4.0, ("B","C") -> 1.0, ("B","B") -> 0.0, ("D","D") -> 0.0, ("C","C") -> 0.0, ("A","B") -> 1.0, ("A","A") -> 0.0, ("B","D") -> 3.0))
  }

  it should "perform Floyd correctly on a weighted graph (2)" in {
    val vertices = Set("A", "B", "C", "D")
    val edges = Set(
      WeightedEdge("A", "B", 1),
      WeightedEdge("A", "C", 4),
      WeightedEdge("B", "C", 2),
      WeightedEdge("B", "D", 5),
      WeightedEdge("C", "D", 1)
    )
    val graph = WeightedGraph(vertices, edges)
    
    val result = FloydAlgorithm.floyd(graph)
    assert(result == HashMap(("C","D") -> 1.0, ("A","C") -> 3.0, ("A","D") -> 4.0, ("B","C") -> 2.0, ("B","B") -> 0.0, ("D","D") -> 0.0, ("C","C") -> 0.0, ("A","B") -> 1.0, ("A","A") -> 0.0, ("B","D") -> 3.0))
  }
}
