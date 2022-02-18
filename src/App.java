import java.util.Scanner;
import java.lang.Math;

/**
 * CPSC3750 Assignment 2, TicTacToe with Minimax AI
 * Author: Albert Arrieta
 * 
 * NOTE:
 * IMPLEMENT MINIMAX
 */

public class App {

    private static char [][] board = {
        {' ', '|', ' ', '|', ' '},
        {'-', '+', '-', '+', '-'},
        {' ', '|', ' ', '|', ' '},
        {'-', '+', '-', '+', '-'},
        {' ', '|', ' ', '|', ' '},
       };

    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        int choice = 0;
        boolean run = true;
        

        while(run == true) {
            System.out.println("Welcome to Tic Tac Toe!");
            System.out.println("Enter 1 to play");
            System.out.println("Enter 2 to learn how to play");
            System.out.println("Enter 3 to Quit");

            choice = scan.nextInt();

            switch(choice) {
                case 1: 
                    play();
                break;

                case 2: 
                    instructions();
                    break;

                case 3:
                    run = false;
                    break;
            }
        }
        scan.close();
    }

    private static void instructions() {
        System.out.println("==============INSTRUCTIONS==============");
        System.out.println("Upon starting a game, you will be given an empty game board.");
        resetBoard();
        printBoard();
        System.out.println("Each position on the board will be numbered and you will have to enter the number of the position you");
        System.out.println("would like to place your move.");
        board[0][0] = '1';
        board[0][2] = '2';
        board[0][4] = '3';
        board[2][0] = '4';
        board[2][2] = '5';
        board[2][4] = '6';
        board[4][0] = '7';
        board[4][2] = '8';
        board[4][4] = '9';
        printBoard();
        System.out.println("Your opponent, an AI that is implemented with a minimax algorithm will be fighting against you.");
        System.out.println("The game will end once a player wins or when a tie occurs.");
        System.out.println("========================================");
    }

    /**
     * Gameplay function of the program. A game of Tic Tac Toe will start and end here.
     */
    private static void play() {
        System.out.println("PLAYING!");
        resetBoard();
        boolean gameEnd = false;
        boolean turn = true; //true if human turn, false if ai

        while(gameEnd == false) {
            int token;
            if (turn == true) {
                boolean playerMove = false;
                while (playerMove == false) {
                    printBoard();
                    System.out.println("Enter the slot you would like to place your token");
                    token = scan.nextInt();
                    playerMove = placeToken(token, false);
                }
                turn = false;
            } else {
                AIPlay();
                turn = true;
            }
            gameEnd = endgameCheck();
        }
        printBoard();
        switch(getWinner()) {
            case -1: 
                System.out.println("=======You Win!!!!=======");
                break;
            case 1:
                System.out.println("=======AI Wins!!!!=======");
                break;
            case 0:
                System.out.println("=======TIE!!!!=======");

        }
    }

    /**
     * Prints the current state of the game board
     */
    private static void printBoard() {
        for (char[] row : board) {
            for(char col : row) {
                System.out.print(col);
            }
            System.out.println();
       }
    }

    /**
     * Resets the game board with empty slots
     */
    private static void resetBoard() {
        board[0][0] = ' ';
        board[0][2] = ' ';
        board[0][4] = ' ';

        board[2][0] = ' ';
        board[2][2] = ' ';
        board[2][4] = ' ';
        
        board[4][0] = ' ';
        board[4][2] = ' ';
        board[4][4] = ' ';
    }

    /**
     * Places the desired token into the desired slot on the game board.
     * @param t the slot to be filled
     * @param ai decides if the token will be O or X (O = AI, X = Human Player)
     */
    private static boolean placeToken(int t, boolean ai) {
        char symbol;
        symbol = (ai) ? 'O' : 'X';
        switch(t) {
            case 1:
                if (board[0][0] == ' ') {
                    board[0][0] = symbol;
                    return true;
                } else {
                    System.out.println("Slot 1 is already filled");
                    return false;
                }
            
            case 2:
                if (board[0][2] == ' ') {
                    board[0][2] = symbol;
                    return true;
                } else {
                    System.out.println("Slot 2 is already filled");
                    return false;
                }

            case 3:
                if (board[0][4] == ' ') {
                    board[0][4] = symbol;
                    return true;
                } else {
                    System.out.println("Slot 3 is already filled");
                    return false;
                }
            
            case 4:
                if (board[2][0] == ' ') {
                    board[2][0] = symbol;
                    return true;
                } else {
                    System.out.println("Slot 4 is already filled");
                    return false;
                }

            case 5:
                if (board[2][2] == ' ') {
                    board[2][2] = symbol;
                    return true;
                } else {
                    System.out.println("Slot 5 is already filled"); 
                    return false;
                }
            
            case 6:
                if (board[2][4] == ' ') {
                    board[2][4] = symbol;
                    return true;
                } else {
                    System.out.println("Slot 6 is already filled"); 
                    return false;
                }

            case 7:
                if (board[4][0] == ' ') {
                    board[4][0] = symbol;
                    return true;
                } else {
                    System.out.println("Slot 7 is already filled"); 
                    return false;
                }
            
            case 8:
                if (board[4][2] == ' ') {
                    board[4][2] = symbol;
                    return true;
                } else {
                    System.out.println("Slot 8 is already filled"); 
                    return false;
                }

            case 9:
                if (board[4][4] == ' ') {
                    board[4][4] = symbol;
                    return true;
                } else {
                    System.out.println("Slot 9 is already filled"); 
                    return false;
                }
            default:
                System.out.println("INVALID SLOT");
                return false;
        }
    }

    /**
     * Check if game is finished
     * @return true if game is completed, false otherwise
     */
    private static boolean endgameCheck() {
        int fill = 0;
        for (char[] row : board) {
            for (char slot : row) {
                if (slot == ' ')
                fill++;
            }
        }

        //horizontal win
        for (int i = 0; i < 5 ; i = i + 2) {
            if (board[i][0] == board[i][2] && board[i][2] == board[i][4] && board[i][0] != ' ') {
                return true;
            }
        }

        //vertical win
        for (int i = 0; i < 5 ; i=i+2) {
            if (board[0][i] == board[2][i] && board[2][i] == board[4][i] && board[0][i] != ' ') {
                return true;
            }
        }
        
        //diagonal win
        if (board[0][0] == board[2][2] && board[2][2] == board[4][4] && board[2][2] != ' ') {
            return true;
        }
        
        if (board[0][4] == board[2][2] && board[2][2] == board[4][0] && board[2][2] != ' ') {
            return true;            
        }

        //if tied
        else if (fill == 0) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * Outputs the corresponding integer to indicate how the game ended
     * @return 0 if tied. 1 if Ai won. -1 if Human Won
     */
    private static int getWinner(){
        //horizontal win
        for (int i = 0; i < 5 ; i = i + 2) {
            if (board[i][0] == board[i][2] && board[i][2] == board[i][4] && board[i][0] != ' ') {
                return winnerVal(board[i][0]);
            }
        }

        //vertical win
        for (int i = 0; i < 5 ; i=i+2) {
            if (board[0][i] == board[2][i] && board[2][i] == board[4][i] && board[0][i] != ' ') {
                return winnerVal(board[0][i]);
            }
        }
        
        //diagonal win
        if (board[0][0] == board[2][2] && board[2][2] == board[4][4] && board[2][2] != ' ') {
            return winnerVal(board[2][2]);
        }
        
        if (board[0][4] == board[2][2] && board[2][2] == board[4][0] && board[2][2] != ' ') {
            return winnerVal(board[2][2]);
        }

        //if tied
        else {
            return 0;
        }
    }


    /**
     * Small function that converts X to a -1 and O to 1
     * @param p
     * @return
     */
    private static int winnerVal(char p) {
        if (p == 'X') {
            return -1;
        } else if (p == 'O') {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * AI function to check every single empty spot on the board and decide on which is the best move to play.
     */
    private static void AIPlay() {
        int bestMoveX=0;
        int bestMoveY=0;
        double bestValue = Double.NEGATIVE_INFINITY;
        //check if slot is available
        for (int i = 0; i < 5; i = i + 2) {
            for (int j = 0; j < 5; j = j + 2) {
                if (board[i][j] == ' ') {  
                    board[i][j] = 'O'; //setting value to make a copy of the board for minimax.
                    double value = minimax(board, false);
                    board[i][j] = ' ';
                    //compare options
                    if (value > bestValue) {
                        bestValue = value;
                        bestMoveX = i;
                        bestMoveY = j;
                    }
                }
            }
        }

        board[bestMoveX][bestMoveY] = 'O';
    }

    /**
     * Minimax algorithm function. A recursive function that checks all the possible outcomes from a specific move.
     * @param b copy of board
     * @param maxing true if it is the maximizing turn, false if minimizing turn
     * @return value of the result of path
     */
    private static int minimax(char[][] b, boolean maxing) {
        if (endgameCheck() == true) {
            return getWinner();
        }

        if (maxing) {
            double bestValue = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < 5; i = i + 2) {
                for (int j = 0; j < 5; j = j + 2) {
                    if (b[i][j] == ' ') {
                        b[i][j] = 'O'; //setting value to make a copy of the board for minimax.
                        double value = minimax(b, false);
                        b[i][j] = ' ';
                        bestValue = Math.max(value, bestValue);
                    }
                }
            }
            // System.out.println(bestValue);
            return (int)bestValue;
        } else {
            double bestValue = Double.POSITIVE_INFINITY;
            for (int i = 0; i < 5; i = i + 2) {
                for (int j = 0; j < 5; j = j + 2) {
                    if (b[i][j] == ' ') {
                        b[i][j] = 'X'; //setting value to make a copy of the board for minimax.
                        double value = minimax(b, true);
                        b[i][j] = ' ';
                        bestValue = Math.min(value, bestValue);
                    }
                }
            }
            // System.out.println(bestValue);
            return (int)bestValue;
        }

        
    }
}

