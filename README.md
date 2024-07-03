## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

# Global Structure

We have split our project into two sub-projects : a core and an app sub-project. The core sub-project contains everything in link with ?????????????????? and the app sub-project everything concerning ???????????

We have decided to gather the different graph definition files into a 'graph' package. In order to optimize our code, we decided to make the Graph data structure into a trait. This abstraction allowed us to have a clear and adaptable definition of our main data structure. We decided to use Sets to represent our edges and vertices, since they guarantee uniqueness of the elements they contain. 

Our different types of graphs, which are all implementation of the Graph trait, make use of case classes in their definitions. Our aim with this implementation is to implement immutability, and enable the use of pattern matching. 

We also opted to create a worksheets folder, to centralize the different sheets we've created. In those worksheets, we create scenarios to illustrate and test our different Graph classes. On the same line of thought, we created an "algo" folder for the different algorithms we developed.

For the tests, we implemented unit tests using the scalatest library, and the flatspec style. They have been grouped into the 'test' folder.

Finally, we chose to represent our work with a ZIO 2 app.


