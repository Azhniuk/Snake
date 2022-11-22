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
    int foodEaten, foodX, foodY, bodylength = 2;
    boolean gameCont = false;           //not continue
    int x, y, x1, y1, x2, y2, DELAY, additional;
    char dir = 'R';   // go right

    

    static final int G_Size=(S_Width*S_Height)/(Game_unit_size*Game_unit_size);  // dev into squares
    final int 
    x_snake[]=new int[G_Size], 
    y_snake[]=new int[G_Size];      // snake position when the game starts


    ArrayList<Integer> walls = new ArrayList<Integer>(); // Create an ArrayList for walls


   




    Panel(){
        this.setPreferredSize(new Dimension(S_Width,S_Height));
        this.setBackground(Color.lightGray);
        this.setFocusable(true);
        this.addKeyListener(new Keyboard());
        random = new Random();
        GameStart();
    }


    //Main constructors
    public void GameStart() {  //start
        DELAY = 170;
        newWallPosition();                           
        newFoodPosition();
        gameCont = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics graphic) {  //Call draw
        super.paintComponent(graphic);
        draw(graphic);
    }


    //GRAPHICS
    public void draw(Graphics graphic) {
        if(gameCont){ 
            //apple
            graphic.setColor(Color.red);
            graphic.fillOval(foodX, foodY,Game_unit_size,Game_unit_size);

            //Wall
            for (int i = 0; i <= walls.size(); i = i+4){
                graphic.setColor(new Color(153, 102, 0));
                graphic.fillRect(walls.get(i), walls.get(i+1), Game_unit_size, Game_unit_size);
                graphic.fillRect(walls.get(i+2), walls.get(i+3), Game_unit_size, Game_unit_size);
                if(i == walls.size() - 4){
                    break;
                }
            }
            
            
                
             

            //snake
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
            FontMetrics font_me=getFontMetrics(graphic.getFont());
            graphic.drawString("Score:"+foodEaten,(S_Width-font_me.stringWidth("Score:"+foodEaten))/2,graphic.getFont().getSize()); 
        }
        else{
            gameOver(graphic);
            
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


    //additional constructors 
    public void newFoodPosition() {
        foodX=random.nextInt((int)(S_Width/Game_unit_size))*Game_unit_size;
        foodY=random.nextInt((int)(S_Height/Game_unit_size))*Game_unit_size;

    }

    public void clear(){
        walls.removeAll(walls);
        newWallPosition();
        
        //walls = new ArrayList<Integer>();
        
    }
    public void newWallPosition() {
        x = random.nextInt((int)(S_Width/Game_unit_size))*Game_unit_size;
        y = random.nextInt((int)(S_Height/Game_unit_size))*Game_unit_size;
        

        x1 = x + Game_unit_size;
        y1 = y;

        walls.add(x);
        walls.add(y);
        walls.add(x1);
        walls.add(y1);
        }
            
    

    public void food_EatenOrNot() {     // for checking the food has been eaten by snake or not
        if((x_snake[0]==foodX)&&(y_snake[0]==foodY)){
            bodylength++;
            foodEaten++;
            newFoodPosition();
            newWallPosition();
        }
    }

    public void checkHit() { 
        // if snake bite itself and if it collides with walls
        for (int i = bodylength; i > 0; i--)
            {if((x_snake[0]==x_snake[i])&&(y_snake[0]==y_snake[i]))
                {gameCont=false;}}
        if(x_snake[0]<0)
            {gameCont = false;}
        if(x_snake[0]>S_Width)
           { gameCont = false;}
        if (y_snake[0] < 0) 
        {    gameCont = false;}
        if (y_snake[0] > S_Height) 
        {    gameCont = false;}
        
        //if it hits walls
        for (int i = 0; i <= walls.size(); i=i+2){
            System.out.println(walls);
            if((x_snake[0]==walls.get(i)) && (y_snake[0]==walls.get(i+1)))
            {    gameCont = false;}
            if(i == walls.size() - 2){
                break;
            }
            
        }
        
        if(!gameCont)
          {  timer.stop();
        }
    }


    public void gameOver(Graphics graphic) {    // When ever game is over 
        graphic.setColor(Color.red);

        //1
        graphic.setFont(new Font("Courier", Font.PLAIN, 35));
        FontMetrics font_me = getFontMetrics(graphic.getFont());
        graphic.drawString("Score:" + foodEaten, (S_Width - font_me.stringWidth("Score:" + foodEaten)) / 2, graphic.getFont().getSize());
        
        //2
        graphic.drawString("Press R to Replay", (S_Width - font_me.stringWidth("Press R to Replay")) / 2, S_Height / 2 - 150);  

        //3
        graphic.setFont(new Font("Courier", Font.BOLD, 75));
        FontMetrics font_me1 = getFontMetrics(graphic.getFont());
        graphic.drawString("Game Over", (S_Width - font_me1.stringWidth("Game Over")) / 2, S_Height/2);

        }


    //action listener 
    public class Keyboard extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent way) {
            switch (way.getKeyCode()) {
                //side to move
                case KeyEvent.VK_LEFT:
                        dir='L';
                    break;
                case KeyEvent.VK_UP:
                        dir = 'U';
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

                case KeyEvent.VK_R:
                if(!gameCont){
                    foodEaten=0;
                    bodylength=2;
                    dir='R';
                    Arrays.fill(x_snake,0);
                    Arrays.fill(y_snake,0);
                    clear();
                    GameStart();
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
        repaint();
    }

}
    

