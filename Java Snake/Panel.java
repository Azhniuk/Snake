import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class Panel extends JPanel implements ActionListener{

    //initialization of variables
    static final int S_Width = 1000, S_Height = 600, Game_unit_size = 33; 

    Timer timer;
    Random random;
    int foodEaten, foodX, foodY, bodylength, speedShown = 0;
    boolean gameCont = false, firstGame = true, menu1 = true, speed;           //not continue
    int x, y, x1, y1, x2, y2, delay = 100, additional, level, speedDisplay = 0;
    char dir = 'R';   // go right

    String  col1Heading = "Speed",
            col1First = "Static",
            col1Second = "Dynamic";  
    

    static final int G_Size=(S_Width*S_Height)/(Game_unit_size*Game_unit_size);  // dev into squares
    final int 
    x_snake[]=new int[G_Size], 
    y_snake[]=new int[G_Size];      // snake position when the game starts

    ArrayList<Integer> walls = new ArrayList<Integer>(); // Create an ArrayList for walls



    Panel(){  //create a Panel
        this.setPreferredSize(new Dimension(S_Width,S_Height));
        this.setBackground(Color.lightGray);
        this.setFocusable(true);
        this.addKeyListener(new Keyboard());
        random = new Random();
    }


    public void GameStartClassic() {  //start 1 Classic
        speedShown = 0;
        foodEaten = 0;
        level = 1;
        delay = 140;

        newFoodPosition();
        gameCont = true;
        timer = new Timer(delay, this::actionPerformed) ;
        timer.start();
    }

    public void GameStartExtended() {  //start 2 Extended
        speedShown = 0;
        foodEaten = 0;
        level = 2;
        delay = 140;

        newWallPosition();                           
        newFoodPosition();
        gameCont = true;
        timer = new Timer(delay, this);
        timer.start();
    }

   

    //GRAPHICS
    public void paintComponent(Graphics graphic) {  //Call draw
        super.paintComponent(graphic);
        draw(graphic);

    }

    public void draw(Graphics graphic) {
        if(gameCont){ 
            //Apple
            graphic.setColor(Color.red);
            graphic.fillOval(foodX, foodY,Game_unit_size,Game_unit_size);

            //Wall
            for (int i = 0; i < walls.size() - 1; i = i + 2){
                graphic.setColor(new Color(153, 102, 0));
                graphic.fillRect(walls.get(i), walls.get(i+1), Game_unit_size, Game_unit_size);
                if(i == walls.size()-1){
                    break;
                }
            }
            
            //Snake
            for(int i=0; i<bodylength; i++){  
                if(i%2 == 0){    
                    graphic.setColor(new Color(0, 255, 51));
                    graphic.fillRect(x_snake[i],y_snake[i],Game_unit_size,Game_unit_size);
                }
                else{
                    graphic.setColor(new Color(0, 204, 0));
                    graphic.fillRect(x_snake[i],y_snake[i],Game_unit_size,Game_unit_size);
                }
            }

            //Score
            graphic.setColor(Color.black);
            graphic.setFont(new Font("Courier", Font.PLAIN, 35));
            FontMetrics font=getFontMetrics(graphic.getFont());
            graphic.drawString("Score:"+foodEaten,(S_Width-font.stringWidth("Score:"+foodEaten))/2,graphic.getFont().getSize()); 

             //Speed
             graphic.setFont(new Font("Courier", Font.PLAIN, 25));
             graphic.drawString("Speed:" + speedShown, (S_Width)/2 - 65, graphic.getFont().getSize() + 35); 
        }
        else{
            gameMenu(graphic);
        }
        }

    
  
    //Move
    public void move() {
        for(int i = bodylength; i > 0; i--){
            x_snake[i]=x_snake[i-1];
            y_snake[i]=y_snake[i-1];
        }
        switch (dir) {
            case 'U':
            y_snake[0]=y_snake[0] - Game_unit_size;
                break;
            case 'L':
                x_snake[0] = x_snake[0] - Game_unit_size;
                break;
            case 'D':
                y_snake[0] = y_snake[0] + Game_unit_size;
                break;
            case 'R':
                x_snake[0] = x_snake[0] + Game_unit_size;
                break;
        }
    }


    //Main Constructors
    public void newFoodPosition() {
        foodX=random.nextInt((int)(S_Width/Game_unit_size))*Game_unit_size;
        foodY=random.nextInt((int)(S_Height/Game_unit_size))*Game_unit_size;
        if (speed) {
            delay = setInterval(delay);
            speedShown +=1;
        }
    }


    public void newWallPosition() {
        x = random.nextInt((int)(S_Width/Game_unit_size))*Game_unit_size;
        y = random.nextInt((int)(S_Height/Game_unit_size))*Game_unit_size;

        additional = random.nextInt(1, 6);

        if(additional == 1){  //add one horisontal
            x1 = x + Game_unit_size;
            y1 = y;
        }
        else if(additional == 2){ //add one vertical
            x1 = x;
            y1 = y + Game_unit_size;
        }

        else if((additional == 3) && (walls.size() >= 8)) { //delete 4 points
            clearWalls();
            return;
        }

        else if(additional == 4){ //add one vertical
            x1 = x + Game_unit_size;
            y1 = y;
            x2 = x + 2*Game_unit_size;
            y2 = y;
            walls.add(x2);
            walls.add(y2);
        }
        else if(additional == 5){ //add one horisontal
            x1 = x ;
            y1 = y + Game_unit_size;
            x2 = x ;
            y2 = y + 2*Game_unit_size;
            walls.add(x2);
            walls.add(y2);
        }

        else { //add big one if not delete one
            x1 = x;
            y1 = y + Game_unit_size;
            x2 = x;
            y2 = y + 2*Game_unit_size;
            walls.add(x2);
            walls.add(y2);
        }


        walls.add(x);
        walls.add(y);
        walls.add(x1);
        walls.add(y1);
    }


    public int setInterval(int a) {
        if (gameCont){
            a = a *9 / 10;  
            timer.setDelay(a);
        }
        return a;
    }

// clear 
    public void clearArray(){
        walls.removeAll(walls);        
    }

    public void clearWalls(){
        int index = walls.size() - 1;
        
        walls.remove(index);
        walls.remove(index - 1);
        walls.remove(index - 2);
        walls.remove(index - 3);  
              
    }
 

    public void food_EatenOrNot() {     // for checking the food has been eaten by snake or not
        if((x_snake[0]==foodX)&&(y_snake[0]==foodY)){
            bodylength++;
            foodEaten++;
            newFoodPosition();

        if(level == 2){
            newWallPosition();
        }

        checkFoodPosition();
        }
    }

    //Hit
    public void checkHit() { 
        // if snake bite itself and if it collides with walls
        for (int i = bodylength; i > 0; i--)
            {if((x_snake[0]==x_snake[i])&&(y_snake[0]==y_snake[i]))
                {
                    menu1 = true;
                    gameCont=false;}}
        if(x_snake[0]<0)
            {
                gameOver();
            }
        if(x_snake[0]>S_Width)
           { 
            gameOver();
        }
        if (y_snake[0] < 0) 
        {    
            gameOver();
        }
        if (y_snake[0] > S_Height) 
        {
            gameOver();
        }


        if(level == 2) {
            //if snake hits walls
            for (int i = 0; i <= walls.size(); i = i + 2) {
                if ((x_snake[0] == walls.get(i)) && (y_snake[0] == walls.get(i + 1))) {
                    gameOver();
                }
                if (i == walls.size() - 2) {
                    break;
                }
            }
        }

        if(!gameCont){                   
            timer.stop();
        }
    }

    public void checkFoodPosition(){
         //if food in wall
         for (int i = 0; i < walls.size() - 1; i = i + 2){
            if (foodX == walls.get(i) && foodY == walls.get(i + 1)){
                newFoodPosition();
            }
            else{
                break;
            }
        }
    }


    public void gameMenu(Graphics graphic) 
    {
        //Clear
        graphic.setColor(Color.lightGray);      
        graphic.fillRect(200,200,600, 400);

        graphic.setColor(Color.black);      

        //Bold Main
        graphic.setFont(new Font("Courier", Font.BOLD, 75));
        FontMetrics fontBold = getFontMetrics(graphic.getFont());

        //First Game
        if (firstGame){
            graphic.drawString("Play", (S_Width - fontBold.stringWidth("Play")) / 2, S_Height/5 + 15);
        }
        else{
            graphic.drawString("Game Over", (S_Width - fontBold.stringWidth("Game Over")) / 2, S_Height/5 + 15);
        }


        //Plain Main
        graphic.setFont(new Font("Courier", Font.PLAIN, 35));
        FontMetrics fontMain = getFontMetrics(graphic.getFont());

        graphic.drawString("Score:" + foodEaten, (S_Width - fontMain.stringWidth("Score:" + foodEaten)) / 2, graphic.getFont().getSize());
        
        graphic.drawString("    1",              (S_Width) / 2, S_Height/2);
        graphic.drawString("    2",              (S_Width) / 2, S_Height/2 + (2 * graphic.getFont().getSize()));


        //First column
        graphic.drawString(col1First,    (S_Width) / 4, S_Height/2);
        graphic.drawString(col1Second, (S_Width) / 4, S_Height/2 + (2 * graphic.getFont().getSize()));

        
        graphic.setFont(new Font("Courier", Font.BOLD, 35));
 
        //Second column
        graphic.drawString("Press Number",   (S_Width) / 2, S_Height/2 - (2 * graphic.getFont().getSize()));
        graphic.drawString(col1Heading,          (S_Width) / 4, S_Height/2 - (2 * graphic.getFont().getSize()));

        graphic.setFont(new Font("Courier", Font.PLAIN, 25));
        graphic.drawString("Speed:" + speedShown, (S_Width)/2 - 65, graphic.getFont().getSize() + 35); 
    

        }

    public void gameOver(){
        menu1 = true;
        col1Heading = "Speed";
        col1First = "Static";
        col1Second = "Dynamic";  

        firstGame = false;    

        gameCont = false;
    }



    //action listener 
    public class Keyboard extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent way) {
            switch (way.getKeyCode()) {
                //side to move
                case KeyEvent.VK_LEFT:
                if (dir != 'R') {
                        dir = 'L';
                }
                    break;
                case KeyEvent.VK_UP:
                if (dir != 'D') {
                    dir = 'U';
                }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (dir != 'L') {
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (dir != 'U') {
                        dir = 'D';
                    }
                    break;


                case KeyEvent.VK_1 :   // 1 Classic
                    if (!gameCont){
                        if(menu1){
                            bodylength=2;
                            dir='R';
                            Arrays.fill(x_snake,0);
                            Arrays.fill(y_snake,0);
                            clearArray();
                            
                            menu1 = false;
    
                            //menu change
                            col1Heading = "Level";
                            col1First = "Classic";
                            col1Second = "Extended";
                            speed = false;
                            gameMenu(getGraphics());                          
                        }
                        else{
                            GameStartClassic();                            
                        }
                    }
                    break;


                case KeyEvent.VK_2 :   // 2 Extended
                    if (!gameCont){
                        if(menu1){
                            bodylength=2;
                            dir='R';
                            Arrays.fill(x_snake,0);
                            Arrays.fill(y_snake,0);
                            clearArray();
                            menu1 = false;
    
                            //menu change
                            col1Heading = "Level";
                            col1First = "Classic";
                            col1Second = "Extended";
                            speed = true;
                            gameMenu(getGraphics()); 

                        }
                        else{
                            GameStartExtended();
                        }
                    }
                    break;
            }            
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent a) {
        if (gameCont) {
            move();
            food_EatenOrNot();
            checkHit();
        }
        else{

            gameMenu(getGraphics());
        }
        repaint();
    }

 
}
    

