/*
<applet code="SimpleSideScrollerIncomplete.java" width="640" height="320">
</applet>

*/

import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class Scroller extends Applet implements Runnable, KeyListener{

	int i = -32;

	MediaTracker tracker;	
	
	Color backgroundColor = new Color(255,255,255);

	int width, height;

	
	int mleft, mtop, mright, mbottom;
	int floor;


	int TileWidth,TileHeight;
	int NumberX, NumberY;
	
	
	// Key flags.


	boolean flip = true;
  	boolean left  = false;
  	boolean right = false;
 	boolean up    = false;
  	boolean down  = false;

	boolean rightlast = false;

	Image backbuffer, background, mario;
   	Graphics backg;

	public void init(){

		tracker = new MediaTracker(this);
	
		
		mario = getImage(getCodeBase(),"mario.png");

		background = getImage(getCodeBase(), "clear_brick_32x32.png");
		
		tracker.addImage(mario, 0);
		tracker.addImage(background, 0);
		try{

			tracker.waitForID(0);
		}catch(InterruptedException ie){}		


		addKeyListener(this);

		width = getWidth();
		height = getHeight();


		mleft = width/2;

		mtop = height-64;

		mright =  width/2+20;

		mbottom =  height-32;
		floor = mbottom;

		TileWidth = background.getWidth(this);

		TileHeight = background.getHeight(this);


		NumberX = width/TileWidth + 2;

		NumberY = height/TileHeight;

				

		backbuffer = createImage(width, height);
     		backg = backbuffer.getGraphics();

		
      		
		Thread t = new Thread(this);
		t.start();
		
	}

	public void paintBackBuffer(){

		backg.setColor(backgroundColor);
		backg.fillRect(0,0,width, height);
		backg.setColor(Color.black);
				

		for(int h = 0; h <= NumberY /*-4*/; h++){

			for(int j = 0; j <= NumberX; j++){


				backg.drawImage(background, i+j*TileWidth, 
						h*TileHeight, this);
			}
	
	}

		
		backg.fillRect(0,height-32,width, 32);

		if(up){
	
			if(mbottom == floor){
				mtop = mtop -100;

				mbottom -= 100;
			}
		}

		if(mbottom < floor){

			mtop+=2;
			mbottom+=2;
		}



		if(right){  
		
			i-=8;
			i = i%32;
			if(i==0) i = -32;
		
			if(flip){
	 			backg.drawImage(mario, mleft, mtop, 
					mright, mbottom, 105, 18, 90,50,this);

				flip = false;

			}else{


				backg.drawImage (mario, mleft, mtop, 
					mright, mbottom, 135, 18, 120,50,this);
	

			flip = true;

			}
		

		}else if(left){
	
			i+=8;
			i = i%32;
			if(i==0) i = -32;
		
			if(flip){
				backg.drawImage(mario, mleft, mtop, 
					mright, mbottom, 90, 18, 105, 50, this);
	
			flip = false;
			}else{


				backg.drawImage(mario, mleft, mtop, 
					mright, mbottom, 120, 18, 135, 50, this);

				flip = true;

			}
	


		}else{	
			backg.drawImage(mario, mleft, mtop, mright, 
				mbottom, 30,18 , 45, 50, this);

		}	

		repaint();
	}

	public void update(Graphics g){

		g.drawImage(backbuffer,0,0,this);
	}

	public void paint(Graphics g){

		update(g);

	}
	
	public void keyPressed(KeyEvent e) {

	    	// Check if any cursor keys have been pressed and set flags.

		if (e.getKeyCode() == KeyEvent.VK_LEFT)
      			left = true;
    		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
      			right = true;
    		if (e.getKeyCode() == KeyEvent.VK_UP)
      			up = true;
    		if (e.getKeyCode() == KeyEvent.VK_DOWN)
      			down = true;
        
  		}

  	public void keyReleased(KeyEvent e) {

    		// Check if any cursor keys where released and set flags.

    	if (e.getKeyCode() == KeyEvent.VK_LEFT){
      		left = false;
		rightlast = false;
	}
    	if (e.getKeyCode() == KeyEvent.VK_RIGHT){
      		right = false;
		rightlast = true;
	}
    	if (e.getKeyCode() == KeyEvent.VK_UP)
      		up = false;
    	if (e.getKeyCode() == KeyEvent.VK_DOWN)
      		down = false;

  	}

  	public void keyTyped(KeyEvent e) {}


	public void run(){

		while(true){
	
			paintBackBuffer();

			try{
				Thread.sleep(50);

			}catch(Exception e){}		
		}

	}
	
}