package app

import zio._
import zio.Console._
import graph._
import zio.json._
import graph.DFSAlgorithm._
import java.io.IOException
import zio.stm.ZSTM
import JsonCodecs._
import app.Main.addEdgeMenu

case class AppState(
  directedGraph: Ref[DirectedGraph[String]],
  undirectedGraph: Ref[UndirectedGraph[String]],
  weightedGraph: Ref[WeightedGraph[String]],
  graphType: GraphType
){
  def ref : Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]] ={
    graphType match
      case GraphType.Directed => directedGraph
      case GraphType.Undirected => undirectedGraph
      case GraphType.Weighted => weightedGraph
  }
  def graph : UIO[DirectedGraph[String] | UndirectedGraph[String] | WeightedGraph[String]] = ref.get

  def addEdge(source:String, destination:String, weight: Option[String] = None) : UIO[Unit] = {
    for {
      _<- graphType match
      case GraphType.Directed => 
        directedGraph.update(graph => graph.addEdge(DirectedEdge(source, destination)))
      case GraphType.Undirected =>
          undirectedGraph.update(graph => graph.addEdge(UndirectedEdge(source, destination)))
      case GraphType.Weighted =>
        weight match
          case Some(weight) => weightedGraph.update(graph => graph.addEdge(WeightedEdge(source, destination, weight.toDouble)))        
          case None => weightedGraph.update(graph => graph.addEdge(WeightedEdge(source, destination, 0)))   
      } yield ()
  }

  def removeEdge(source:String, destination:String) : UIO[Unit] = {
    for {
      _<- graphType match
      case GraphType.Directed => 
        directedGraph.update(graph => graph.removeEdge(DirectedEdge(source, destination)))
      case GraphType.Undirected =>
          undirectedGraph.update(graph => graph.removeEdge(UndirectedEdge(source, destination)))
      case GraphType.Weighted =>
        weightedGraph.update(graph => graph.removeEdge(WeightedEdge(source, destination, 0)))           
    } yield ()
  }

  def loadJson(json: String): UIO[Unit] = {
    val parseJson: UIO[Unit] = graphType match {
      case GraphType.Directed =>
        json.fromJson[DirectedGraph[String]] match {
          case Right(graph) => directedGraph.set(graph).unit
          case Left(error)  => ZIO.unit
        }
      case GraphType.Undirected =>
        json.fromJson[UndirectedGraph[String]] match {
          case Right(graph) => undirectedGraph.set(graph).unit
          case Left(error)  => ZIO.unit
        }
      case GraphType.Weighted =>
        json.fromJson[WeightedGraph[String]] match {
          case Right(graph) => weightedGraph.set(graph).unit
          case Left(error)  => ZIO.unit
        }
    }
    parseJson
  }

  def showJson: UIO[String] = {
    for {
      graph <- graph
      json = graph match
        case graph: DirectedGraph[String] => graph.toJson
        case graph: UndirectedGraph[String] => graph.toJson
        case graph: WeightedGraph[String] => graph.toJson
    } yield json
  }

  def graphViz : UIO[String] = {
    for{
      graph <- graph
      graphviz = graph.match
        case graph: DirectedGraph[String] => graph.toGraphVizDirected
        case graph: UndirectedGraph[String] => graph.toGraphVizUndirected
        case graph: WeightedGraph[String] => graph.toGraphVizWeight
    
    } yield graphviz

  }

    
}

enum GraphType:
  case Directed
  case Undirected
  case Weighted