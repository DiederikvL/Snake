package scr;

/* Includes */
import java.awt.Color;
import java.awt.Point;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Complete snake with logic
 */
public class Snake {

    /* Fields */

    /* Objects */
    private Head head;
    private Directions prefDir = Directions.EAST;
    private Directions realDir = Directions.EAST;
    private PlaySound playSound = new PlaySound();
    private Random random = new Random();

    /* Food is public so Game.java can access it to draw it */
    public Food food = new Food(new Point(1, 3), Color.GREEN);

    private boolean nowalls;
    private boolean pride;
    private int mapsize;
    private boolean retro;
    private int red = 0;
    private int green = 0;
    private int blue = 255;

    /* Files for sound */
    private String eatSound = "../audiofiles/ploink.wav";
    private String gameOver = "../audiofiles/gameover.wav";

    /* Constructor */
    public Snake(Head head, boolean nowalls, boolean pride, int mapsize, boolean retro) {
        this.head = head;
        this.nowalls = nowalls;
        this.pride = pride;
        this.mapsize = mapsize;
        this.retro = retro;
    }

    /* Functions */

    /* Append a block to the snake */
    public void append(Body next) {

        /* Find the last block */
        Body last = findLastBlock();

        /* Set up a reference to the new block */
        last.setNext(next);

        /* Set up the new block */
        next.setPrev(last);
    }

    /* Move the snake without arguments */
    public void move() {

        /* Call the real function with the stored preference */
        this.move(prefDir);
    }

    /* Move the snake with a preference */
    public void move(Directions dir) {

        /* We start at the last snake */
        Body last = findLastBlock();

        /* Store the last location, for when we ate */
        Point lp = last.getLoc();

        /* Loop through the snake */
        while (last.getPrev() != null) {

            /* Move the snake forwards */
            last.setLoc(last.getPrev().getLoc());
            last = last.getPrev();
        }

        /* Store the location of the head, so we can change it */
        int headX = head.getLoc().x;
        int headY = head.getLoc().y;

        /* Switch case to move the head in the right direction */
        switch (dir) {
            case NORTH:
                headY -= 1;
                head.setLoc(new Point(headX, headY));
                this.realDir = Directions.NORTH;
                break;

            case EAST:
                headX += 1;
                head.setLoc(new Point(headX, headY));
                this.realDir = Directions.EAST;
                break;

            case SOUTH:
                headY += 1;
                head.setLoc(new Point(headX, headY));
                this.realDir = Directions.SOUTH;
                break;

            case WEST:
                headX -= 1;
                head.setLoc(new Point(headX, headY));
                this.realDir = Directions.WEST;
                break;
        }

        /* We can still use last, because it is now at the head */
        while (last.getNext() != null) {
            last = last.getNext();

            /* If the head collides with a bodyElement */
            if (head.isCollision(last)) {

                /* Call gameOver to shut down the game */
                gameOver();
            }

            /* If the user wants to play without walls */
            if (nowalls) {

                /* If the user is about to hit the wall */
                if (headX < 0) {

                    /* Warp him to the other side */
                    head.setLoc(new Point(headX + mapsize, headY));
                } else if (headX > mapsize - 1) {
                    head.setLoc(new Point(headX - mapsize, headY));
                } else if (headY < 0) {
                    head.setLoc(new Point(headX, headY + mapsize));
                } else if (headY > mapsize - 1) {
                    head.setLoc(new Point(headX, headY - mapsize));
                }
            }
            /* The user wants to play with walls */
            else {

                /* If you are hitting a wall */
                if (head.getLoc().x < 0 || head.getLoc().x > mapsize - 1 || head.getLoc().y < 0
                        || head.getLoc().y > mapsize - 1) {

                    /* Call gameOver to shut down the game */
                    gameOver();
                }
            }
        }

        /* Checks if the snake collides with the food */
        if (head.isCollision(food)) {

            /* Play a sound to give extra feedback */
            new PlaySound().play(eatSound);

            /* If the user wants to play the colorful snake */
            if (pride) {

                /* Append a piece to the snake, it will use the color of the food */
                append(new Body(lp, food.getColor()));

                /* Set random colors */
                red = random.nextInt(255 - 0 + 1);
                green = random.nextInt(255 - 0 + 1);
                blue = random.nextInt(255 - 0 + 1);

                /* Give the food a new color, randomized */
                food.setColor(new Color(red, green, blue, 255));
            }
            /* If the user wants to play with a normal snake */
            else {

                /* If the user wants to play without retro mode */
                if (!retro) {

                    /* Append a piece to the snake, blue */
                    append(new Body(lp, Color.BLUE));
                }
                /* If the user wants to play regular mode */
                else {

                    /* Append a greenish body part, like Nokia does */
                    append(new Body(lp, new Color(10, 80, 40)));
                }
            }

            /* Set the food to a new location */
            food.setLoc(new Point(random.nextInt(mapsize), random.nextInt(mapsize)));

            /* Makes sure the food does not spawn on top of the snake */
            while (last.getNext() != null) {

                /* If the food is on top of the body */
                if (last.getLoc().x == food.getLoc().x && last.getLoc().y == food.getLoc().y) {

                    /* Give it a new location */
                    food.setLoc(new Point(random.nextInt(mapsize), random.nextInt(mapsize)));
                }

                /* Keep looping until we are at the end */
                last = last.getNext();
            }
        }
    }

    /* Set the direction based on keyboard input */
    public void setDirection(Directions dir) {
        switch (dir) {
            case NORTH:

                /* Check if the preference would make the snake turn 180 degrees */
                if (this.realDir != Directions.SOUTH)
                    this.prefDir = dir;
                break;
            case EAST:
                if (this.realDir != Directions.WEST)
                    this.prefDir = dir;
                break;
            case SOUTH:
                if (this.realDir != Directions.NORTH)
                    this.prefDir = dir;
                break;
            case WEST:
                if (this.realDir != Directions.EAST)
                    this.prefDir = dir;
                break;
        }
    }

    /* Find the last block of the snake */
    public Body findLastBlock() {

        /* Loop through the snake */
        Body last = head;
        while (last.getNext() != null) {
            last = last.getNext();
        }

        /* Return the tail */
        return last;
    }

    /* Return the head */
    public Head getHead() {
        return head;
    }

    /* Return the length by calculating */
    public int getLength() {

        /* Keep track of the length in a temporary integer */
        int length = 1;

        /* Loop through the snake */
        Body last = head;
        while (last.getNext() != null) {
            last = last.getNext();

            /* Add a point for every block */
            length++;
        }

        /* Return the length of the snake */
        return length - 2;
    }

    /* Halt the game (useful for gameOver()) */
    private void sleep(int timer) {
        try {
            TimeUnit.MILLISECONDS.sleep(timer);
        } catch (Exception e) {
            System.out.println("Sleep failed. Exception: " + e);
        }
    }

    /* Shut down the game */
    private void gameOver() {
        playSound.play(gameOver);
        sleep(3000);
        System.exit(0);
    }
}