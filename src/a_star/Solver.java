package a_star;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Solver {

    // Closed state set
    final HashSet <State> closed = new HashSet<State>();

/**     Priority queue which will store the solution states as well as all the expanded states
	final PriorityQueue <State> queue = new PriorityQueue<State>(100, new Comparator<State>(){

		@Override
		public int compare(State a, State b) {

			int lowestMisplacedTiles = 0;		// Stores total number of tiles that are misplaced

			// Comparing misplaced tiles
			for(int i=0; i<Launcher.totalCells; i++){

				if(a.tiles[i] != b.tiles[i]){

					lowestMisplacedTiles++;
				}
			}

			return lowestMisplacedTiles;
		}
	});
 */

    // Priority queue which will store the solution states as well as all the expanded states
    final PriorityQueue <State> queue = new PriorityQueue<State>(100, new Comparator<State>(){

        @Override
        public int compare(State a, State b) {

            return a.priority() - b.priority();
        }
    });

    /*
     * This method adds a valid (non-null and not closed) successor to the queue
     *
     * @param successor		Successor state of a current state
     */
    public void addSuccessor(State successor){

        if(successor != null && !closed.contains(successor)){

            queue.add(successor);
        }
    }

    /*
     * This method solves the puzzle
     *
     * @param initialState		The initial state of the board
     */
    public void solve(int [] initialState){

        queue.clear();
        closed.clear();

        // Initializing time
        long startTime = System.currentTimeMillis();

        // Adding the initial state to the queue
        queue.add(new State(initialState));

        while(!queue.isEmpty()){

            State state = queue.poll();		// Gets the lowest priority state

            // If it gets the goal state then the work is done
            if(state.isGoal()){

                long timeTaken = System.currentTimeMillis() - startTime;		// Total time taken
                state.printAll();
                System.out.println("\nSolution found !!!");

                System.out.println("\n----- Search Analysis -----\n");
                System.out.println("Total time taken: " + timeTaken + "ms");
                System.out.println("Nodes searched: " + queue.size());
                System.out.println("Path cost: " + state.pathCost);
                System.out.println("\n------------------------------");

                return;
            }

            // If it is not the goal state
            closed.add(state);

            // Adds successors to the queue
            addSuccessor(state.moveUp());
            addSuccessor(state.moveDown());
            addSuccessor(state.moveLeft());
            addSuccessor(state.moveRight());

            // If the queue gets empty then there will be no solution
            if(queue.isEmpty()){

                System.out.println("\nSolution not found !!!");
            }
        }
    }
}
