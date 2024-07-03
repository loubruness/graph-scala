import graph.{Graph, DirectedGraph, DirectedEdge, GraphViz}

val vertices = Set("A", "B", "C")
val edges = Set(DirectedEdge("A", "B"), DirectedEdge("B", "C"))
val graphy = new DirectedGraph(vertices, edges)

println(graphy.vertices)
println(graphy.edges)
println(graphy.neighbors("A"))
graphy.toGraphViz