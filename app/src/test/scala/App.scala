import zio._
import graph._
import zio.json._
import app.JsonCodecs._
import org.scalatest.flatspec.AnyFlatSpec

class AppTest extends AnyFlatSpec {
  "DirectedGraph" should "be encoded in Json correctly" in {
    val vertices = Set("0", "1", "2", "3")
    val edges = Set(
      DirectedEdge("0", "1"), DirectedEdge("0", "2"),
      DirectedEdge("1", "2"),
      DirectedEdge("2", "0"), DirectedEdge("2", "3"),
      DirectedEdge("3", "3"))
    val graphy = DirectedGraph(vertices, edges)

    val json = graphy.toJson
    val jsonExpected ="""{"vertices":["0","1","2","3"],"edges":[{"from":"2","to":"3"},{"from":"0","to":"1"},{"from":"0","to":"2"},{"from":"3","to":"3"},{"from":"2","to":"0"},{"from":"1","to":"2"}]}"""
    assert(json == jsonExpected)
  }

  it should "be decoded from Json correctly" in {
    val json = """{"vertices":["0","1","2","3"],"edges":[{"from":"2","to":"3"},{"from":"0","to":"1"},{"from":"0","to":"2"},{"from":"3","to":"3"},{"from":"2","to":"0"},{"from":"1","to":"2"}]}"""
    val decodedGraph = json.fromJson[DirectedGraph[String]]

    assert(decodedGraph == Right(DirectedGraph(Set("0", "1", "2", "3"),Set(DirectedEdge("2","3"), DirectedEdge("0","1"), DirectedEdge("0","2"), DirectedEdge("3","3"), DirectedEdge("2","0"), DirectedEdge("1","2")))))
  }

  "UndirectedGraph" should "be encoded in Json correctly" in {
    val vertices = Set("0", "1", "2", "3")
    val edges = Set(
      UndirectedEdge("0", "1"), UndirectedEdge("0", "2"),
      UndirectedEdge("1", "2"),
      UndirectedEdge("2", "0"), UndirectedEdge("2", "3"),
      UndirectedEdge("3", "3"))
    val graphy = UndirectedGraph(vertices, edges)

    val json = graphy.toJson
    val jsonExpected ="""{"vertices":["0","1","2","3"],"edges":[{"v1":"0","v2":"1"},{"v1":"2","v2":"3"},{"v1":"1","v2":"2"},{"v1":"3","v2":"3"},{"v1":"0","v2":"2"},{"v1":"2","v2":"0"}]}"""
    assert(json == jsonExpected)
  }

  it should "be decoded from Json correctly" in {
    val json = """{"vertices":["0","1","2","3"],"edges":[{"v1":"0","v2":"1"},{"v1":"2","v2":"3"},{"v1":"1","v2":"2"},{"v1":"3","v2":"3"},{"v1":"0","v2":"2"},{"v1":"2","v2":"0"}]}"""
    val decodedGraph = json.fromJson[UndirectedGraph[String]]

    assert(decodedGraph == Right(UndirectedGraph(Set("0","1", "2", "3"),Set(UndirectedEdge("0","1"), UndirectedEdge("2","3"), UndirectedEdge("1","2"), UndirectedEdge("3","3"), UndirectedEdge("0","2"), UndirectedEdge("2","0")))))
  }

  "WeightedGraph" should "be encoded in Json correctly" in {
    val vertices = Set("0", "1", "2", "3")
    val edges = Set(
      WeightedEdge("0", "1", 5), WeightedEdge("0", "2", 4),
      WeightedEdge("1", "2", 3),
      WeightedEdge("2", "0", 2), WeightedEdge("2", "3", 1),
      WeightedEdge("3", "3", 0))
    val graphy = WeightedGraph(vertices, edges)

    val json = graphy.toJson
    val jsonExpected ="""{"vertices":["0","1","2","3"],"edges":[{"from":"1","to":"2","weight":3.0},{"from":"3","to":"3","weight":0.0},{"from":"2","to":"0","weight":2.0},{"from":"0","to":"1","weight":5.0},{"from":"0","to":"2","weight":4.0},{"from":"2","to":"3","weight":1.0}]}"""
    assert(json == jsonExpected)
  }

  it should "be decoded from Json correctly" in {
    val json = """{"vertices":["0","1","2","3"],"edges":[{"from":"1","to":"2","weight":3.0},{"from":"3","to":"3","weight":0.0},{"from":"2","to":"0","weight":2.0},{"from":"0","to":"1","weight":5.0},{"from":"0","to":"2","weight":4.0},{"from":"2","to":"3","weight":1.0}]}"""
    val decodedGraph = json.fromJson[WeightedGraph[String]]

    assert(decodedGraph == Right(WeightedGraph(Set("0", "1", "2", "3"),Set(WeightedEdge("1","2",3.0), WeightedEdge("3","3",0.0), WeightedEdge("2","0",2.0), WeightedEdge("0","1",5.0), WeightedEdge("0","2",4.0), WeightedEdge("2","3",1.0)))))
  }
}
