import java.awt.*;
import java.awt.event.*;
import java.util.random.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements  ActionListener, KeyListener {
    private class Tile{
        int x;
        int y;
        Tile(int x ,int y){
            this.x=x;
            this.y=y;
        }
    }
    int boardHeight;
    int boardWidth;
    int tileSize=25;
    Random random;
    Timer loop;
    int velcoityX,velocityY;
    boolean gameOver=false;

    Tile snakeHead;
    Tile food;
    ArrayList<Tile> snakeBody;
     
     

    SnakeGame(int boardHeight, int boardWidth){
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        setPreferredSize(new Dimension(this.boardHeight,this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

         snakeHead= new Tile(5,5);
         snakeBody =new ArrayList<Tile>();


         food = new Tile(10,10);
         random =new Random();
         placeFood();

         velcoityX=0;
         velocityY=0;
         loop = new Timer(160,this);
         loop.start();
    }
    public void paint(Graphics g ){
        super.paint(g);
        draw(g);
    }
    public void draw(Graphics g){

        //for(int i=0;i<boardWidth/tileSize;i++){
            //g.drawLine(i*tileSize,0,i*tileSize,boardWidth);
           // g.drawLine(0,i*tileSize, boardWidth, i*tileSize);

        //}
        g.setColor(Color.RED);
       // g.fillRect(food.x*tileSize, food.y*tileSize,tileSize,tileSize);
        g.fill3DRect(food.x*tileSize, food.y*tileSize,tileSize,tileSize,true);


        g.setColor(Color.GREEN);
        //g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize,tileSize,tileSize);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize,tileSize,tileSize,true);


        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize,tileSize,tileSize );
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize,tileSize,tileSize ,true);

        }

        g.setFont(new Font("Arial",Font.BOLD,18));
        
        if(gameOver){
        g.setColor(Color.RED);
        g.drawString("GAME OVER",tileSize-16,tileSize);
        g.setColor(Color.GREEN);
        g.drawString("SCORE:"+String.valueOf(snakeBody.size()),tileSize-14,tileSize+25);
        }
        else{
            g.setColor(Color.GREEN);
            g.drawString("Score:"+String.valueOf(snakeBody.size()),tileSize-16,tileSize);
        }
    }
    public void placeFood() {
        food.x= random.nextInt(boardWidth/tileSize);
        food.y= random.nextInt(boardWidth/tileSize);

    }
    public boolean collision (Tile tile1,Tile tile2){
        return tile1.x==tile2.x &&tile1.y==tile2.y;
    }
    public void move(){
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x,food.y));
            placeFood();
    
        }
        for(int i=snakeBody.size()-1;i>=0;i--){
            Tile snakePart =snakeBody.get(i);
            if(i==0){
                snakePart.x=snakeHead.x;
                snakePart.y=snakeHead.y;

            }
            else {
                Tile prevPart= snakeBody.get(i-1);
                snakePart.x=prevPart.x;
                snakePart.y=prevPart.y;

            }
        }
        snakeHead.x+=velcoityX;
        snakeHead.y+=velocityY;

        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart =snakeBody.get(i);
            if(collision(snakeHead, snakePart)){
                gameOver=true;
            }
        }
        if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight ){
            gameOver=true;
          
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            loop.stop();
        }
        
    }
   
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP && velocityY!=1){
            velcoityX= 0;
            velocityY= -1;
        }
        else if (e.getKeyCode()==KeyEvent.VK_DOWN&& velocityY!=-1){
            velcoityX= 0;
            velocityY= 1;
        }
         else if (e.getKeyCode()==KeyEvent.VK_LEFT && velcoityX!=1){
            velcoityX= -1;
            velocityY= 0;
        }
        else if (e.getKeyCode()==KeyEvent.VK_RIGHT && velcoityX!= -1){
            velcoityX= 1;
            velocityY= 0;
        }
        
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    @Override
    public void keyTyped(KeyEvent e) {
       
    }
}
