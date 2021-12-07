import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;

public class Game extends JFrame implements ActionListener{
    Scene scene;
    Timer gameLoopTimer;
    Player p1;
    Player2 p2;
    JLayeredPane pane;
    JLabel p1Score;
    JLabel p2Score;
    JLabel label;
    Audio winSE;
    JButton restart;
    public static void main(String[] args) throws Exception {
        new Game();
    }
    public Game(){
        // create the JFrame that holds the game canvas
        this.setSize(654, 516);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.setTitle("Recycling Wars");
        // use layered pane to prevent flashing images.
        pane = new JLayeredPane();
        pane = getLayeredPane();
        label = new JLabel();
        p1Score = new JLabel();
        p2Score = new JLabel();
        winSE = new Audio("win.wav");
        // init the scenes and players
        scene = new Scene("background-1.png");
        scene.setupTiles();
        scene.loadTile("Map.csv");
        p1 = new Player(scene, "ch.png", 50, 50);
        p2 = new Player2(scene, "ch2.png", 50, 50);
        // add the scene and player to the JFrame
        pane.add(p1, new Integer(3));
        pane.add(p2, new Integer(3));
        pane.add(scene, new Integer(2));
        pane.add(p1.hook, new Integer(4));
        pane.add(p2.hook, new Integer(4));
        initScore();
        // add keyboard listensers to players
        this.addKeyListener(p1);
        this.addKeyListener(p2);
        this.setVisible(true);

        // restart button
        restart = new JButton();
        restart.addActionListener(this);
        pane.add(restart, new Integer(5));
        restart.setBounds(250, 250, 100, 20);
        restart.setText("Restart");
        restart.setVisible(false);
        // define and start a game loop
        gameLoopTimer = new Timer(30, this);
        gameLoopTimer.start();
    }

    public void initScore(){
        p1Score.setFont(new Font("Serif", Font.BOLD, 15));
        p1Score.setText("Player 1 Score:" + p1.score);
        p1Score.setBounds(0, 0, 150, 100);
        p1Score.setForeground(Color.RED);
        pane.add(p1Score, new Integer(3));

        p2Score.setFont(new Font("Serif", Font.BOLD, 15));
        p2Score.setText("Player 2 Score:" + p2.score);
        p2Score.setBounds(500, 0, 150, 100);
        p2Score.setForeground(Color.RED);
        pane.add(p2Score, new Integer(3));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // action for each time the game loops
        if(e.getSource() == gameLoopTimer){
            // update scene and player
            // scene.update();
            scene.updateTiles();
            p1.update();
            p2.update();
            // check if player's hook hits another player
            if (p1.hook.collideWith(p2)){
                if (scene.p1Can.size() == 0){
                    announceWinner("Player 1 win!");
                    gameLoopTimer.stop();
                    winSE.play();
                }else{
                    p1.hook.reset();
                    // p1.ability++;
                    // p1.castAbility();
                    // p1Score.setText("Player 1 Score:" + p1.score);
                }
            }
            if (p2.hook.collideWith(p1)){
                if (scene.p2Can.size() == 0){
                    announceWinner("Player 2 win!");
                    gameLoopTimer.stop();
                    winSE.play();
                }else{
                    p2.hook.reset();
                    // p2.ability++;
                    // p1.castAbility();
                    // p1Score.setText("Player 2 Score:" + p2.score);
                }
            }
            for (int i = 0; i < scene.p1Can.size(); i++){
                if (p1.hook.collideWith(scene.p1Can.get(i))){
                    scene.p1Can.get(i).setState(0);
                    scene.p1Can.remove(i);
                    p1.hook.reset();
                    System.out.println(p1.hook.x);
                    p1.score++;
                    p1Score.setText("Player 1 Score:" + p1.score);
                }
            }
            for (int i = 0; i < scene.p2Can.size(); i++){
                if (p2.hook.collideWith(scene.p2Can.get(i))){
                    scene.p2Can.get(i).setState(0);
                    scene.p2Can.remove(i);
                    p2.hook.reset();
                    p2.score++;
                    p2Score.setText("Player 2 Score:" + p2.score);
                }
            }
        }
        if (e.getSource() == restart){
            this.dispose();
            new Game();
        }
    }
    // announce the winner by displaying text.
    public void announceWinner(String message){
        label.setFont(new Font("Serif", Font.BOLD, 50));
        label.setText(message);
        label.setBounds(160, 150, 400, 60);
        label.setForeground(Color.RED);
        pane.add(label, new Integer(3));
        restart.setVisible(true);
    }
}
