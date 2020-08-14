package src;
import java.awt.Color;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

/**
 * this could also be named app or program
 */
public class Main {

    static Timer timer = new Timer();
    static TimerTask task;
    static Timer timer2 = new Timer();
    static TimerTask task2;

    public static boolean nowalls;
    public static boolean pride;
    public static boolean picasso;
    public static boolean retro;

    public static void main(String[] args) {

        nowalls = false;
        pride = false;
        picasso = false;
        retro = false;
        int speed = 100;
        int scale = 20;
        int mapsize = 20;

        Color headColor = Color.BLACK;
        Color bodyColor = Color.BLUE;

        for(int i = 0; i < args.length; i++) {
            if (args.length > 0) {
                if (args[i].equals("--no-walls") || args[i].equals("-n")) {
                    nowalls = true;
                }
                if (args[i].equals("--pride") || args[i].equals("-p")) {
                    pride = true;
                }
                if (args[i].equals("--scale") || args[i].equals("-s")) {
                    try {
                        Integer.parseInt(args[i+1]);
                    } catch (Exception e) {
                        System.out.println("--scale requires a number. See --help.");
                        System.exit(0);
                    }
                    scale = Integer.parseInt(args[i + 1]);
                }
                if (args[i].equals("--mapsize") || args[i].equals("-m")) {
                    try {
                        Integer.parseInt(args[i+1]);
                    } catch (Exception e) {
                        System.out.println("--mapsize requires a number. See --help.");
                        System.exit(0);
                    }
                    mapsize = Integer.parseInt(args[i + 1]);
                }
                if(args[i].equals("--picasso") || args[i].equals("-pi")){
                    picasso = true;
                    pride = true;
                }
                if(args[i].equals("--retro") || args[i].equals("-r")){
                    try {
                        Integer.parseInt(args[i+1]);
                    } catch (Exception e) {
                        System.out.println("--retro requires a number. See --help.");
                        System.exit(0);
                    }
                    retro = true;
                    speed = Integer.parseInt(args[i + 1]);
                    headColor = new Color(10,40,20);
                    bodyColor = new Color(10,80,40);
                }

                if(args[i].equals("--help") || args[i].equals("-h")){
                    System.out.println("\n" +
                            "The following parameters are available:\n" +
                            "\n" +
                            "--no-walls     / -n    this disables all walls\n" +
                            "--pride        / -p    this turns the snake into a rainbow snake\n" +
                            "--picasso      / -pi   this turns the snake into a Snakasso\n" +
                            "--mapsize      / -m    this allows you to create a map of your own size\n" +
                            "                       default: 20\n" +
                            "--scale NUM    / -s    this allows you to scale the game\n" +
                            "                       default: 20\n" +
                            "--retro NUM    / -r    this allows you to play the nokia version of snake\n" +
                            "                       the number is the speed. recommended: 300\n" +
                            "--help         / -h    display this help\n" +
                            "\n" +
                            "");

                    System.exit(0);
                }
            }
        }
        /* unit test */
        Head head = new Head(new Point(6, 4), headColor);


        Snake snake = new Snake(head, nowalls, pride, mapsize, retro);
        Body body = new Body(new Point(5, 4), bodyColor);

        snake.append(body);

        /* screen */
        JFrame frame = new JFrame();

        Game game = new Game(snake, scale, mapsize, picasso, retro);

        frame.add(game);
        frame.addKeyListener(game);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        task = new TimerTask() {

            @Override
            public void run() {
                snake.move();
            }
        };
        timer.schedule(task, speed, speed);

        task2 = new TimerTask() {

            @Override
            public void run() {
                game.repaint();
            }
        };
        timer2.schedule(task2, 1, 1);

    }
}