package a_star;

import java.util.Arrays;

public class State {

    public int [] tiles;			// Tiles left to right, up to bottom
    public int spaceIndex;			// Index of blank space (0) in tiles
    public State previousState;		// Previous state in solution chain
    public int g;					// Level cost measuring from start node
    public int h;					// Heuristic value (differences from goal)
    static int pathCost = 0;		// Stores the amount of steps to reach goal state

    /*
     * Constructor
     * Building a start state
     *
     * @param initialState		The initial state of the board
     */
    public State(int [] initialState){

        tiles = initialState;
        spaceIndex = index(tiles, 0);
        g = 0;
        h = heuristic(tiles);
        previousState = null;
    }

    /*
     * Constructor
     * Building a successor to previous state by sliding tile from given index
     *
     * @param previousState		Previous state of the current state
     * @param					New blank space index
     */
    public State(State previousState, int slideFromIndex){

        tiles = Arrays.copyOf(previousState.tiles, previousState.tiles.length);
        tiles[previousState.spaceIndex] = tiles[slideFromIndex];
        tiles[slideFromIndex] = 0;
        spaceIndex = slideFromIndex;
        g = previousState.g + 1;
        h = heuristic(tiles);
        this.previousState = previousState;
    }

    /*
     * This method is work as the priority function [also known as f(n)]
     *
     * @return g + h	Summation of length of the path from start node and heuristic values [as f(n) = g(n) + h(n)]
     */
    public int priority(){

        return g + h;
    }

    /*
     * This method returns true if we get to the goal state
     *
     * @return true		If current state (tiles) is equal to goal state
     */
    public boolean isGoal(){

        return Arrays.equals(tiles, Launcher.goalState);
    }

    // Slide up movement
    public State moveUp(){

        if(spaceIndex < 6){

            return new State(this, spaceIndex+3);
        }else{

            return null;
        }
    }

    // Slide down movement
    public State moveDown(){

        if(spaceIndex > 2){

            return new State(this, spaceIndex-3);
        }else{

            return null;
        }
    }

    // Slide right movement
    public State moveLeft(){

        if(spaceIndex % 3 < 2){

            return new State(this, spaceIndex+1);
        }else{

            return null;
        }
    }

    // Slide left movement
    public State moveRight(){

        if(spaceIndex % 3 > 0){

            return new State(this, spaceIndex-1);
        }else{

            return null;
        }
    }

    /*
     * This method will print the state of the board
     */
    public void print(){

        for(int i=0; i<Launcher.totalCells; i += 3){

            System.out.println(tiles[i] + " " + tiles[i+1] + " " + tiles[i+2]);
        }
    }

    /*
     * This method will print the solution chain with start state first
     */
    public void printAll(){

        if(previousState != null){

            previousState.printAll();		// Recursion
        }

        pathCost++;					// Increasing 1 for each step for solution state counter
        System.out.println();
        print();
    }

    /*
     * This method returns the index of space or blank cell (0)
     *
     * @param currentState		The current state of the board
     * @param spacevalue		The value of blank space which is considered as 0
     *
     * @return i				The index of blank space (0)
     * @return -1				If the index is not found
     */
    public int index(int [] currentState, int spaceValue){

        for(int i=0; i<currentState.length; i++){

            if(currentState[i] == spaceValue){

                return i;
            }
        }

        return -1;
    }

    /*
     * This method calculates the Manhattan distance of the current state
     *
     * @param a		Index of the tile or cell
     * @param b		The number or value of the tile or cell
     */
    public int manhattanDistance(int a, int b){

        return Math.abs(a/3 - b/3) + Math.abs(a%3 - b%3);
    }

    /*
     * This method calculates the heuristic value of the current state
     *
     * @param tiles		The current state of the board
     *
     * @return h		Heuristic value which is calculated
     */
    public int heuristic(int [] tiles){

        int h = 0;

        for(int i=0; i<tiles.length; i++){

            if(tiles[i] != 0){

                // Storing the maximum value from current h value and the Manhattan distance of the tile
                h = Math.max(h, manhattanDistance(i, tiles[i]));
            }
        }

        return h;
    }

    @Override
    public boolean equals(Object obj){

        if(obj instanceof State){

            State other = (State)obj;
            return Arrays.equals(tiles, other.tiles);
        }

        return false;
    }

    @Override
    public int hashCode(){

        return Arrays.hashCode(tiles);
    }
}
