import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MarsRover {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Insert horizontal map size:");
        int sizex = reader.nextInt();
        System.out.println("Insert vertical map size:");
        int sizey = reader.nextInt();

        System.out.println("Insert number of obstacles:");
        int obstaclesNumber = reader.nextInt();

        int x;
        int y;
        List<List<Integer>> obstacles = new ArrayList<>();
        for (int i = 0; i < obstaclesNumber; i++) {
            System.out.println("Obstacle " + i);
            System.out.println("Insert coordinate x:");
            x = reader.nextInt();
            System.out.println("Insert coordinate y:");
            y = reader.nextInt();
            obstacles.add(Arrays.asList(x, y));
        }

        System.out.println("Insert horizontal initial rover position:");
        int roverx = reader.nextInt();
        System.out.println("Insert vertical initial rover position:");
        int rovery = reader.nextInt();
        System.out.println("Insert initial rover direction:");
        String roverz = reader.next(); //n = north, e = east, w = west, s = south

        do {
            System.out.println("Insert command (f = forward, b = backward, l = turn left, r = turn right):");
            String command = reader.next();
            if (command.equals("f")) {
                if (roverz.equals("n")) {
                    rovery += 1;
                }
                if (roverz.equals("w")) {
                    roverx -= 1;
                }
                if (roverz.equals("s")) {
                    rovery -= 1;
                }
                if (roverz.equals("e")) {
                    roverx += 1;
                }
            }
            if (command.equals("b")) {
                if (roverz.equals("n")) {
                    rovery -= 1;
                }
                if (roverz.equals("w")) {
                    roverx += 1;
                }
                if (roverz.equals("s")) {
                    rovery += 1;
                }
                if (roverz.equals("e")) {
                    roverx -= 1;
                }
            }
            if (command.equals("l")) {
                if (roverz.equals("n")) {
                    roverz = "w";
                }
                if (roverz.equals("w")) {
                    roverz = "s";
                }
                if (roverz.equals("s")) {
                    roverz = "e";
                }
                if (roverz.equals("e")) {
                    roverz = "n";
                }
            }
            if (command.equals("r")) {
                if (roverz.equals("n")) {
                    roverz = "e";
                }
                if (roverz.equals("e")) {
                    roverz = "s";
                }
                if (roverz.equals("s")) {
                    roverz = "w";
                }
                if (roverz.equals("w")) {
                    roverz = "n";
                }
            }
            System.out.println(String.format("Rover is at x:%d y:%d facing:%s", roverx, rovery, roverz));
        } while (true);

    }

}
