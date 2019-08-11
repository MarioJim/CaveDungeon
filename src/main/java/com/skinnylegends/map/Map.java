package com.skinnylegends.map;

import com.skinnylegends.character.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Map implements Serializable {
    private Random random;
    private Room[][] rooms;
    private int minNumRooms, maxNumRooms, sizeX, sizeY;
    public int startX, startY;
    private final int doorChance = 30;

    public Map(long seed) {
        random = new Random(seed);
        minNumRooms = 10;
        maxNumRooms = 20;
        sizeX = 7;
        sizeY = 9;
        startX = (int) Math.floor((sizeX - 1) / 2.0);
        startY = (int) Math.floor((sizeY - 1) / 2.0);
        // Generate map layout
        generateMap();
        // Generate every room
        for (Room[] roomArr : rooms)
            for (Room r : roomArr)
                if (r != null)
                    r.generateRoom(random);
    }

    private void generateMap() {
        // Declare a counter of rooms created
        int numRooms;
        // Declare an ArrayList to store coordinates of rooms to create
        ArrayList<List<Integer>> roomsToCreate;
        // Retry map generation while the number of rooms is between the limits
        do {
            roomsToCreate = new ArrayList<>();
            // Declare array of rooms
            rooms = new Room[sizeX][sizeY];
            // Set center room as initial position
            declareRoom(startX, startY, Room.Type.INITIAL, roomsToCreate);
            // Generate rooms with random probabilities from roomsToCreate
            for (numRooms = 0; numRooms < roomsToCreate.size() - 1; numRooms++)
                declareRoom(
                        roomsToCreate.get(numRooms).get(0),
                        roomsToCreate.get(numRooms).get(1),
                        Room.getRandomType(random),
                        roomsToCreate
                );
        } while (roomsToCreate.size() < minNumRooms || roomsToCreate.size() > maxNumRooms);
        // Generate boss room
        declareRoom(roomsToCreate.get(numRooms).get(0), roomsToCreate.get(numRooms).get(1), Room.Type.BOSS, roomsToCreate);
    }

    private void declareRoom(int x, int y, Room.Type type, ArrayList<List<Integer>> roomsToCreate) {
        // 0 top, 1 right, 2 bottom, 3 left
        boolean[] doors = new boolean[4];
        // Generate random door arrangement if this isn't the last room (boss)
        if (type != Room.Type.BOSS)
            for (int i = 0; i < 4; i++)
                doors[i] = random.nextInt(100) < doorChance;
        // Check if it is next to walls (but leave the entire border with null rooms)
        doors[0] &= x != 1;
        doors[2] &= x != sizeX - 2;
        doors[3] &= y != 1;
        doors[1] &= y != sizeY - 2;
        // Check if there are doors from neighboring rooms to this one, if there are add them to this one
        if (rooms[x - 1][y] != null) doors[0] = rooms[x - 1][y].getDoor(2);
        if (rooms[x][y + 1] != null) doors[1] = rooms[x][y + 1].getDoor(3);
        if (rooms[x + 1][y] != null) doors[2] = rooms[x + 1][y].getDoor(0);
        if (rooms[x][y - 1] != null) doors[3] = rooms[x][y - 1].getDoor(1);
        // If there is a door without a room next to it, and the room hasn't been included in roomsToCreate, add it
        if (doors[0] && rooms[x - 1][y] == null && !roomsToCreate.contains(Arrays.asList(x - 1, y)))
            roomsToCreate.add(Arrays.asList(x - 1, y));
        if (doors[1] && rooms[x][y + 1] == null && !roomsToCreate.contains(Arrays.asList(x, y + 1)))
            roomsToCreate.add(Arrays.asList(x, y + 1));
        if (doors[2] && rooms[x + 1][y] == null && !roomsToCreate.contains(Arrays.asList(x + 1, y)))
            roomsToCreate.add(Arrays.asList(x + 1, y));
        if (doors[3] && rooms[x][y - 1] == null && !roomsToCreate.contains(Arrays.asList(x, y - 1)))
            roomsToCreate.add(Arrays.asList(x, y - 1));
        // If it is adjacent to the initial room, and its state is 3, make it 2
        if ((y == startY && (x == startX + 1 || x == startX - 1)) ||
                (x == startX && (y == startY - 1 || y == startY + 1)))
            if (type == Room.Type.HARD)
                type = Room.Type.EASY;
        // Create a room, set type and doors, and add the new room to map
        rooms[x][y] = new Room(type, doors);
    }

    public Room getRoom(int x, int y) {
        return rooms[x][y];
    }

    public void setPlayer(Player player) {
        rooms[startX][startY].setPlayer(player);
    }

    public String mapToString() {
        StringBuilder res = new StringBuilder();
        String[] roomSplit, row;
        // Find rooms to be trimmed from the left
        int minRooms = sizeY;
        for (int x = 1; x < sizeX - 2; x++) {
            int currentRooms = 0;
            while (rooms[x][currentRooms] == null && currentRooms < sizeY - 1) currentRooms++;
            minRooms = Math.min(minRooms, currentRooms);
        }
        // Generate a placeholder for null rooms
        StringBuilder whitespace = new StringBuilder().append(" ".repeat(Room.sizeY + 3));
        int leadingWhitespace = --minRooms * whitespace.length();
        // Generate the string
        for (int x = 1; x < sizeX - 1; x++) {
            row = new String[Room.sizeX + 2];
            Arrays.fill(row, "");
            for (int y = 1; y < sizeY - 1; y++) {
                if (rooms[x][y] != null) {
                    roomSplit = rooms[x][y].roomToString().split("\n");
                    // Show the state in the map
                    roomSplit[0] = "--" + rooms[x][y].getType().toString().charAt(0) + roomSplit[0].substring(3);
                    for (int i = 0; i < row.length; i++)
                        row[i] += roomSplit[i] + " ";
                } else
                    for (int i = 0; i < row.length; i++)
                        row[i] += whitespace.toString();
            }
            // Remove leading and trailing whitespace
            for (String s : row)
                res.append(s.substring(leadingWhitespace).replaceFirst("\\s++$", "")).append("\n");
        }
        // Remove empty lines
        return res.toString().replaceAll("\n\\s*\n", "\n");
    }
}