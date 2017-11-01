package windyday;

import windyday.blocks.*;
import windyday.utils.ColorPalette;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.ClosedDirectoryStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/*
 * Josh Coates - Windy Day Game
 */

public class WindyDayGame extends JPanel {

    public static final int X_FRAME_DIM = 512;
    public static final int Y_FRAME_DIM = 512;
    public static final int MS_PER_UPDATE = 17;

    private final String logoImgPath = "windyday/assets/windyTextPaint.png";
    private BufferedImage logo;
    private Font pixelatedFont;

    private int leafFrequency;
    private int cloudFrequency;
    private int groundFrequency;
    private int scoringFrequency;

    private boolean gameInProgress;

    private List<Block> blocks;
    private Player player;

    private int score;
    private int finalScore;

    private boolean running;

    public WindyDayGame() {
        setBackground(ColorPalette.blue);
        this.setFocusable(true);
        loadImages();
        pixelatedFont = new Font("Pixelated Regular", Font.BOLD, 18);

        blocks = Collections.synchronizedList(new ArrayList<Block>());

        // Initialise some starting blocks.
        blocks.add(new Ground(-128));
        blocks.add(new Ground(32));
        blocks.add(new Ground(192));
        blocks.add(new Ground(352));
        blocks.add(new Ground(512));
        blocks.add(new Cloud(432));
        blocks.add(new Cloud(332));
        blocks.add(new Cloud(232));
        blocks.add(new Cloud(132));
        blocks.add(new Cloud(32));

        player = new Player();

        assignKeys();
        running = true;
        gameInProgress = false;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(X_FRAME_DIM, Y_FRAME_DIM);
    }

    private void loadImages() {
        try {
            File logoImg = new File(logoImgPath);
            logo = ImageIO.read(logoImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void assignKeys() {
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "up");
        this.getActionMap().put("up", new MovementAction(player, 0, -1));

        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "down");
        this.getActionMap().put("down", new MovementAction(player, 0, 1));

        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "left");
        this.getActionMap().put("left", new MovementAction(player, -1, 0));

        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "right");
        this.getActionMap().put("right", new MovementAction(player, 1, 0));

        this.getInputMap().put(KeyStroke.getKeyStroke("N"), "newRound");
        this.getActionMap().put("newRound", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                newRound();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        synchronized (blocks) {
            for (Block block : blocks) {
                block.paint(g2d);
            }
        }

        player.paint(g2d);
        if (gameInProgress) {
            player.paint(g2d);
        } else {
            g2d.setColor(ColorPalette.orange);
            g2d.fillRect((int) (X_FRAME_DIM / 2) - 132,
                    (int) (Y_FRAME_DIM / 2) - 115,
                    265,
                    230);
            g2d.drawRect((int) (X_FRAME_DIM / 2) - 132,
                    (int) (Y_FRAME_DIM / 2) - 115,
                    265,
                    230);

            g2d.drawImage(logo,
                    (int) (X_FRAME_DIM / 2) - 112,
                    (int) (Y_FRAME_DIM / 2) - 85,
                    null);

            g2d.setFont(pixelatedFont);
            g2d.setColor(ColorPalette.darkBlue);
            g2d.drawString(" press n to play! ",
                    (int) (X_FRAME_DIM / 2) - 80,
                    (int) (Y_FRAME_DIM / 2) + 75);

            if (finalScore > 0) {
                g2d.drawString(" score = " + finalScore,
                        (int) (X_FRAME_DIM / 2) - 80,
                        (int) (Y_FRAME_DIM / 2) + 95);
            }
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    public void startGame() {
        leafFrequency = 7;
        cloudFrequency = 58;
        groundFrequency = 160;
        scoringFrequency = 294;
        assignKeys();
        runGameLoop();
    }

    private void newRound() {
        synchronized (blocks) {
            ListIterator<Block> iter = blocks.listIterator();
            while (iter.hasNext()) {
                Block block = iter.next();
                if (block instanceof Leaf || block instanceof Player) {
                    iter.remove();
                }
            }

            player = new Player();
        }
        assignKeys();

        leafFrequency = 7;
        cloudFrequency = 58;
        groundFrequency = 160;
        scoringFrequency = 294;
        score = 0;
        gameInProgress = true;
    }

    // Detects collision between two axis-aligned rectangles.
    private boolean collisionOccurs(Block b1, Block b2) {

        if (b1.getXPos() < b2.getXPos() + b2.getWidth()
                && b1.getXPos() + b1.getWidth() > b2.getXPos()
                && b1.getYPos() < b2.getYPos() + b2.getHeight()
                && b1.getHeight() + b1.getYPos() > b2.getYPos()) {

            return true;
        }

        return false;
    }

    private synchronized void resolveCollision(Block block) {
        player.setYVelocity(-player.getYVelocity());
        player.setXVelocity(block.getXVelocity() >= 0 ?
                block.getXVelocity() + 1 : block.getXVelocity());
    }

    private synchronized void spawnBlocks(int frameCount) {
        if (gameInProgress) {
            if (frameCount % leafFrequency == 0) {
                blocks.add(new Leaf());
            }

            if (frameCount % scoringFrequency == 0) {
                score += 10;
            }
        }

        if (frameCount % cloudFrequency == 0) {
            blocks.add(new Cloud());
        }

        if (frameCount % groundFrequency == 0) {
            blocks.add(new Ground());
        }
    }

    public void runGameLoop() {
        Thread loop = new Thread() {
            public void run() {
                gameLoop();
            }
        };
        loop.start();
    }

    private synchronized void updateGame(int timeStep, int frameCount) {
        synchronized (blocks) {
            spawnBlocks(frameCount);

            for (int i = 0; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                block.update(timeStep);
                if (collisionOccurs(block, player)) {
                    resolveCollision(block);
                    player.update(timeStep);
                }
            }
            player.update(timeStep);

            ListIterator<Block> iter = blocks.listIterator();
            while (iter.hasNext()) {
                Block block = iter.next();
                if (Math.abs(block.getXPos()) > 3 * X_FRAME_DIM) {
                    iter.remove();
                }
            }

            if (player.getXPos() < 0 || player.getXPos() > X_FRAME_DIM) {
                player.setAlive(false);
                gameInProgress = false;
            }
        }

        finalScore = score;
    }

    private void gameLoop() {
        double lastTime = System.currentTimeMillis();
        double lag = 0.0;
        int frameCount = 1;
        int timeStep = 1;
        long sleepTime;

        while (running) {
            double currentTime = System.currentTimeMillis();
            double elapsed = currentTime - lastTime;

            updateGame(timeStep, frameCount);

            paintImmediately(0, 0, X_FRAME_DIM, Y_FRAME_DIM);

            sleepTime = (long) (currentTime + MS_PER_UPDATE - System.currentTimeMillis());
            try {
                if (sleepTime < 0) {
                    sleepTime = 0;
                }
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            frameCount++;
            lastTime = currentTime;
        }
    }
}
