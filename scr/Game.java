import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.net.URL;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Instance of the game
public class Game extends JPanel{
    private JLabel scoreLabel;
    int frameCounter = 0; 

      
public void setScoreLabel(JLabel label) {
    this.scoreLabel = label;
}

    int crx,cry;	//location of the crossing
    int car_x,car_y;    //x and y location of user's car
    int speedX,speedY;	//the movement values of the user's car
    int nOpponent;      //the number of opponent vehicles in the game
    String imageLoc[]; //array used to store oponnent car images
    int lx[],ly[];  //integer arrays used to store the x and y values of the oncoming vehicles
    int score;      //intger variable used to store the current score of the player
    int highScore;  //integer variable used to store the high score of the player
    int speedOpponent[]; //integer array used to store the spped value of each opponent vehicle in the game
    boolean isFinished; //boolean that will be used the end the game when a colision occurs
    boolean isUp, isDown, isRight, isLeft;  //boolean values that show when a user clicks the corresponding arrow key
    
    public Game(){
          
        crx = cry = -999;   //initialing setting the location of the crossing to (-999,-999)
        //Listener to get input from user when a key is pressed and released
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) { //when a key is released
                stopCar(e); //stop movement of car
            }
            public void keyPressed(KeyEvent e) { //when a key is pressed
                moveCar(e); //move the car in the direction given by the key
            }
        });
        setFocusable(true); 
        car_x = car_y = 300;    
        isUp = isDown = isLeft = isRight = false;  
        speedX = speedY = 0;    
        nOpponent = 0; 
        lx = new int[20]; 
        ly = new int[20]; 
        imageLoc = new String[20];
        speedOpponent = new int[20]; 
        isFinished = false; 
        score = highScore = 0;  
    }
    
   
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D obj = (Graphics2D) g;
        obj.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try{
            obj.drawImage(getToolkit().getImage("images/st_road.png"), 0, 0 ,this); //draw road on window
            if(cry >= -499 && crx >= -499) //if a road crossing has passed the window view
                obj.drawImage(getToolkit().getImage("images/cross_road.png"),crx,cry,this); //draw another road crossing on window
            
            obj.drawImage(getToolkit().getImage("images/car_self.png"),car_x,car_y,this);   //draw car on window
            
            if(isFinished){ //if collision occurs
                obj.drawImage(getToolkit().getImage("images/boom.png"),car_x-30,car_y-30,this); //draw explosion image on window at collision to indicate the collision has occured
            }
            
            if(this.nOpponent > 0){ //if there is more than one opponent car in the game
                for(int i=0;i<this.nOpponent;i++){ //for every opponent car
                    obj.drawImage(getToolkit().getImage(this.imageLoc[i]),this.lx[i],this.ly[i],this); //draw onto window
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    //function that moves the road scene across the window to make it seem like the car is driving
    void moveRoad(int count){
        if(crx == -999 && cry == -999){ //if the road crossing has passed by
            if(count%10 == 0){  
                crx = 499;      //send crossing location back at the beginning
                cry = 0;
            }
        }
        else{  
            crx--; 
        }
        if(crx == -499 && cry == 0){ 
            crx = cry = -999;   
        }
        car_x += speedX; 
        car_y += speedY; 
        
       
        if(car_x < 0)   
            car_x = 0; 
        
      
        if(car_x+93 >= 500) 
            car_x = 500-93; 
        
     
        if(car_y <= 124)    
            car_y = 124;  
        
        
        if(car_y >= 364-50) 
            car_y = 364-50; 
        
        
        for(int i=0;i<this.nOpponent;i++){ 
            this.lx[i] -= speedOpponent[i]; 
        }
        
       
        int index[] = new int[nOpponent];
        for(int i=0;i<nOpponent;i++){
            if(lx[i] >= -127){
                index[i] = 1;
            }
        }
        int c = 0;
        for(int i=0;i<nOpponent;i++){
            if(index[i] == 1){
                imageLoc[c] = imageLoc[i];
                lx[c] = lx[i];
                ly[c] = ly[i];
                speedOpponent[c] = speedOpponent[i];
                c++;
            }
        }
        
        score += nOpponent - c; 
        
        if(score > highScore)   //if the current score is higher than the high score
            highScore = score;  //update high score to the current score
        
        nOpponent = c;
        
       
        int diff = 0; //difference between users car and opponents car initially set to zero
        for(int i=0;i<nOpponent;i++){ //for all opponent cars
            diff = car_y - ly[i]; //diff is the distance between the user's car and the opponent car
            if((ly[i] >= car_y && ly[i] <= car_y+46) || (ly[i]+46 >= car_y && ly[i]+46 <= car_y+46)){   //if the cars collide vertically
                if(car_x+87 >= lx[i] && !(car_x >= lx[i]+87)){  //and if the cars collide horizontally
                    System.out.println("My car : "+car_x+", "+car_y);
                    System.out.println("Computer car : "+lx[i]+", "+ly[i]);
                    this.finish(); //end game and print end message
                }
            }
        }
    }
    
   
    void finish() {
    String str = "";
    isFinished = true;
    this.repaint();

//    if (score == highScore && score != 0)
//        str = "\nCongratulations!!! Its a high score";

    // Save score to database
    DBManager.updateScore(NameInput.playerName, score);  

    // Show result in new frame
    JFrame resultFrame = new JFrame("Game Over");
    resultFrame.setSize(400, 250);
    resultFrame.setLocationRelativeTo(null);
    resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    resultFrame.setLayout(new BorderLayout());

    JLabel scoreLabel = new JLabel("<html><center>Game Over!<br>Your Score: " + score + "</center></html>", SwingConstants.CENTER);
    scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
    resultFrame.add(scoreLabel, BorderLayout.CENTER);

    JButton backButton = new JButton("Back to Main Menu");
    backButton.addActionListener(e -> {
        resultFrame.dispose();
        SwingUtilities.getWindowAncestor(this).dispose();
        new MainMenu();
    });
    resultFrame.add(backButton, BorderLayout.SOUTH);

    resultFrame.setVisible(true);
}

    
    
    //function that handles input by user to move the user's car up, left, down and right
    public void moveCar(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP){   //if user clicks on the up arrow key
            isUp = true;
            speedX = CarConfigDialog.baseSpeed;     //moves car foward
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){ //if user clicks on the down arrow key
            isDown = true;
            speedX = -CarConfigDialog.baseSpeed;    //moves car backwards
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){ //if user clicks on the right arrow key
            isRight = true;
            speedY = 1;     //moves car to the right
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){ //if user clicks on the left arrow key
            isLeft = true;
            speedY = -1;    //moves car to the left
        }
    }
    
    //function that handles user input when the car is supposed to be stopped
    public void stopCar(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP){   //if user clicks on the up arrow key
            isUp = false;
            speedX = 0; //set speed of car to zero
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){    //if user clicks on the down arrow key
            isDown = false;
            speedX = 0; //set speed of car to zero
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){    //if user clicks on the left arrow key
            isLeft = false;
            speedY = 0; //set speed of car to zero
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){   //if user clicks on the right arrow key
            isRight = false;
            speedY = 0; //set speed of car to zero
        }
    }
    
    

   public void startGameLoop() {
    int count = 1;
    while (!isFinished) {
        moveRoad(count);
        repaint();

      
        frameCounter++;
        if (frameCounter % 10 == 0) {  //  (~0.5 second if 10ms/frame)
            score++;
            if (score > highScore)
                highScore = score;

            if (scoreLabel != null)
                scoreLabel.setText("Score: " + score + "     High Score: " + highScore);
        }

        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        count++;

      
        if (nOpponent < 4 && count % 200 == 0) {
            imageLoc[nOpponent] = "images/car_left_" + ((int) ((Math.random() * 100) % 3) + 1) + ".png";
            lx[nOpponent] = 499;

            int p = switch ((int) (Math.random() * 100) % 4) {
                case 0 -> 250;
                case 1 -> 300;
                case 2 -> 185;
                default -> 130;
            };
            ly[nOpponent] = p;
            speedOpponent[nOpponent] = (int) (Math.random() * 100) % CarConfigDialog.botSpeed + 2;
            nOpponent++;
        }
    }
}

    public static void main(String[] args) {
        NameInput.show();     // ask for player name
        new MainMenu();  
    }

}
