package com.leejs1030.connectfour.game;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.leejs1030.connectfour.datastructure.Board;
import com.leejs1030.connectfour.myexception.WrongInputException;

public class Game{
    private Board board;
    private int turn;
    static Scanner sc = new Scanner(System.in);

    public Game(){
        this.board = new Board();
        this.turn = 0;
    }

    public int playGame(){
        showStartMsg();
        int winner = -1;
        while(winner < 0){
            board.showBoard();
            showTurn();
            int col = useTurn();
            if(isFinished(this.board.getTop(col) - 1, col)) winner = this.turn;
            changeTurn();
        }
        return winner;
    }

    private void changeTurn(){ turn ^= 1; }

    private char getChip(){
        if(turn == 1) return 'O';
        else return 'X';
    }

    private void showStartMsg(){
        System.out.println("새 게임을 시작합니다.");
        System.out.println("Player 0: O");
        System.out.println("Player 1: X");
        System.out.print("\n\n\n");
    }

    private void showTurn(){ System.out.println("Player " + turn + " (" + getChip() + ")의 차례 입니다!"); }

    private int useTurn(){
        while(true){
            try{
                int col = getUserInput();
                this.board.insertChip(col, getChip());
                return col;
            } catch(InputMismatchException err){
                System.out.println("정수로 입력해 주세요!");
            }catch(WrongInputException err){
                System.out.println(err.getMessage());
            }
        }
    }

    private int getUserInput() throws WrongInputException, InputMismatchException{
        System.out.print("몇 번째 열에 놓으시겠습니까? ");
        int pos = sc.nextInt();
        if(!(0 < pos && pos < 7)) throw new WrongInputException(0);
        // else if(this.board.isFull(pos)) throw new WrongInputException(1);
        return pos - 1;
    }

    private boolean checkCol(int row, int col, char t){
        int cr, cl;
        for(cr = col + 1; cr < 7; cr++){
            if(board.get(row, cr) != t) break;
        }
        cr--;
        for(cl = col - 1; cl >= 0; cl--){
            if(board.get(row, cl) != t) break;
        }
        cl++;
        return (cr - cl + 1 >= 4);
    }

    private boolean checkRow(int row, int col, char t){
        int rr, rl;
        for(rr = row + 1; rr < 6; rr++){
            if(board.get(rr, col) != t) break;
        }
        rr--;
        for(rl = row - 1; rl >= 0; rl--){
            if(board.get(rl, col) != t) break;
        }
        rl++;
        return (rr - rl + 1 >= 4);
    }

    private boolean checkDiagonal(int row, int col, char t){
        return checkUpperRight(row, col, t) || checkLowerRight(row, col, t);
    }

    private boolean checkUpperRight(int row, int col, char t){
        int ir, il;
        for(ir = 1; row + ir < 6 && col + ir < 7; ir++){
            if(board.get(row + ir, col + ir) != t) break;
        }
        ir--;
        for(il = -1; row + il >= 0 && col + il >= 0; il--){
            if(board.get(row + il, col + il) != t) break;
        }
        il++;
        return (ir - il + 1 >= 4);
    }

    private boolean checkLowerRight(int row, int col, char t){
        int ir, il;
        for(ir = 1; row + ir < 6 && col - ir >= 0; ir++){
            if(board.get(row + ir, col - ir) != t) break;
        }
        ir--;
        for(il = -1; row + il >= 0 && col - il < 7; il--){
            if(board.get(row + il, col - il) != t) break;
        }
        il++;
        return (ir - il + 1 >= 4);
    }

    private boolean isFinished(int row, int col){
        char t = this.board.get(row, col);
        return checkCol(row, col, t) || checkRow(row, col, t) || checkDiagonal(row, col, t);
    }
}
