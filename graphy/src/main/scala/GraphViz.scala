package graph

implicit class GraphViz[V](graph: Graph[V, DirectedEdge[V]]) {
  def toGraphViz: String = {
    val verticesStr = graph.vertices.map(v => s""""$v"""").mkString("\n")
    val edgesStr = graph.edges.map(e => s""""${e.from}" -> "${e.to}"""").mkString("\n")
    s"""
       |digraph G {
       |$verticesStr
       |$edgesStr
       |}
     """.stripMargin
  }
}
