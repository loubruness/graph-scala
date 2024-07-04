package graph

implicit class UndirectedGraphViz[V](graph: Graph[V, UndirectedEdge[V]]) {
  def toGraphVizUndirected: String = {
    val verticesStr = graph.vertices.foldLeft("")((acc, v) => acc + s""""$v"\n""")
    val edgesStr = graph.edges.foldLeft("")((acc, e) => acc + s""""${e.v1}" -- "${e.v2}"\n""")
    s"""
       |graph G {
       |$verticesStr
       |$edgesStr
       |}
     """.stripMargin
  }
}
