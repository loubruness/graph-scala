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
    _ <- printLine("Welcome to the ZIO Graph Application!")
    _ <- selectMenu
    } yield ()

  def selectMenu: ZIO[Any, IOException, Unit] = for {
    directedRef <- Ref.make(DirectedGraph(Set.empty[String], Set.empty))
    undirectedRef <- Ref.make(UndirectedGraph(Set.empty[String], Set.empty))
    weightedRef <- Ref.make(WeightedGraph(Set.empty[String], Set.empty))
    _ <- printLine("Select Graph Type:")
    _ <- printLine("1. Directed Graph")
    _ <- printLine("2. Undirected Graph")
    _ <- printLine("3. Weighted Graph")
    _ <- printLine("Q. Exit")


    choice <- readLine
    _ <- choice match {
      case "1" => mainMenu(directedRef)
      case "2" => mainMenu(undirectedRef)
      case "3" => mainMenu(weightedRef)

      case "Q" => printLine("Goodbye!")
      case _ => printLine("Invalid choice. Please try again.") *> selectMenu
    }
  } yield ()

  def mainMenu(ref: Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
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
      _ <- choice match 
        case "1" => addEdgeMenu(ref)
        case "2" => removeEdgeMenu(ref)
        case "3" => displayGraph(ref)
        case "4" => saveGraphToJson(ref)
        case "5" => loadGraphFromJson(ref)
        case "6" => algorithmMenu(ref)
        case "Q" => printLine("Goodbye!")
        case _ => printLine("Invalid choice. Please try again.") *> mainMenu(ref)

    } yield ()
  }
  def algorithmMenu(ref:  Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Algorithm Menu:")
      graph <- ref.get
      _ <- graph match 
        case graph: DirectedGraph[String] => 
          printLine("1. Depth-First Search (DFS)") *> 
          printLine("2. Breadth-First Search (BFS)") *> 
          printLine("3. Topological Sort") *> 
          printLine("4. Dijkstra's Shortest Path") *> 
          printLine("5. Cycle Detection") *> 
          printLine("6. Floyd-Warshall Algorithm")
        case graph: UndirectedGraph[String] => 
          printLine("1. Depth-First Search (DFS)") *> 
          printLine("2. Breadth-First Search (BFS)") *> 
          printLine("3. Topological Sort") *> 
          printLine("4. Cycle Detection")
        case graph: WeightedGraph[String] => 
          printLine("1. Floyd-Warshall Algorithm")
      
      _ <- printLine("Q. Back to Main Menu")

      choice <- readLine
      _ <- graph match{
        case graph: DirectedGraph[String] => 
          choice match 
            case "1" => dfsMenu(ref)
            case "2" => bfsMenu(ref)
            case "3" => topologicalSortMenu(ref)
            case "5" => cycleDetectionMenu(ref)
            case "6" => floydWarshallMenu(ref)
            case "Q" => mainMenu(ref)
            case _ => printLine("Invalid choice. Please try again.") *> algorithmMenu(ref)
          
        case graph: UndirectedGraph[String] =>
          choice match 
            case "1" => dfsMenu(ref)
            case "2" => bfsMenu(ref)
            case "3" => topologicalSortMenu(ref)
            case "4" => cycleDetectionMenu(ref)
            case "Q" => mainMenu(ref)
            case _ => printLine("Invalid choice. Please try again.") *> algorithmMenu(ref)
          
        case graph: WeightedGraph[String] =>
          choice match 
            case "1" => floydWarshallMenu(ref)
            case "Q" => mainMenu(ref)
            case _ => printLine("Invalid choice. Please try again.") *> algorithmMenu(ref)
      }
    } yield ()
  }

  def addEdgeMenu(ref: Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the source vertex:")
      source <- readLine

      _ <- printLine("Enter the destination vertex:")
      destination <- readLine
      
      _ <- ref match
        case ref: Ref[DirectedGraph[String]] => 
          ref.update(graph => graph.addEdge(DirectedEdge(source, destination))) *>
          printLine(s"Directed Edge ($source, $destination) added.")
        case ref: Ref[UndirectedGraph[String]] => 
          ref.update(graph => graph.addEdge(UndirectedEdge(source, destination))) *>
          printLine(s"Undirected Edge ($source, $destination) added.")
        case ref: Ref[WeightedGraph[String]] => 
          for {
            _ <- printLine("Enter the weight:")
            weight <- readLine

            _ <- ref.update(graph => graph.addEdge(WeightedEdge(source, destination, weight.toDouble))) *>
            printLine(s"Weighted Edge ($source, $destination, $weight) added.")

          } yield ()

      _ <- mainMenu(ref)

    } yield ()
  }

  def removeEdgeMenu(ref: Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the source vertex:")
      source <- readLine
      _ <- printLine("Enter the destination vertex:")
      destination <- readLine
      _ <- ref match
        case ref: Ref[DirectedGraph[String]] => 
          ref.update(graph => graph.removeEdge(DirectedEdge(source, destination))) *>
          printLine(s"Directed Edge ($source, $destination) removed.")

        case ref: Ref[UndirectedGraph[String]] => 
          ref.update(graph => graph.removeEdge(UndirectedEdge(source, destination))) *>
          printLine(s"Undirected Edge ($source, $destination) removed.")

        case ref: Ref[WeightedGraph[String]] => 
          ref.update(graph => graph.removeEdge(WeightedEdge(source, destination, 0))) *>
          printLine(s"Weighted Edge ($source, $destination) removed.")

      _ <- mainMenu(ref)

    } yield ()
  }

  def displayGraph(ref: Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      graph <- ref.get
      _ <- graph match
        case graph: DirectedGraph[String] => 
          for {
            _ <- printLine(graph.toGraphViz)
          } yield ()
        case graph: UndirectedGraph[String] =>
          for {
            _ <- printLine(graph)
          } yield ()
        case graph: WeightedGraph[String] =>
          for {
            _ <- printLine(graph)
          } yield ()
      _ <- mainMenu(ref)
    } yield ()
  }

  def saveGraphToJson(ref: Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      graph <- ref.get
      json = graph match
        case graph: DirectedGraph[String] => graph.toJson
        case graph: UndirectedGraph[String] => graph.toJson
        case graph: WeightedGraph[String] => graph.toJson
        
      _ <- printLine(s"Graph JSON: $json")
      _ <- mainMenu(ref)
    } yield ()
  }

  def loadGraphFromJson(ref: Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the graph JSON:")
      json <- readLine
      _ <- ref match
        case ref: Ref[DirectedGraph[String]] => 
          json.fromJson[DirectedGraph[String]] match {
            case Right(graph) => ref.set(graph) *> printLine("Directed Graph loaded successfully.")
            case Left(error)  => printLine(s"Failed to load graph: $error")
          }
        case ref: Ref[UndirectedGraph[String]] =>
          json.fromJson[UndirectedGraph[String]] match {
            case Right(graph) => ref.set(graph) *> printLine("Undirected Graph loaded successfully.")
            case Left(error)  => printLine(s"Failed to load graph: $error")
          }
        case ref: Ref[WeightedGraph[String]] =>
          json.fromJson[WeightedGraph[String]] match {
            case Right(graph) => ref.set(graph) *> printLine("Weighted Graph loaded successfully.")
            case Left(error)  => printLine(s"Failed to load graph: $error")
          }
      _ <- mainMenu(ref)
    } yield ()
  }

  def dfsMenu(ref: Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the starting vertex:")
      start <- readLine
      graph <- ref.get
      _ <- graph match {
        case graph: DirectedGraph[String] => printLine(s"DFS: ${DFSAlgorithm.dfs(graph, start)}")
        case graph: UndirectedGraph[String] => printLine(s"DFS: ${DFSAlgorithm.dfs(graph, start)}")
        case _ => printLine("Invalid Graph Type")
      }
      _ <- algorithmMenu(ref)
    } yield ()
  }

  def bfsMenu(ref: Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the starting vertex:")
      start <- readLine
      graph <- ref.get
      _ <- graph match {
        case graph: DirectedGraph[String] => printLine(s"BFS: {BFSAlgorithm.bfs(graph, start)}")
        case graph: UndirectedGraph[String] => printLine(s"BFS: {BFSAlgorithm.bfs(graph, start)}")
        case _ => printLine("Invalid Graph Type")
      }
      _ <- algorithmMenu(ref)
    } yield ()
  }

  def topologicalSortMenu(ref: Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      graph <- ref.get
      _ <- graph match {
        case graph: DirectedGraph[String] => printLine(s"Topological Sort: {TopologicalSortAlgorithm.topologicalSort(graph)}")
        case _ => printLine("Invalid Graph Type")
      }
      _ <- algorithmMenu(ref)
    } yield ()
  }

  def cycleDetectionMenu(ref: Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      graph <- ref.get
      _ <- graph match {
        case graph: DirectedGraph[String] => printLine(s"Cycle Detection: {CycleDetectionAlgorithm.hasCycle(graph)}")
        case graph: UndirectedGraph[String] => printLine(s"Cycle Detection: {CycleDetectionAlgorithm.hasCycle(graph)}")
        case _ => printLine("Invalid Graph Type")
      }
      _ <- algorithmMenu(ref)
    } yield ()
  }

  def floydWarshallMenu(ref: Ref[DirectedGraph[String]] | Ref[UndirectedGraph[String]] | Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      graph <- ref.get
      _ <- graph match {
        case graph: WeightedGraph[String] => printLine(s"Floyd-Warshall: {FloydWarshallAlgorithm.floydWarshall(graph)}")
        case _ => printLine("Invalid Graph Type")
      }
    } yield ()
  }

  def dijkstraMenu(ref: Ref[WeightedGraph[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the starting vertex:")
      start <- readLine
      graph <- ref.get
      _ <- printLine(s"Dijkstra's Shortest Path: ${DijkstraAlgorithm.dijkstra(graph,start)}")
      _ <- algorithmMenu(ref)
    } yield ()
  }
}
