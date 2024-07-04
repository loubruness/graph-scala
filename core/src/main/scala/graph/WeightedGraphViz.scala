package graph


implicit class WeightedGraphViz[V](graph: Graph[V, WeightedEdge[V]]) {
  def toGraphVizWeight: String = {
    val verticesStr = graph.vertices.foldLeft("")((acc, v) => acc + s""""$v"\n""")
    val edgesStr = graph.edges.foldLeft("")((acc, e) => acc + s"""   "${e.from}" -> "${e.to}" [weight="${e.weight}"]    \n""")
    s"""
       |digraph G {
       |$verticesStr
       |$edgesStr
       |}
     """.stripMargin
  }
}
