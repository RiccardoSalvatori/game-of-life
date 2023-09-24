package gol.view;

import gol.model.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Collections;

import static java.awt.event.KeyEvent.*;

public class GoLFrame extends JFrame implements GoLView {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private final GoLPanel panel;

    public GoLFrame() {
        super("Game Of Life");
        this.setSize(WIDTH, HEIGHT);
        panel = new GoLPanel();
        this.add(panel);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    @Override
    public void draw(World world) {
        this.panel.setWorld(world);
        SwingUtilities.invokeLater(this::repaint);
    }

    private static class GoLPanel extends JPanel {
        private GoLGrid golGrid;
        private final Label golInfoLabel;
        private World world = new World(Collections.emptySet());

        public GoLPanel() {
            golGrid = new GoLGrid();
            BorderLayout borderLayout = new BorderLayout();
            golInfoLabel = new Label(golInfoText());
            golInfoLabel.setPreferredSize(new Dimension(getWidth(), 50));
            this.add(golInfoLabel);
            borderLayout.addLayoutComponent(golInfoLabel, BorderLayout.PAGE_START);

            this.add(golGrid);
            borderLayout.addLayoutComponent(golGrid, BorderLayout.CENTER);
            this.setLayout(borderLayout);


        }


        public void setWorld(World world) {
            this.world = world;
            this.golGrid.setWorld(this.world);
            this.golInfoLabel.setText(golInfoText());

        }

        private String golInfoText() {
            final long memUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            return String.format("Live cells: %d,  Generation: %d,  Mem: %s, Computation time: %d ms, Grid: %dx%d",
                    world.getLiveCellsCount(), world.getGeneration(),
                    humanReadableByteCountBin(memUsage), world.getTimeToComputeLastGeneration(),
                    golGrid.gridSize, golGrid.gridSize
            );
        }

        public static String humanReadableByteCountBin(long bytes) {
            long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
            if (absB < 1024) {
                return bytes + " B";
            }
            long value = absB;
            CharacterIterator ci = new StringCharacterIterator("KMGTPE");
            for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
                value >>= 10;
                ci.next();
            }
            value *= Long.signum(bytes);
            return String.format("%.1f %ciB", value / 1024.0, ci.current());
        }
    }


    static class GoLGrid extends JComponent implements MouseListener, KeyListener, MouseWheelListener, MouseMotionListener {
        private World world = new World(Collections.emptySet());
        private int xGridOrig = 0;
        private int yGridOrig = 0;
        private int gridSize = 100;
        private int startDragX;
        private int startDragY;

        public GoLGrid() {
            addMouseMotionListener(this);
            addMouseListener(this);
            addKeyListener(this);
            addMouseWheelListener(this);
            this.setFocusable(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponents(g);
            int xSize = getWidth() / gridSize;
            int ySize = getHeight() / gridSize;
            for (int x = 0; x < gridSize; x++) {
                for (int y = 0; y < gridSize; y++) {
                    final int rectx = x * xSize;
                    final int recty = y * ySize;
                    if (x == gridSize / 2 && y == gridSize / 2) {
                        drawViewCell(g, xSize, ySize, rectx, recty);
                    } else {
                        drawGameOfLifeCellCell(g, xSize, ySize, x, y, rectx, recty);
                    }


                }
            }
            g.dispose();
        }

        private void drawGameOfLifeCellCell(Graphics g, int xSize, int ySize, int x, int y, int rectx, int recty) {
            final int worldx = x + this.xGridOrig;
            final int worldy = y + this.yGridOrig;
            if (this.world.isAlive(worldx, worldy)) {
                g.fillRect(rectx, recty, xSize, ySize);
            } else {
                g.drawRect(rectx, recty, xSize, ySize);
            }
        }

        private void drawViewCell(Graphics g, int xSize, int ySize, int rectx, int recty) {
            final Color prev = g.getColor();
            g.setColor(Color.RED);
            g.fillRect(rectx, recty, xSize, ySize);
            g.setColor(prev);
        }

        public void setWorld(World world) {
            this.world = world;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            this.startDragX = e.getX();
            this.startDragY = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }


        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            final int keyCode = e.getKeyCode();
            if (keyCode == VK_UP) {
                this.yGridOrig -= 1;
            } else if (keyCode == VK_DOWN) {
                this.yGridOrig += 1;
            } else if (keyCode == VK_LEFT) {
                this.xGridOrig -= 1;
            } else if (keyCode == VK_RIGHT) {
                this.xGridOrig += 1;
            }
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getWheelRotation() > 0) {
                this.gridSize *= 1.1;
            }

            if (e.getWheelRotation() < 0) {
                this.gridSize /= 1.1;
            }

            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            this.xGridOrig -= (e.getX() - this.startDragX) * 1.1;
            this.yGridOrig -= (e.getY() - this.startDragY) * 1.1;
            this.startDragX = e.getX();
            this.startDragY = e.getY();
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
}
