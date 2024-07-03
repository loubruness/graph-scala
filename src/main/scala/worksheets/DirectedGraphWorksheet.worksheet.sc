import graph.{DirectedGraph, DirectedEdge}

val vertices = Set("A", "B", "C")
val edges = Set(DirectedEdge("A", "B"), DirectedEdge("B", "C"))
val graphy = new DirectedGraph(vertices, edges)

println(graphy.vertices)
println(graphy.edges)
println(graphy.neighbors("A"))
println(graphy.addEdge(DirectedEdge("C", "A")).edges)
println(graphy.removeEdge(DirectedEdge("B", "C")).edges)
println(graphy.edges)
println(graphy.neighbors("B"))