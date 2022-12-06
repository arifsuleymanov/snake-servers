package com.codenjoy.dojo.snake.client;

import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.RandomDice;

import java.util.List;

public class YourSolver implements Solver<Board> {

    private Dice dice;
    private Board board;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        System.out.println(board.toString());

        //Current coordinates of apple and Snake head
        int appleY = board.getApples().get(0).getY();
        int appleX = board.getApples().get(0).getX();
        int headerX = board.getHead().getX();
        int headerY = board.getHead().getY();

        int nextX = 0;
        int nextY = 0;

        if (headerX < appleX) {
            nextX = 1;
        }
        if (headerX > appleX) {
            nextX = -1;
        }
        if (headerY < appleY) {
            nextY = 1;
        }
        if (headerY > appleY) {
            nextY = -1;
        }

        if (nextX > 0) {
            if (ifPresentBarrier(board, board.getHead(), nextX, 0)) {
                return Direction.RIGHT.toString();
            }
        }
        if (nextY < 0) {
            if (ifPresentBarrier(board, board.getHead(), 0, nextY)) {
                return Direction.DOWN.toString();
            }
        }
        if (nextX < 0) {
            if (ifPresentBarrier(board, board.getHead(), nextX, 0)) {
                return Direction.LEFT.toString();
            }
        }
        if (nextY > 0) {
            if (ifPresentBarrier(board, board.getHead(), 0, nextY)) {
                return Direction.UP.toString();
            }
        }
        if (ifPresentBarrier(board, board.getHead(), 1, 0)) {
            return Direction.RIGHT.toString();
        }
        if (ifPresentBarrier(board, board.getHead(), 0, -1)) {
            return Direction.DOWN.toString();
        }
        if (ifPresentBarrier(board, board.getHead(), -1, 0)) {
            return Direction.LEFT.toString();
        }
        if (ifPresentBarrier(board, board.getHead(), 0, 1)) {
            return Direction.UP.toString();
        }

        return Direction.LEFT.toString();
    }

    public static void main(String[] args) {
        WebSocketRunner.runClient(
                // paste here board page url from browser after registration
                "http://159.89.27.106/codenjoy-contest/board/player/4zy06yxc84g3xohm86xs?code=8628184304176585005",
                new YourSolver(new RandomDice()),
                new Board());
    }

    public static boolean ifPresentBarrier(Board board, Point point, int nextX, int nextY) {
        if (ifPresentStone(board, board.getStones(), point, nextX, nextY)) {
            if (ifPresentStone(board, board.getWalls(), point, nextX, nextY)) {
                if (ifPresentStone(board, board.getSnake(), point, nextX, nextY)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean ifPresentStone(Board board, List<Point> stones, Point head, int nextX, int nextY) {
        for (Point stone : stones) {
            if (head.getX() + nextX == stone.getX() && head.getY() + nextY == stone.getY()) {
                return false;
            }
        }
        return true;
    }


}