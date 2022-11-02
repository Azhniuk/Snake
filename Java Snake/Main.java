class Main {
    //To check if the game is ended
    boolean gameOver = false;

    //size of a square
    int s = 4;

    public static void main(String[] args) {
        System.out.println("Hello, World!"); 
    }

    public void endGame(){
        gameOver = true;
        background(0);

        fill(255);

        text("Gameover", 10, 20); 
      }
      

      public void reset() {
        gameOver = false;
        

        snakePositions.clear();
        
        PVector c = new PVector(width/2, height/2);
        
    
        snakePositions.add(c);
        
        randomize(objPosition, 0, width-s); 
      }
}
