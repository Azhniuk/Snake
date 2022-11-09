import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


public class Panel extends JPanel implements ActionListener{

    //initialization of variables

    static final int S_Width=1000, S_Height=600, Game_unit_size=30; // радіус
    public ConcurrentHashMap<String, Object> map; //map for walls
    Timer timer;
    Random random;
    int foodEaten, foodX, foodY, bodylength = 2;
    boolean gameCont = false;           //continue
    static final int DELAY = 180;       //speed
    




    //!!!!!!!!!
    char dir = 'D';    
    static final int G_Size=(S_Width*S_Height)/(Game_unit_size*Game_unit_size);
    final int x_snake[]=new int[G_Size], y_snake[]=new int[G_Size];
///



    Panel(){
        this.setPreferredSize(new Dimension(S_Width,S_Height));
        this.setBackground(Color.lightGray);
        this.setFocusable(true);
        this.addKeyListener(new Keyboard());

        random = new Random();
        Game_start();
    }


    //Main constructors
    public void Game_start() {                        //start
        newFoodPosition();        
        gameCont=true;
        timer=new Timer(DELAY, this);
        timer.start();

        map = new ConcurrentHashMap();

    }

    public void paintComponent(Graphics graphic) {  //Call draw
        super.paintComponent(graphic);
        draw(graphic);
    }

    public void paintWall(Graphics graphic){

    }
   

    public void draw(Graphics graphic) {
        if(gameCont){
            //apple
            graphic.setColor(Color.red);
            graphic.fillOval(foodX, foodY,Game_unit_size,Game_unit_size);

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

            //wall
            graphic.setColor(new Color(153, 102, 0));
            graphic.fillRect(10 , 10 ,Game_unit_size,Game_unit_size);

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
        for(int i=bodylength;i>0;i--){
            x_snake[i]=x_snake[i-1];
            y_snake[i]=y_snake[i-1];
        }
        switch (dir) {
            case 'U':
            y_snake[0]=y_snake[0]-Game_unit_size;
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

    public void food_EatenOrNot() {// for checking the food has been eaten by snake or not
        if((x_snake[0]==foodX)&&(y_snake[0]==foodY)){
            bodylength++;
            foodEaten++;
            newFoodPosition();
        }
    }
    public void checkHit() {
// for checking if by mistake snake bite itself and if it collides with walls
        for (int i=bodylength;i>0;i--)
            {if((x_snake[0]==x_snake[i])&&(y_snake[0]==y_snake[i]))
                {gameCont=false;}}
        if(x_snake[0]<0)
            {gameCont=false;}
        if(x_snake[0]>S_Width)
           { gameCont=false;}
        if (y_snake[0] < 0) 
        {    gameCont = false;}
        if (y_snake[0] > S_Height) 
        {    gameCont = false;}
        if(!gameCont)
          {  timer.stop();}
    }
    public void gameOver(Graphics graphic) {// When ever game is over this function will be called.
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Courier", Font.PLAIN, 35));
        FontMetrics font_me = getFontMetrics(graphic.getFont());
        graphic.drawString("Score:" + foodEaten, (S_Width - font_me.stringWidth("Score:" + foodEaten)) / 2,
                graphic.getFont().getSize());
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Courier", Font.BOLD, 75));
        FontMetrics font_me2 = getFontMetrics(graphic.getFont());
        graphic.drawString("Game Over", (S_Width - font_me2.stringWidth("Game Over")) / 2, S_Height/2);
                graphic.setColor(Color.red);
        graphic.setFont(new Font("Courier", Font.PLAIN, 35));
        FontMetrics font_me3 = getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to Replay", (S_Width - font_me3.stringWidth("Press R to Replay")) / 2, S_Height / 2-150);
    }
    public class Keyboard extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(dir!='R'){
                        dir='L';
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
                case KeyEvent.VK_R:
                if(!gameCont){
                    foodEaten=0;
                    bodylength=2;
                    dir='R';
                   Arrays.fill(x_snake,0);
                   Arrays.fill(y_snake,0);
                    Game_start();
                }
                break;
            }            
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (gameCont) {
            move();
            food_EatenOrNot();
            checkHit();
        }
        repaint();
    }
    
}