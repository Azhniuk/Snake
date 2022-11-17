 public class Keyboard extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                   // if(dir !='R'){
                        dir='L';
                   // }
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
