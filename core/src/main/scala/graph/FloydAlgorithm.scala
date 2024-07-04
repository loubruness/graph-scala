package graph

import scala.collection.mutable

object FloydAlgorithm {
  def floyd[V](graph: WeightedGraph[V]): Map[(V, V), Double] = {
    // Define our default distance
    val dist = scala.collection.mutable.Map[(V, V), Double]().withDefaultValue(Double.PositiveInfinity)
    
    // Fill in the distances that we know
    graph.edges.foreach { case WeightedEdge(from, to, weight) => dist((from, to)) = weight }
    
    //Check if the distances we have until now are the smallest
    for {k <- graph.vertices ; i <- graph.vertices ; j <- graph.vertices} 
    {
      if (dist((i, j)) > dist((i, k)) + dist((k, j)))
        dist((i, j)) = dist((i, k)) + dist((k, j))
    }

    // Make sure that the distance between a node and itself is 0
    graph.vertices.foreach(v => dist((v, v)) = 0.0)

    dist.toMap
  } 

}
