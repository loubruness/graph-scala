package app

import zio._
import zio.Console._
import graph._
import zio.json._
import graph.DFSAlgorithm._
import java.io.IOException
import zio.stm.ZSTM
import JsonCodecs._

object Main extends ZIOAppDefault {

  def run: URIO[ZIOAppArgs & Scope, ExitCode] = program.exitCode

  val program: ZIO[Any, IOException, Unit] = for {
    _ <- printLine("Welcome to the ZIO Graph Application!")
    directedRef <- Ref.make(DirectedGraph(Set.empty[String], Set.empty))
    undirectedRef <- Ref.make(UndirectedGraph(Set.empty[String], Set.empty))
    weightedRef <- Ref.make(WeightedGraph(Set.empty[String], Set.empty))
    _ <- selectMenu(AppState(directedRef, undirectedRef, weightedRef, GraphType.Directed))

    } yield ()

  def selectMenu(appState: AppState): ZIO[Any, IOException, Unit] = for {

    _ <- printLine("Select Graph Type:")
    _ <- printLine("1. Directed Graph")
    _ <- printLine("2. Undirected Graph")
    _ <- printLine("3. Weighted Graph")
    _ <- printLine("Q. Exit") 


    choice <- readLine
    _ <- choice match {
      case "1" => mainMenu(AppState(appState.directedGraph,appState.undirectedGraph,appState.weightedGraph,GraphType.Directed))         
      case "2" => mainMenu(AppState(appState.directedGraph,appState.undirectedGraph,appState.weightedGraph,GraphType.Undirected))
      case "3" => mainMenu(AppState(appState.directedGraph,appState.undirectedGraph,appState.weightedGraph,GraphType.Weighted))

      case "Q" => printLine("Goodbye!") *> ZIO.succeed(())
      case _ => printLine("Invalid choice. Please try again.") *> selectMenu(appState)
    }
  } yield ()

  def mainMenu(appState: AppState): ZIO[Any, IOException, Unit] = {
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
        case "1" => addEdgeMenu(appState)
        case "2" => removeEdgeMenu(appState)
        case "3" => displayGraph(appState)
        case "4" => saveGraphToJson(appState)
        case "5" => loadGraphFromJson(appState)
        case "6" => algorithmMenu(appState)
        case "Q" => printLine("Goodbye!") *> selectMenu(appState)
        case _ => printLine("Invalid choice. Please try again.") *> mainMenu(appState)

    } yield ()
  }
  def algorithmMenu(appState:AppState): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Algorithm Menu:")
      _ <- appState.graphType match 
        case GraphType.Directed =>
          printLine("1. Depth-First Search (DFS)") *> 
          printLine("2. Breadth-First Search (BFS)") *> 
          printLine("3. Topological Sort") *> 
          printLine("4. Cycle Detection") 
        case GraphType.Undirected => 
          printLine("1. Depth-First Search (DFS)") *> 
          printLine("2. Breadth-First Search (BFS)") *> 
          printLine("3. Cycle Detection")
        case GraphType.Weighted => 
          printLine("1. Depth-First Search (DFS)") *> 
          printLine("2. Breadth-First Search (BFS)") *> 
          printLine("3. Topological Sort") *> 
          printLine("4. Dijkstra's Shortest Path") *> 
          printLine("5. Cycle Detection") *> 
          printLine("6. Floyd-Warshall Algorithm")
      
      _ <- printLine("Q. Back to Main Menu")

      choice <- readLine
      _ <- appState.graphType match{
        case GraphType.Directed => 
          choice match 
            case "1" => dfsMenu(appState)
            case "2" => bfsMenu(appState)
            case "3" => topologicalSortMenu(appState)
            case "4" => cycleDetectionMenu(appState)
            case "Q" => mainMenu(appState)
            case _ => printLine("Invalid choice. Please try again.") *> algorithmMenu(appState)
          
        case GraphType.Undirected =>
          choice match 
            case "1" => dfsMenu(appState)
            case "2" => bfsMenu(appState)
            case "3" => cycleDetectionMenu(appState)
            case "Q" => mainMenu(appState)
            case _ => printLine("Invalid choice. Please try again.") *> algorithmMenu(appState)
          
        case GraphType.Weighted =>
          choice match 
            case "1" => dfsMenu(appState)
            case "2" => bfsMenu(appState)
            case "3" => topologicalSortMenu(appState)
            case "4" => dijkstraMenu(appState)
            case "5" => cycleDetectionMenu(appState)
            case "6" => floydWarshallMenu(appState)
            case "Q" => mainMenu(appState)
            case _ => printLine("Invalid choice. Please try again.") *> algorithmMenu(appState)
      }
    } yield ()
  }

  def addEdgeMenu(appState:AppState): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the source vertex:")
      source <- readLine
      _ <- printLine("Enter the destination vertex:")
      destination <- readLine

      _ <- appState.graphType match
        case GraphType.Weighted => 
          for {
            _ <- printLine("Enter the weight:")
            weight <- readLine
            _ <- appState.addEdge(source, destination, Some(weight))
            _ <- printLine(s"Edge ($source, $destination) with weight $weight added.")
          } yield ()
        case _ => 
          for{
            _<-appState.addEdge(source, destination)
            _ <- printLine(s"Edge ($source, $destination) added.")
          } yield ()
      _ <- mainMenu(appState)

    } yield ()
  }

  def removeEdgeMenu(appState:AppState): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the source vertex:")
      source <- readLine
      _ <- printLine("Enter the destination vertex:")
      destination <- readLine
      _ <- appState.removeEdge(source, destination) 
      _ <- printLine(s"Edge ($source, $destination) removed.")
      

    } yield ()
  }

  def displayGraph(appState:AppState): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Displaying Graph...")
      _ <- printLine(appState.graphViz)
      _ <- mainMenu(appState)
    } yield ()
  }

  def saveGraphToJson(appState:AppState): ZIO[Any, IOException, Unit] = {
    for {
      json <- appState.showJson
      _ <- printLine(s"Graph JSON: $json")
      _ <- mainMenu(appState)
    } yield ()
  }

  def loadGraphFromJson(appState:AppState): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the graph JSON:")
      json <- readLine
      _ <- appState.loadJson(json)
      _ <- printLine("Graph loaded from JSON.")
      _ <- mainMenu(appState)
    } yield ()
  }

  def dfsMenu(appState:AppState): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the starting vertex:")
      start <- readLine
      graph <- appState.graph
      _ <- printLine(s"DFS: ${DFSAlgorithm.dfs(graph, start)}")

      _ <- algorithmMenu(appState)
    } yield ()
  }

  def bfsMenu(appState:AppState): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the starting vertex:")
      start <- readLine
      graph <- appState.graph

      _<- printLine(s"BFS: ${BFSAlgorithm.bfs(graph, start)}")

      _ <- algorithmMenu(appState)
    } yield ()
  }

  def topologicalSortMenu(appState:AppState): ZIO[Any, IOException, Unit] = {
    for {
      graph <- appState.graph
      _ <- graph match {
        case graph: DirectedGraph[String] => printLine(s"Topological Sort: {TopologicalSortAlgorithm.topologicalSort(graph)}")
        case _ => printLine("Invalid Graph Type")
      }
      _ <- algorithmMenu(appState)
    } yield ()
  }

  def cycleDetectionMenu(appState:AppState): ZIO[Any, IOException, Unit] = {
    for {
      graph <- appState.graph

      _ <- printLine(s"Cycle Detection: ${CycleDetectionAlgorithm.detectCycle(graph)}")
      _ <- algorithmMenu(appState)
    } yield ()
  }

  def floydWarshallMenu(appState:AppState): ZIO[Any, IOException, Unit] = {
    for {
      graph <- appState.graph
      _ <- graph match {
        case graph: WeightedGraph[String] => printLine(s"Floyd-Warshall: ${FloydAlgorithm.floyd(graph)}")
        case _ => printLine("Invalid Graph Type")
      }
      _ <- algorithmMenu(appState)
    } yield ()
  }

  def dijkstraMenu(appState:AppState): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine("Enter the starting vertex:")
      start <- readLine
      graph <- appState.graph
      _ <- graph match
        case graph : WeightedGraph[String] => printLine(s"Dijkstra's Shortest Path: ${DijkstraAlgorithm.dijkstra(graph,start)}")
        case _ => printLine("Invalid Graph Type")
      _ <- algorithmMenu(appState)
    } yield ()
  }
}
