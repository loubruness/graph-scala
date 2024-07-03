import graph.{Graph, Directed_Graph, DirectedEdge, GraphViz}

val vertices = Set("A", "B", "C")
val edges = Set(DirectedEdge("A", "B"), DirectedEdge("B", "C"))
val graphy = new Directed_Graph(vertices, edges)

println(graphy.vertices)
println(graphy.edges)
println(graphy.neighbors("A"))
println(graphy.toGraphViz)