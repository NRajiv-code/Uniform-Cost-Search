/* NAME:RAJIV NIDUMOLU, UTA ID: 1001866606 */

import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

class Node implements Comparable<Node> {
    /**
     * The properties of a node:
     * 1. nodeStateName - The name of the state for which the node is created. Type
     * : String
     * 2. nodeParent - The Parent of the current node. Type: Node
     * 3. nodeDepth - The level of the current node. Type : Integer
     * 4. nodePathCost - The cumulative cost to reach the current node. Type: Double
     */
    private String nodeStateName;
    private Node nodeParent;
    private Integer nodeDepth;
    private double nodePathCost;

    public Node(String nodeStateName, Node nodeParent, Integer nodeDepth, double nodePathCost) {
        this.nodeStateName = nodeStateName;
        this.nodeParent = nodeParent;
        this.nodeDepth = nodeDepth;
        this.nodePathCost = nodePathCost;

    }

    public String getNodeStateName() {
        return nodeStateName;
    }

    public Node getNodeParent() {
        return nodeParent;
    }

    public Integer getNodeDepth() {
        return nodeDepth;
    }

    public double getNodePathCost() {
        return nodePathCost;
    }

    public int compareTo(Node node) {

        double tempCost = node.nodePathCost;
        if (this.nodePathCost > tempCost) {
            return 1;
        } else if (this.nodePathCost == tempCost) {
            return 0;
        } else {
            return -1;
        }
        // return 0;
    }
}

public class find_route {
    public static Node startState;
    public static String goalState;
    public static ArrayList<Node> fringe;
    public static ArrayList<String> closedSet;
    public static Map<String, Double> fileinputSet;

    public static void main(String args[]) {
        String fileName = args[0];
        startState = new Node(args[1], null, 0, 0);
        goalState = args[2];
        fileinputSet = new LinkedHashMap<String, Double>();
        readFile(fileName);
        fringe = new ArrayList<Node>();
        fringe.add(startState);
        closedSet = new ArrayList<String>();

        Node goal = extractNodeFromFringe();
        /** Call the user defined function for extracting the node from the fringe */

        if (goal == null) {/** The if loop is executed if no route is found */
            System.out.println("distance: infinty\nroute:\n none");
        } else {
            getPath(goal);/** Calls the user defined function for retracing the path from the goal node */
        }
    }

    public static void readFile(String fileName) {
        try {
            Scanner ioFile = new Scanner(new File(fileName));/** uses a scanner for accessing the .txt file */
            String ioFileData;
            while (!(ioFileData = ioFile.nextLine().toString()).equals("END OF INPUT")) {/**
                                                                                          * iterate till the "END OF
                                                                                          * INPUT" line is reached
                                                                                          */
                // = file_scanner.nextLine();
                /**
                 * The below set of code is used to read the msg from the system line by line
                 * and then load it in to the queue(map) as key and value
                 */
                /**
                 * The message retrieved from the file is stored in the format of key:
                 * "<SourceCity>-<DestCity> and message: <PathCost>
                 */
                StringTokenizer fileST = new StringTokenizer(ioFileData, " ");
                String keyForMap = fileST.nextToken() + " " + fileST.nextToken();
                double dataForMap = (double) Integer.parseInt(fileST.nextToken());
                fileinputSet.put(keyForMap, dataForMap);/** Adding the message read into the map one by one. */
            }
            ioFile.close();/** closing the file */
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            // e.printStackTrace();
        }
    }

    public static ArrayList<Node> exp_Successor(Node parentNode) {
        int count = 0;
        count = count++;
        ArrayList<Node> successorList = new ArrayList<Node>();

        for (String pathKey : fileinputSet.keySet()) {/**
                                                       * Iterates over the map which contains the data from the input
                                                       * file
                                                       */
            Node tempNode = null;
            StringTokenizer pathString = new StringTokenizer(pathKey, " ");/**
                                                                            * StringTokenizer is used for extracting
                                                                            * each of the component in one input line
                                                                            */
            String firstState = pathString.nextToken();
            String secondState = pathString.nextToken();
            /**
             * The below if else condition checks if the extracted nodes name is present in
             * one of the token, if yes we generate the node for the other
             */
            if ((firstState.equalsIgnoreCase(parentNode.getNodeStateName()))) {
                tempNode = new Node(secondState, parentNode, parentNode.getNodeDepth() + 1,
                        parentNode.getNodePathCost() + fileinputSet.get(pathKey));
                successorList.add(tempNode);
            } else if (secondState.equalsIgnoreCase(parentNode.getNodeStateName())) {
                tempNode = new Node(firstState, parentNode, parentNode.getNodeDepth() + 1,
                        parentNode.getNodePathCost() + fileinputSet.get(pathKey));
                successorList.add(tempNode);
            }
        }

        return successorList;/** Returns an ArrayList containing the nodes of all the successor */

    }

    public static Node extractNodeFromFringe() {
        Node extractedNode, goalNode;

        goalNode = null;

        while (fringe.isEmpty() == false) {/**
                                            * The below line of codes will loop until the fringeList becomes empty or a
                                            * goal state is found
                                            */
            extractedNode = fringe.remove(0);

            /**
             * Since Uniformed cost is implemented, the first element of the fringe is
             * extracted.
             */

            if ((extractedNode.getNodeStateName()).equalsIgnoreCase(goalState)) { /**
                                                                                   * Check if the extracted node is the
                                                                                   * goal state
                                                                                   */
                // System.out.println("Result found: "+extractedNode.getNodeStateName());

                goalNode = extractedNode;
                break; /** If the goal node is found the while condition is terminated. */
            } else {
                if (closedSet.isEmpty()) {
                    fringe.addAll(exp_Successor(extractedNode));
                    closedSet.add((extractedNode.getNodeStateName()));
                }
                /**
                 * We check if the closed state contains the extracted node state name. Only if
                 * it does not contain the node,successor is retrieved
                 */
                else if (!(closedSet.contains((extractedNode.getNodeStateName())))) { /**
                                                                                       * Check if the extracted node is
                                                                                       * not already added in to closed
                                                                                       * set
                                                                                       */

                    fringe.addAll(exp_Successor(extractedNode)); /**
                                                                  * Calling the user defined function for getting the
                                                                  * list of successors and then adding the node of
                                                                  * successors into fringe
                                                                  */

                    closedSet.add((extractedNode.getNodeStateName()));/**
                                                                       * Adding the extracted nodes state name into the
                                                                       * closed set.
                                                                       */

                }
                Collections.sort(fringe);
            }

        }
        System.out.println("nodes expanded :" + fringe.size());
        System.out.println("nodes generated :" + closedSet.size());
        return goalNode;

    }

    public static void getPath(Node goalNode) {
        Node currentNode;
        double Distance = goalNode.getNodePathCost();
        ArrayList<String> outputDisplay = new ArrayList<String>();
        currentNode = goalNode;
        /**
         * Storing the path detail as we retrace the node back to the source node(for
         * which parent = null)x
         */
        while (currentNode.getNodeParent() != null) {
            String outputString = currentNode.getNodeParent().getNodeStateName() + " to "
                    + currentNode.getNodeStateName();
            double distance = currentNode.getNodePathCost() - (currentNode.getNodeParent().getNodePathCost());
            outputString += " " + distance;
            outputDisplay.add(0, outputString);
            currentNode = currentNode.getNodeParent();

        }
        /** Display the stored output */
        System.out.println("distance: " + Distance + "\n" + "route:");
        // System.out.println("");
        for (String outputLine : outputDisplay) {
            System.out.println(outputLine);
        }
    }

}
