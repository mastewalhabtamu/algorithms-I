import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;


public class Solver {
    class Node implements Comparable<Node> {
        private int manhattan;
        private int moves;
        private Board board;
        private Node prevNode;

        public Node(Node prevNode, Board board, int priorMoves) {
            if (board == null) {
                throw new IllegalArgumentException();
            }

            this.board = board;
            this.manhattan = board.manhattan();

            this.moves = priorMoves + 1;

            this.prevNode = prevNode;
        }

        public int getManhattan() {
            return this.manhattan;
        }

        public int getMoves() {
            return this.moves;
        }

        public Board getBoard() {
            return board;
        }

        public Node getPrevNode() {
            return prevNode;
        }

        public int priority() {
            return this.manhattan + this.moves;
        }

        public boolean equals(Object obj) {
            if (obj.getClass() != this.getClass()) {
                return false;
            }

            Node nodeObj = (Node) obj;
            return this.manhattan == nodeObj.getManhattan() && this.moves == nodeObj.getMoves()
                    && this.board.equals(nodeObj.getBoard());
        }

        public int compareTo(Node obj) {
            return this.priority() - obj.priority();
        }
    }

    private MinPQ<Node> mainPQ = new MinPQ<>();
    private MinPQ<Node> twinPQ = new MinPQ<>();
    private Node mainGoalNode;
    private Node twinGoalNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // first insertions
        mainPQ.insert(new Node(null, initial, -1));
        twinPQ.insert(new Node(null, initial.twin(), -1));

        // search for node with goal board
        boolean mainTurn = true;

        // first minimum priority nodes
        Node mainMinNode = mainPQ.delMin();
        Node twinMinNode = twinPQ.delMin();
        while (mainGoalNode == null && twinGoalNode == null) {
            if (mainTurn) {
                if (!mainMinNode.getBoard().isGoal()) {
                    for (Board neighbour : mainMinNode.getBoard().neighbors()) {
                        Node neighbourNode = new Node(mainMinNode, neighbour,
                                                      mainMinNode.getMoves());
                        mainPQ.insert(neighbourNode);
                    }

                    // get new minimum priority node
                    mainMinNode = mainPQ.delMin();
                }
                else {
                    this.mainGoalNode = mainMinNode;
                }
            }
            else { // twin turn
                if (!twinMinNode.getBoard().isGoal()) {
                    for (Board neighbour : twinMinNode.getBoard().neighbors()) {
                        Node neighbourNode = new Node(twinMinNode, neighbour,
                                                      twinMinNode.getMoves());
                        twinPQ.insert(neighbourNode);
                    }

                    // get new minimum priority node
                    twinMinNode = twinPQ.delMin();
                }
                else {
                    this.twinGoalNode = twinMinNode;
                }
            }

            // change turns
            mainTurn = !mainTurn;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return twinGoalNode != null;
    }

    // min number of moves to solve initial board
    public int moves() {
        return mainGoalNode.getMoves();
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        List<Board> solutionBoards = new ArrayList<>();

        Node currentNode = mainGoalNode;
        while (currentNode.getPrevNode() != null) {
            solutionBoards.add(currentNode.getBoard());

            currentNode = currentNode.getPrevNode();
        }

        return solutionBoards;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
