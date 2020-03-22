package a_star;

import java.util.Random;

public class Launcher {

    // Board goal state of the puzzle
    static final int [] goalState = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    static final int totalCells = 9;		// Total number of cells in the board

    public static void main(String [] args){

        // Board initial state not randomly generated
        int [] initialState = {9, 9, 9, 9, 9, 9, 9, 9, 9};
//		int [] initialState = {2, 5, 0, 1, 6, 3, 4, 7, 8};		// Manually inserted value

        Random random = new Random();		// Random operator
        int a = 0;							// Stores the number generated randomly

        for(int i=0; i<totalCells;){

            a = (int)random.nextInt(totalCells);		// Random number generated between 0 to 8

            if(!searchSameNumber(initialState, a)){

                initialState[i] = a;		// Stores the randomly generated number to the cell
                i++;
            }
        }

        System.out.println("\n------------------------------");
        System.out.println("\nInitial State:");

        // Printing initial states
        for(int i=0; i<totalCells; i++){

            if(i % 3 == 0){

                System.out.println();
            }

            System.out.print(initialState[i] + " ");
        }

        System.out.println("\n------------------------------");

        System.out.println("\nGoal State:");

        // Printing goal states
        for(int i=0; i<totalCells; i++){

            if(i % 3 == 0){

                System.out.println();
            }

            System.out.print(goalState[i] + " ");
        }

        System.out.println("\n------------------------------");

        // Initializing the solver
        Solver solver = new Solver();
        solver.solve(initialState);
    }

    /*
     * This method checks each cells number of the board are same or not with the value
     * generated randomly
     *
     * @param initialState 	The initial state of the board
     * @param a 			The number generated randomly
     *
     * @return true			If the cell number is same as the number generated randomly
     * @return false		If all the cell numbers are not same as the number generated randomly
     */
    public static boolean searchSameNumber(int [] initialState, int a){

        for(int i=0; i<totalCells; i++){

            if(initialState[i] == a){

                return true;
            }
        }

        return false;
    }
}
