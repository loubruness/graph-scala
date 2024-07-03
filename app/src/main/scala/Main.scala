package app

import zio._
import zio.Console._
import graph._
import graph.JsonCodecs._
import zio.json._
import graph.DFSAlgorithm._
import java.io.IOException

object Main extends ZIOAppDefault {

  def run: URIO[ZIOAppArgs & Scope, ExitCode] = program.exitCode

  val program: ZIO[Any, IOException, Unit] = for {
    ref <- Ref.make(DirectedGraph(Set.empty[String], Set.empty[DirectedEdge[String]]))
    _ <- printLine("Welcome to the ZIO Graph Application!")
    _ <- mainMenu(ref)
  } yield ()

  def mainMenu(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Main Menu:")
      _ <- printLine("1. Add Edge")
      _ <- printLine("2. Remove Edge")
      _ <- printLine("3. Display Graph")
      _ <- printLine("4. Save Graph to JSON")
      _ <- printLine("5. Load Graph from JSON")
      _ <- printLine("6. Go to Algorithm Menu")
      _ <- printLine("Q. Exit")
      choice <- readLine
      _ <- choice match {
        case "1" => addEdgeMenu(ref)
        case "2" => removeEdgeMenu(ref)
        case "3" => displayGraph(ref)
        case "4" => saveGraphToJson(ref)
        case "5" => loadGraphFromJson(ref)
        case "6" => algorithmMenu(ref)
        case "Q" => printLine("Goodbye!")
        case _ => printLine("Invalid choice. Please try again.") *> mainMenu(ref)
      }
    } yield ()
  }

  def algorithmMenu(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Algorithm Menu:")
      _ <- printLine("1. Depth-First Search (DFS)")
      _ <- printLine("2. Breadth-First Search (BFS)")
      _ <- printLine("3. Topological Sort")
      _ <- printLine("4. Dijkstra's Shortest Path")
      _ <- printLine("5. Cycle Detection")
      _ <- printLine("6. Floyd-Warshall Algorithm")

      _ <- printLine("Q. Back to Main Menu")
      choice <- readLine
      _ <- choice match {
        case "1" => dfsMenu(ref)
        case "2" => bfsMenu(ref)
        case "3" => topologicalSortMenu(ref)
        case "4" => dijkstraMenu(ref)
        case "5" => cycleDetectionMenu(ref)
        case "6" => floydWarshallMenu (ref)

        case "Q" => mainMenu(ref)
        case _ => printLine("Invalid choice. Please try again.") *> algorithmMenu(ref)
      }
    } yield ()
  }

  def addEdgeMenu(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the source vertex:")
      source <- readLine
      _ <- printLine("Enter the destination vertex:")
      destination <- readLine
      _ <- ref.update(graph => graph.addEdge(DirectedEdge(source, destination)))
      _ <- printLine(s"Edge ($source, $destination) added.")
      _ <- mainMenu(ref)
    } yield ()
  }

  def removeEdgeMenu(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the source vertex:")
      source <- readLine
      _ <- printLine("Enter the destination vertex:")
      destination <- readLine
      _ <- ref.update(graph => graph.removeEdge(DirectedEdge(source, destination)))
      _ <- printLine(s"Edge ($source, $destination) removed.")
      _ <- mainMenu(ref)
    } yield ()
  }

  def displayGraph(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      graph <- ref.get
      _ <- printLine(graph.toGraphViz)
      _ <- mainMenu(ref)
    } yield ()
  }

  def saveGraphToJson(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      graph <- ref.get
      json = graph.toJson
      _ <- printLine(s"Graph JSON: $json")
      _ <- mainMenu(ref)
    } yield ()
  }

  def loadGraphFromJson(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the graph JSON:")
      json <- readLine
      _ <- json.fromJson[DirectedGraph[String]] match {
        case Right(graph) => ref.set(graph) *> printLine("Graph loaded successfully.")
        case Left(error)  => printLine(s"Failed to load graph: $error")
      }
      _ <- mainMenu(ref)
    } yield ()
  }

  def dfsMenu(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the starting vertex:")
      start <- readLine
      graph <- ref.get
      _ <- printLine(s"DFS: ${DFSAlgorithm.dfs(graph, start)}")
      _ <- algorithmMenu(ref)
    } yield ()
  }

  def bfsMenu(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the starting vertex:")
      start <- readLine
      graph <- ref.get
      _ <- printLine(s"BFS: {graph.bfs(start)}")
      _ <- algorithmMenu(ref)
    } yield ()
  }

  def topologicalSortMenu(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      graph <- ref.get
      _ <- printLine(s"Topological Sort: {graph.topologicalSort}")
      _ <- algorithmMenu(ref)
    } yield ()
  }

  def cycleDetectionMenu(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      graph <- ref.get
      _ <- printLine(s"Cycle Detection: {graph.hasCycle}")
      _ <- algorithmMenu(ref)
    } yield ()
  }

  def floydWarshallMenu(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      graph <- ref.get
      _ <- printLine(s"Floyd-Warshall Algorithm: {graph.floydWarshall}")
      _ <- algorithmMenu(ref)
    } yield ()
  }

  def dijkstraMenu(ref: Ref[DirectedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the starting vertex:")
      start <- readLine
      graph <- ref.get
      _ <- printLine(s"Dijkstra's Shortest Path: {DijkstraAlgorithm.dijkstra(graph,start)    }")
      _ <- algorithmMenu(ref)
    } yield ()
  }



}
