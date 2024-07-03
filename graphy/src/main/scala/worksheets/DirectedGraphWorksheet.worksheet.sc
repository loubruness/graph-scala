import graph.{Directed_Graph, DirectedEdge}

val vertices = Set("A", "B", "C")
val edges = Set(DirectedEdge("A", "B"), DirectedEdge("B", "C"))
val graphy = new Directed_Graph(vertices, edges)

println(graphy.vertices)
println(graphy.edges)
println(graphy.neighbors("A"))
println(graphy.addEdge(DirectedEdge("C", "A")).edges)
println(graphy.removeEdge(DirectedEdge("B", "C")).edges)
println(graphy.edges)
println(graphy.neighbors("B"))