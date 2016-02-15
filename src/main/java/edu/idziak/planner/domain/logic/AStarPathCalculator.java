package edu.idziak.planner.domain.logic;

import com.google.common.collect.Lists;
import edu.idziak.planner.domain.entity.EntityCell;
import edu.idziak.planner.domain.entity.EntityDestinationCell;
import edu.idziak.planner.domain.entity.GridCell;
import edu.idziak.planner.domain.entity.GridModel;
import edu.idziak.planner.domain.entity.GridPath;
import edu.idziak.planner.domain.entity.Position;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AStarPathCalculator implements PathCalculator {

    @Override
    public Optional<GridPath> calculatePath(GridModel gridModel, EntityCell startCell, EntityDestinationCell goalCell) {
        Set<Node> openSet = new HashSet<>();
        Set<Node> closedSet = new HashSet<>();
        Map<Node, Node> cameFrom = new HashMap<>();

        Node start = Node.of(startCell.getPosition().getX(), startCell.getPosition().getY());
        Node goal = Node.of(goalCell.getPosition().getX(), goalCell.getPosition().getY());
        start.gScore = 0;
        start.fScore = heuristicCostEstimate(start, goal);
        openSet.add(start);

        while (!openSet.isEmpty()) {

            Node current = getNodeWithLowestFScore(openSet);
            if (current.equals(goal)) {
                GridPath.Builder builder = GridPath.builder();
                builder.start(startCell);
                builder.end(goalCell);
                List<Position> points = reconstructPath(cameFrom, goal, startCell);
                builder.points(points);
                return Optional.of(builder.build());
            }

            openSet.remove(current);
            closedSet.add(current);
            Set<Node> neighbors = getNeighbors(current, gridModel);
            for (Node neighbor : neighbors) {
                if (closedSet.contains(neighbor))
                    continue;
                double tentativeGScore = current.gScore + 1;
                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                } else if (tentativeGScore >= neighbor.gScore) {
                    continue;
                }
                cameFrom.put(neighbor, current);
                neighbor.gScore = tentativeGScore;
                neighbor.fScore = neighbor.gScore + heuristicCostEstimate(neighbor, goal);
            }
        }
        return Optional.empty();
    }

    private Node getNodeWithLowestFScore(Set<Node> openSet) {
        return openSet.stream().min(Comparator.comparing(node -> node.fScore)).get();
    }

    private Set<Node> getNeighbors(Node current, GridModel gridModel) {
        return Arrays.asList(
                Node.of(current.x, current.y - 1),
                Node.of(current.x, current.y + 1),
                Node.of(current.x - 1, current.y),
                Node.of(current.x + 1, current.y)
        ).stream().filter(node ->
                gridModel.isPositionWithinBounds(Position.of(node.x, node.y))
                        && !GridModel.isObstacleCell(gridModel.getCell(node.x, node.y).orElse(null)))
                .collect(Collectors.toSet());
    }

    private List<Position> reconstructPath(Map<Node, Node> cameFrom, Node current, GridCell start) {
        Position startPos = start.getPosition();
        Node startNode = Node.of(startPos.getX(), startPos.getY());
        List<Position> points = new LinkedList<>();
        while (!startNode.equals(cameFrom.get(current))) {
            current = cameFrom.get(current);
            points.add(Position.of(current.x, current.y));
        }
        return Lists.reverse(points);
    }

    private static double heuristicCostEstimate(Node a, Node b) {
        int xDiff = a.x - b.x;
        int yDiff = a.y - b.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    private static class Node {
        public int x;
        public int y;
        public double fScore;
        public double gScore;

        public static Node of(int x, int y) {
            Node node = new Node();
            node.x = x;
            node.y = y;
            node.gScore = Integer.MAX_VALUE;
            node.fScore = Integer.MAX_VALUE;
            return node;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + "," + fScore + "," + gScore + ")";
        }
    }
}
