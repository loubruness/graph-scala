package graph

implicit class DirectedGraphViz[V](graph: Graph[V, DirectedEdge[V]]) {
  def toGraphVizDirected: String = {
    val verticesStr = graph.vertices.foldLeft("")((acc, v) => acc + s""""$v"\n""")
    val edgesStr = graph.edges.foldLeft("")((acc, e) => acc + s""""${e.from}" -> "${e.to}"\n""")
    s"""
       |digraph G {
       |$verticesStr
       |$edgesStr
       |}
     """.stripMargin
  }
}
