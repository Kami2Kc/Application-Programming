import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener
{
    private SpaceInvaders space;

    Player[] player = new Player[1];
    Bullet[] bullet = new Bullet[1];
    Enemy[] enemies = new Enemy[55];
    EnemyBullet[] eBullets = new EnemyBullet[3];
    Shelter[][][] shelter = new Shelter[4][4][3];

    int enemyType = 0;
    int enemySize = 25;
    int enemySpacing = enemySize + 15;
    int enemyMoveDown = 15;
    int bulletW = 2;
    int bulletH = 6;
    int bulletSpeed = 6;
    int shelterSize = 15;
    int eBulletW = 2;
    int eBulletH = 8;

    int[] enemyIndex = new int[3];

    double playerX;
    double playerY = 500;
    double playerVelocityX = 0;
    double bulletX;
    double bulletY;
    double enemyX = 40;
    double enemyY = 40;
    double enemySpeed = 0.1;
    double enemySpeedIncrease = 0.02;
    double eBulletSpeed = 0.8;
    double eBulletSpeedIncrease = 0.2;
    double shelterX = 69;
    double shelterY = 400;

    boolean playerGoRight = false;
    boolean playerGoLeft = false;

    Color enemyColor;

    Timer timer = new Timer(6, this);

    Game(SpaceInvaders space)
    {
        this.space = space;
        playerX = (space.getContentPane().getWidth() / 2.0) - 20;
        timer.start();

        makePlayer();
        loadBullet();
        buildShelter();
        startRound();

        addKeyListener(this);
        setFocusable(true);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        requestFocus(true);
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) g;

        checkPlayerDead();

        // Only run the game while the player is alive / playing
        if (space.playerLives > 0)
        {
            // Pick 3 random enemies that are alive to shoot bullets
            for (int p = 0; p < 3; p++)
            {
                enemyIndex[p] = pickEnemy();

                if (enemyIndex[0] == enemyIndex[1])
                {
                    enemyIndex[1] = pickEnemy();
                }

                if (enemyIndex[1] == enemyIndex[2])
                {
                    enemyIndex[2] = pickEnemy();
                }

                if (enemyIndex[0] == enemyIndex[2])
                {
                    enemyIndex[2] = pickEnemy();
                }

                if (enemies[enemyIndex[p]].visible)
                {
                    enemyShoot(enemies[enemyIndex[p]]);
                }
            }

            // Draw the black background
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, 600, 600);

            // Draw the players bullet, make it move and make sure to despawn it when it reaches y = 0
            if (bullet[0].visible)
            {
                bullet[0].y -= bulletSpeed;

                g2.setColor(Color.YELLOW);
                g2.fill(new Rectangle2D.Double(bullet[0].x - 2, bullet[0].y + 30, bulletW, bulletH));

                if (bullet[0].y < 0)
                {
                    bullet[0].visible = false;
                    bullet[0].y = playerY - 5;
                    bullet[0].x = playerX;
                }
            }

            // Draw all the enemies and color code them
            for (Enemy enemy : enemies) {
                if (enemy.visible) {
                    if (enemy.type == 0) {
                        enemyColor = new Color(144, 0, 255);
                        g2.setColor(enemyColor);
                    }

                    if (enemy.type == 1) {
                        enemyColor = Color.BLUE;
                        g2.setColor(enemyColor);
                    }

                    if (enemy.type == 2) {
                        enemyColor = Color.WHITE;
                        g2.setColor(enemyColor);
                    }

                    g2.fill(new Rectangle2D.Double(enemy.x, enemy.y, enemySize, enemySize));

                    if (enemy.y + enemySize >= 550) {
                        space.playerLives = -1;
                    }
                }

                // Check if bullet hit an enemy
                if ((bullet[0].x >= enemy.x && bullet[0].x <= enemy.x + enemySize) && (bullet[0].y >= enemy.y && bullet[0].y <= enemy.y + enemySize)) {
                    killEnemy(enemy);
                }

                else if ((bullet[0].x + bulletW >= enemy.x && bullet[0].x + bulletW <= enemy.x + enemySize) && (bullet[0].y >= enemy.y && bullet[0].y <= enemy.y + enemySize)) {
                    killEnemy(enemy);
                }

                else if ((bullet[0].x >= enemy.x && bullet[0].x <= enemy.x + enemySize) && (bullet[0].y + bulletH >= enemy.y && bullet[0].y + bulletH <= enemy.y + enemySize)) {
                    killEnemy(enemy);
                }

                else if ((bullet[0].x + bulletW >= enemy.x && bullet[0].x + bulletW <= enemy.x + enemySize) && (bullet[0].y + bulletH >= enemy.y && bullet[0].y + bulletH <= enemy.y + enemySize)) {
                    killEnemy(enemy);
                }
            }


            // Draw the shelters / shields, check for if player shot it, check for if enemy shot it, check if enemy is touching it
            g2.setColor(Color.GREEN);
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    for (int k = 0; k < 3; k++)
                    {
                        if (shelter[i][j][k].visible)
                        {
                            g2.fill(new Rectangle2D.Double(shelter[i][j][k].x , shelter[i][j][k].y, shelterSize, shelterSize ));
                        }

                        // Check if play shot shelter block
                        if ((bullet[0].x >= shelter[i][j][k].x && bullet[0].x <= shelter[i][j][k].x + shelterSize) && (bullet[0].y >= shelter[i][j][k].y && bullet[0].y <= shelter[i][j][k].y + shelterSize))
                        {
                            bulletShelterBlock(shelter[i][j][k]);
                        }

                        // Check if enemy shot the shelter block
                        for (EnemyBullet eb : eBullets)
                        {
                            if ((eb.x >= shelter[i][j][k].x && eb.x <= shelter[i][j][k].x + shelterSize) && (eb.y + eBulletH >= shelter[i][j][k].y && eb.y + eBulletH <= shelter[i][j][k].y + shelterSize))
                            {
                                enemyBulletHitShelter(shelter[i][j][k], eb);
                            }

                            else if ((eb.x + eBulletW >= shelter[i][j][k].x && eb.x + eBulletW <= shelter[i][j][k].x + shelterSize) && (eb.y + eBulletH >= shelter[i][j][k].y && eb.y + eBulletH <= shelter[i][j][k].y + shelterSize))
                            {
                                enemyBulletHitShelter(shelter[i][j][k], eb);
                            }
                        }

                        // Check if enemy is touching the shelter blocks and remove them
                        for (Enemy e : enemies)
                        {
                            if ((e.x >= shelter[i][j][k].x && e.x <= shelter[i][j][k].x + shelterSize) && (e.y >= shelter[i][j][k].y && e.y <= shelter[i][j][k].y + shelterSize))
                            {
                                enemyTouchedShelter(shelter[i][j][k] , e);
                            }

                            else if ((e.x + enemySize >= shelter[i][j][k].x && e.x + enemySize <= shelter[i][j][k].x + shelterSize) && (e.y >= shelter[i][j][k].y && e.y <= shelter[i][j][k].y + shelterSize))
                            {
                                enemyTouchedShelter(shelter[i][j][k] , e);
                            }

                            else if ((e.x >= shelter[i][j][k].x && e.x <= shelter[i][j][k].x + shelterSize) && (e.y + enemySize >= shelter[i][j][k].y && e.y + enemySize <= shelter[i][j][k].y + shelterSize))
                            {
                                enemyTouchedShelter(shelter[i][j][k] , e);
                            }

                            else if ((e.x + enemySize >= shelter[i][j][k].x && e.x + enemySize <= shelter[i][j][k].x + shelterSize) && (e.y + enemySize >= shelter[i][j][k].y && e.y + enemySize <= shelter[i][j][k].y + shelterSize))
                            {
                                enemyTouchedShelter(shelter[i][j][k] , e);
                            }
                        }
                    }
                }
            }

            // Draw enemy bullets and despawn them when they reach y = 700 to allow more time between shots
            // Also check if player has been hit by them
            for (EnemyBullet eb : eBullets)
            {
                if (eb.visible)
                {
                    g2.setColor(Color.RED);
                    g2.fill(new Rectangle2D.Double(eb.x, eb.y, eBulletW, eBulletH));

                    if (eb.y > 700)
                    {
                        eb.visible = false;
                    }

                    if ((eb.x >= player[0].x && eb.x <= player[0].x + 40) && (eb.y >= player[0].y && eb.y <= player[0].y + 10))
                    {
                        enemyBulletHitPlayer(eb);
                    }

                    else if ((eb.x >= player[0].x && eb.x <= player[0].x + 40) && (eb.y + eBulletH >= player[0].y && eb.y + eBulletH <= player[0].y + 10))
                    {
                        enemyBulletHitPlayer(eb);
                    }

                    else if ((eb.x + eBulletW >= player[0].x && eb.x + eBulletW <= player[0].x + 40) && (eb.y >= player[0].y && eb.y <= player[0].y + 10))
                    {
                        enemyBulletHitPlayer(eb);
                    }

                    else if ((eb.x + eBulletW >= player[0].x && eb.x + eBulletW <= player[0].x + 40) && (eb.y + eBulletH >= player[0].y && eb.y + eBulletH <= player[0].y + 10))
                    {
                        enemyBulletHitPlayer(eb);
                    }
                }
            }

            // Check players health and pick color to add extra representation of the players health
            if (space.playerLives == 3) g2.setColor(Color.GREEN);
            if (space.playerLives == 2) g2.setColor(Color.YELLOW);
            if (space.playerLives == 1) g2.setColor(Color.RED);

            // Draw player and line below the player
            g2.fill(new Rectangle2D.Double(player[0].x, player[0].y + 30, 40, 10));
            g2.fillPolygon(new int[] {(int) Math.round(player[0].x), (int) Math.round(player[0].x + 20), (int) Math.round(player[0].x + 40)}, new int[] {540, 520, 540}, 3);
            g2.drawLine(0, 550, 600, 550);

            moveEnemies();
            enemyBulletTravel();
            checkEnemiesDead();
        }
    }

    // Update all enemies and move them
    // If enemies reach either side of the screen move them down and make them move the other way
    // Only moves down when a live enemy reaches a side
    public void moveEnemies()
    {
        for (Enemy enemy : enemies) {
            if (enemy.turnLeft) {
                enemy.x -= enemy.speed;
            }

            if (!enemy.turnLeft) {
                enemy.x += enemy.speed;
            }

            if (enemy.x > 585 - enemySize) {
                if (enemy.visible) {
                    for (Enemy enemy1 : enemies) {
                        enemy1.turnLeft = true;
                        enemy1.y += enemyMoveDown;
                    }
                }
            }

            if (enemy.x < 0) {
                if (enemy.visible) {
                    for (Enemy enemy2 : enemies) {
                        enemy2.turnLeft = false;
                        enemy2.y += enemyMoveDown;
                    }
                }
            }
        }
    }

    // Update all the bullets that are currently shot
    public void enemyBulletTravel()
    {
        for (EnemyBullet eb : eBullets)
        {
            if (eb.visible)
            {
                eb.y += eb.speed;
            }
        }
    }

    // Keep check of how many enemies are alive and if 1 is left increase its speed
    // If 0 enemies are left it starts the next round
    public void checkEnemiesDead()
    {
        int numberOfEnemiesLeft = 55;
        for (Enemy enemy : enemies)
        {
            if (!enemy.visible)
            {
                numberOfEnemiesLeft --;
            }
        }

        if (numberOfEnemiesLeft == 0)
        {
            loadBullet();
            buildShelter();
            startRound();
        }

        if (numberOfEnemiesLeft == 1)
        {
            for (Enemy enemy : enemies)
            {
                enemy.speed = 3;
            }
        }
    }

    // If enemy is hit by a players bullet and are both currently visible (on screen / alive )
    // it will kill that enemy and depending on the type of enemy it will add points to the players core and updated the JLabel
    // Also increase the enemies speed for each kill by a fixed amount
    public void killEnemy(Enemy e)
    {
        if (e.visible && bullet[0].visible)
        {
            e.visible = false;

            if (e.type == 0)
            {
                space.playerScore += 30;
            }

            if (e.type == 1)
            {
                space.playerScore += 20;
            }

            if (e.type == 2)
            {
                space.playerScore += 10;
            }
            space.playerScoreLabel.setText("SCORE : " + space.playerScore + "      ");
            bullet[0].visible = false;
            bullet[0].x = playerX;
            bullet[0].y = playerY;

            for (Enemy enemy : enemies)
            {
                enemy.speed += enemySpeedIncrease;
            }
        }
    }

    // Make the bullets spawn at the coordinates of an enemy to make it appear like they are shooting them
    // Only shoot bullets when there are bullets available (maximum of 3 on the screen at a time)
    public void enemyShoot(Enemy e)
    {
        if (!eBullets[0].visible && e.y < 484)
        {
            eBullets[0].x = (e.x + (enemySize / 2.0));
            eBullets[0].y = e.y;
            eBullets[0].visible = true;
        }

        else if (!eBullets[1].visible && e.y < 484)
        {
            eBullets[1].x = (e.x + (enemySize / 2.0));
            eBullets[1].y = e.y;
            eBullets[1].visible = true;
        }

        else if (!eBullets[2].visible && e.y < 484)
        {
            eBullets[2].x = (e.x + (enemySize / 2.0));
            eBullets[2].y = e.y;
            eBullets[2].visible = true;
        }
    }

    // Pick an enemy at random from the array and return their index
    public int pickEnemy()
    {
        Random enemy = new Random();
        return enemy.nextInt(enemies.length);
    }

    // If a players bullet hit a shelter block it will despawn it (set visible to false)
    public void bulletShelterBlock(Shelter s)
    {
        if (s.visible && bullet[0].visible)
        {
            s.visible = false;
            bullet[0].visible = false;
            bullet[0].x = playerX;
            bullet[0].y = playerY;
        }
    }

    // If an enemies bullet hits a shelter block it will despawn it (set visible to false)
    public void enemyBulletHitShelter(Shelter s , EnemyBullet eb)
    {
        if (s.visible && eb.visible)
        {
            s.visible = false;
            eb.visible = false;
        }
    }

    // If a bullet hits the player it will take away 1 point of health and update the lives JLabel and de-spawn that bullet
    public void enemyBulletHitPlayer(EnemyBullet eb)
    {
        if (eb.visible)
        {
            eb.visible = false;
            space.playerLives --;
            space.playerLivesLabel.setText("LIVES : " + space.playerLives + "      ");
        }
    }

    // If an enemy touches a shelter block it will cause it to despawn
    public void enemyTouchedShelter(Shelter s, Enemy e)
    {
        if (s.visible & e.visible)
        {
            s.visible = false;
        }
    }

    // Restarts the round and despawn all enemy bullets to avoid getting hit at the start of a new round
    // Each round the speed of enemy bullets increases by 0.2 (20% or first round speed)
    public void startRound()
    {
        enemyX = 40;
        enemyY = 40;
        enemySpeed = 0.1;
        enemyType = 0;

        for (int i = 0; i < enemies.length; i++)
        {
            if (i == 11)
            {
                enemyType = 1;
            }

            if (i == 33)
            {
                enemyType = 2;
            }

            enemies[i] = new Enemy(enemyX, enemyY, enemySpeed, true, false ,enemyType);
            enemyX += enemySpacing;
            if ((i + 1) % 11 == 0)
            {
                enemyX = 40;
                enemyY += enemySpacing;
            }
        }
        eBulletSpeed += eBulletSpeedIncrease;
        loadEnemyBullets();
    }

    // Create the shelter block and set all to visible
    // they are also reset at the start of each round
    public void buildShelter()
    {
        double tempX;
        double tempY = shelterY + shelterSize;

        for (int i = 0; i < 4; i++)
        {
            tempX = (shelterX + (i * 129));

            for (int j = 0; j < 4; j++)
            {
                if (j == 0 || j == 3)
                {
                    tempY = shelterY + shelterSize;
                }
                if (j == 1 || j == 2)
                {
                    tempY = shelterY;
                }

                for (int k = 0; k < 3; k++)
                {

                    shelter[i][j][k] = new Shelter(tempX, tempY, true);
                    tempY += shelterSize;
                }
                tempX += shelterSize;
            }
        }
    }

    // Creates the player
    public void makePlayer()
    {
        player[0] = new Player(playerX, playerY, playerVelocityX);
    }

    // Creates the bullets and resets them
    public void loadBullet()
    {
        bullet[0] = new Bullet(bulletX, bulletY, bulletSpeed, false);
    }

    // Creates the enemy bullets and resets them
    public void loadEnemyBullets()
    {
        for (int j = 0; j < eBullets.length; j++)
        {
            Enemy e = enemies[pickEnemy()];
            eBullets[j] = new EnemyBullet((e.x + (enemySize / 2.0)), e.y, eBulletSpeed, false);
        }
    }

    // Check if the players health is below or equal to 0 to see if the player is dead or quit
    // change the screen to the enter score screen to allow the player to enter their name which then saves their score and name to
    // the scoreboard file
    // Also resets the enemy bullet speed to original value
    public void checkPlayerDead()
    {
        if (space.playerLives <= 0)
        {
            eBulletSpeed = 0.8;
            startRound();
            buildShelter();
            space.gameOverScore.setText("YOUR SCORE : " + space.playerScore);
            space.cardLayout.show(space.panel, "Enter score");
        }
    }

    // Updates the screen and makes sure that the player can not move off screen
    public void actionPerformed(ActionEvent ke)
    {
        repaint();
        player[0].x += player[0].speed;

        if (player[0].x > 544)
        {
            player[0].x = 544;
        }

        if (player[0].x < 0)
        {
            player[0].x = 0;
        }
    }

    // Makes the player move to the right
    public void right()
    {
        player[0].speed = 1.5;
    }

    // Makes the player move to the left
    public void left()
    {
        player[0].speed = -1.5;
    }

    // If a movement key is released this will stop the player
    public void stop()
    {
        player[0].speed = 0;
    }

    // Get player inputs
    public void keyPressed(KeyEvent ke)
    {
        int code = ke.getKeyCode();

        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
        {
            playerGoRight = true;
            right();
        }

        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
        {
            playerGoLeft = true;
            left();
        }

        if (code == KeyEvent.VK_SPACE)
        {
            if (!bullet[0].visible)
            {
                bullet[0].x = player[0].x + 20;
                bullet[0].y = player[0].y;
                bullet[0].visible = true;
            }
        }
    }

    public void keyTyped(KeyEvent ke)
    {

    }

    // Check when player releases a key
    public void keyReleased(KeyEvent ke)
    {
        int code = ke.getKeyCode();

        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
        {
            playerGoRight = false;
        }

        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
        {
            playerGoLeft = false;
        }

        if (!playerGoRight && !playerGoLeft)
        {
            stop();
        }
    }
}