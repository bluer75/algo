package alg;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class RobotRoomCleaner {

    interface Robot {
        // Returns true if the cell in front is open and robot moves into the cell.
        // Returns false if the cell in front is blocked and robot stays in the current cell.
        public boolean move();

        // Robot will stay in the same cell after calling turnLeft/turnRight.
        // Each turn will be 90 degrees.
        public void turnLeft();

        public void turnRight();

        // Clean the current cell.
        public void clean();
    }

    private static class RobotImpl implements Robot {

        private int[][] grid;
        private int[] pos;
        private Move move;

        public RobotImpl(int[][] grid, int row, int col) {
            this.grid = grid;
            this.pos = new int[] { row, col };
            move = Move.U;
        }

        @Override
        public boolean move() {
            int[] next = move.move(pos);
            if (next[0] >= 0 && next[0] < grid.length && next[1] >= 0 && next[1] < grid[next[0]].length
                    && grid[next[0]][next[1]] != 0) {
                pos[0] = next[0];
                pos[1] = next[1];
                return true;
            }
            return false;
        }

        @Override
        public void turnLeft() {
            move = move.left();
        }

        @Override
        public void turnRight() {
            move = move.right();
        }

        @Override
        public void clean() {
            grid[pos[0]][pos[1]] = 2;
        }
    }

    private Deque<Move> stack = new LinkedList<>();
    private Map<Integer, Map<Integer, Integer>> visited = new HashMap<>();
    private int[] pos = new int[] { 0, 0 };
    private Move direction = Move.U;

    public void cleanRoom(Robot robot) {
        visit(pos, 1);
        while (true) {
            // explore
            explore(robot);
            if (!stack.isEmpty()) {
                // backtrack - position robot and move
                Move move = stack.pop();
                int[] newPos = null;
                if (direction.left() == move) {
                    robot.turnLeft();
                } else if (direction.right() == move) {
                    robot.turnRight();
                } else if (direction.reverse() == move) {
                    robot.turnRight();
                    robot.turnRight();
                }
                direction = move;
                robot.move();
                newPos = direction.move(pos);
                pos[0] = newPos[0];
                pos[1] = newPos[1];
            } else {
                return;
            }
        }
    }

    private void explore(Robot robot) {
        boolean moved = false;
        int[] newPos = null;
        while (true) {
            moved = false;
            // try forward
            newPos = direction.move(pos);
            if (!visited(newPos) && robot.move()) {
                moved = true;
            }
            if (!moved) {
                // try left
                newPos = direction.left().move(pos);
                if (!visited(newPos)) {
                    robot.turnLeft();
                    if (robot.move()) {
                        direction = direction.left();
                        moved = true;
                    } else {
                        robot.turnRight();
                    }
                }
            }
            if (!moved) {
                // try right
                newPos = direction.right().move(pos);
                if (!visited(newPos)) {
                    robot.turnRight();
                    if (robot.move()) {
                        direction = direction.right();
                        moved = true;
                    } else {
                        robot.turnLeft();
                    }
                }
            }
            if (!moved) {
                // try backward
                newPos = direction.right().right().move(pos);
                if (!visited(newPos)) {
                    robot.turnRight();
                    robot.turnRight();
                    if (robot.move()) {
                        direction = direction.reverse();
                        moved = true;
                    } else {
                        robot.turnLeft();
                        robot.turnLeft();
                    }
                }
            }
            if (moved) {
                pos[0] = newPos[0];
                pos[1] = newPos[1];
                visit(pos, 1);
                stack.push(direction.reverse());
            } else {
                // no more moves
                break;
            }
        }
        // clean current cell and mark it as cleaned
        robot.clean();
        visit(pos, 2);
    }

    private boolean visited(int[] pos) {
        return visited.getOrDefault(pos[0], Collections.emptyMap()).getOrDefault(pos[1], 0) > 0;
    }

    private void visit(int[] pos, int value) {
        visited.putIfAbsent(pos[0], new HashMap<>());
        visited.get(pos[0]).put(pos[1], value);
    }

    private enum Move {
        U(-1, 0) {
            @Override
            Move reverse() {
                return D;
            }

            @Override
            Move left() {
                return L;
            }

            @Override
            Move right() {
                return R;
            }
        },
        D(1, 0) {
            Move reverse() {
                return U;
            }

            @Override
            Move left() {
                return R;
            }

            @Override
            Move right() {
                return L;
            }
        },
        L(0, -1) {
            @Override
            Move reverse() {
                return R;
            }

            @Override
            Move left() {
                return D;
            }

            @Override
            Move right() {
                return U;
            }
        },
        R(0, 1) {
            @Override
            Move reverse() {
                return L;
            }

            @Override
            Move left() {
                return U;
            }

            @Override
            Move right() {
                return D;
            }
        };

        private int row;
        private int col;

        Move(int row, int col) {
            this.row = row;
            this.col = col;
        }

        abstract Move reverse();

        abstract Move left();

        abstract Move right();

        int[] move(int[] pos) {
            return new int[] { pos[0] + row, pos[1] + col };
        }
    }

    public static void main(String... args) {
        //        int[][] grid = new int[][] { { 1, 1, 1, 1, 1, 0, 1, 1 }, { 1, 1, 1, 1, 1, 0, 1, 1 }, { 1, 0, 1, 1, 1, 1, 1, 1 },
        //                { 0, 0, 0, 1, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1, 1, 1, 1 } };
        //        Robot robot = new RobotImpl(grid, 1, 3);
        new RobotRoomCleaner().cleanRoom(new RobotImpl(new int[][] { { 1 }, { 1 } }, 0, 0));
    }
}
