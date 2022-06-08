Name and UTA ID of the student.
Rajiv Ramachandra Nidumolu,

Java
openjdk 11.0.14.1 2022-02-08
OpenJDK Runtime Environment Temurin-11.0.14.1+1 (build 11.0.14.1+1)
OpenJDK 64-Bit Server VM Temurin-11.0.14.1+1 (build 11.0.14.1+1, mixed mode)

How the code is structured.
class find_route -main class() where the core implementation of finding route, with the methods():
public static void readFile(String fileName) ---> reading the input file using IO buffers
public static ArrayList<Node> exp_Successor(Node parentNode) ---> successor list expander method
public static Node extractNodeFromFringe()  ---> pop function to extract nodes from the fringe as required.
public static void getPath(Node goalNode) ---> Storing the path detail as we retrace the node back to the source node

class Node - Defines <Node> structure
The properties of a node:
     * 1. nodeStateName - The name of the state for which the node is created. Type
     * : String
     * 2. nodeParent - The Parent of the current node. Type: Node
     * 3. nodeDepth - The level of the current node. Type : Integer
     * 4. nodePathCost - The cumulative cost to reach the current node. Type: Double


How to run the code
javac find_route.java

java find_route input1.txt Bremen Kassel
