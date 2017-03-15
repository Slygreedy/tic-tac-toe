
//need to make this logic 2 dimensional and integrate with board (and maybe remove some redundant constructors?)...

package TicTacToe;

import java.util.LinkedList;
import java.util.List;

public class TicTacToeLogic {

    public char [] board;
    public char turn;
    private int size = 3;

    //different constructors
    public TicTacToeLogic() {
        this.board =  "         ".toCharArray();
        this.turn = 'x';
    }

    //this constructor is used to complete move method which takes this constructor as input
    public TicTacToeLogic (char[] board, char turn) {
        this.board = board;
        this.turn = turn;
    }

    //this constructor is used to complete win test
    public TicTacToeLogic (String str) {
        this.board = str.toCharArray();
        this.turn = 'x';
    }

    //created to complete final minimax test
    public TicTacToeLogic (String str, char turn) {
        this.board = str.toCharArray();
        this.turn = turn;
    }

    //turn array of board (object) into a String - don't need an override - why?
    public String toString() {
        return new String(board);
    }

    public TicTacToeLogic move (int idx) {
        char[] newBoard = board.clone();
        newBoard[idx] = turn;
        return new TicTacToeLogic(newBoard, turn == 'x' ? 'o' : 'x');
    }

    public Integer[] possibleMoves() {
        List<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                list.add(i);
            }
        }
        Integer[] array = new Integer[list.size()];
        list.toArray(array);
        return array;
    }

    //not sure what this does except it is used to calculate win_line which is used by win?
    public boolean win_line (char turn, int start, int step) {
        for (int i = 0; i < 3; i++) {
            if (board[start + step*i] != turn) {
                return false;
            }
        }
        // why is this method returning true or false?
        return true;
    }

    //this describes how to win i.e. line up OR line down plus 2 types of diagonal line
    //need to understand logic better
    public boolean win (char turn) {
        for (int i = 0; i < size; i++) {
            if (win_line(turn, i*size, 1) || win_line(turn, i, size)) {
                return true;
            }
        }
        if (win_line(turn, size-1, size-1) || win_line(turn, 0, size+1)) {
            return true;
        }
        return false;
    }

    public int minimax() {
        if (win('x')) {
            return 100;
        }
        if (win('o')) {
            return -100;
        }
        if (possibleMoves().length == 0) {
            return 0;
        }
        Integer mm = null;
        for (Integer idx : possibleMoves()) {
            Integer value = move(idx).minimax();
            if (mm == null || turn == 'x' && mm < value || turn == 'o' && value < mm) {
                mm = value;
            }
        }
        return mm + (turn == 'x' ? - 1 : 1);
    }

    public int bestMove() {
        Integer mm = null;
        int best = -1;
        for (Integer idx : possibleMoves()) {
            Integer value = move(idx).minimax();
            if (mm == null || turn == 'x' && mm < value || turn == 'o' && value < mm) {
                mm = value;
                best = idx;
            }
        }
        return best;
    }

    public boolean gameEnd() {
        return win('x') || win ('o') || possibleMoves().length == 0;
    }
}
