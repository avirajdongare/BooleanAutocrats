import java.awt.*;
import java.awt.event.*;
import java.applet.*;

class Brick {
	public int x,y;
	
	public Brick(int x,int y) {
		this.x = x;
		this.y = y;
	}	
}
	
class Ball {
	public int x,y,slope;
	public boolean nw,sw,ne,se;
	
	public Ball(int x,int y) {
		ne = true;
		this.x = x;
		this.y = y;
	}	
}
	

public class Pong extends Applet implements Runnable,MouseMotionListener,KeyListener {
	private int gameflag;
	private boolean player1 = true;
	private final int INIT_ACTIVE = 0;
	private final int INTRO_ACTIVE = 1;
	private final int GAME1_ACTIVE = 2;
	private final int GAME2_ACTIVE = 3;
	private int padx,pad1x,score;
	private boolean gameover;
	private Thread t;
	private Image dbuffer,back,back1,ballpic1,ballpic2,pad,pad1,brickpic,gameoverpic,logo,title;
	private Graphics dbuffer_gfx;
	private Brick brick[] = new Brick[5];
	private Ball ball[] = new Ball[3];
	private Ball ball2;
	private Ball logoxy;
		
	public void init() {
		requestFocus();		
		gameoverpic = getImage(getDocumentBase(),"gameover.gif");
		title = getImage(getDocumentBase(),"title.gif");
		logo = getImage(getDocumentBase(),"logo.gif");
		brickpic = getImage(getDocumentBase(),"brick.gif");
		pad = getImage(getDocumentBase(),"pad.gif");
		pad1 = getImage(getDocumentBase(),"pad1.gif");
		ballpic1 = getImage(getDocumentBase(),"ball.gif");
		ballpic2 = getImage(getDocumentBase(),"ball1.gif");
		back = getImage(getDocumentBase(),"back.jpg");
		back1 = getImage(getDocumentBase(),"back1.jpg");
		dbuffer=createImage(getSize().width,getSize().height);
		dbuffer_gfx=dbuffer.getGraphics();
		
		gameflag = INIT_ACTIVE;
		logoxy = new Ball(((int)(Math.random() * 4) * (getSize().width/4)),
                          ((int)(Math.random() * 4) * (getSize().height/4)));
		addMouseMotionListener(this);
		addKeyListener(this);
    }
	
	public void initgame1() {
		score = 0;
		
		for(int j = 0; j < ball.length ; j++) {
			ball[j] = new Ball(
							  (int)(Math.random() * getSize().width),
							  (int)(Math.random() * getSize().height)
							  );
		}	
		for(int i = 0; i < brick.length ; i++) { 
		 brick[i] = new Brick(
			                 ((int)(Math.random() * 5) * (getSize().width/5)),
                             ((int)(Math.random() * 5) * (getSize().height/5))
							 );
		} 
	}	
    
	public void initgame2() {
	    ball2 = new Ball((int)(Math.random() * getSize().width),getSize().height/2);
		pad1x = getSize().width / 2 - 25;
		padx = getSize().width / 2;
	}
	
	public void start() {
    	t = new Thread(this);
    	t.start();
    }
    
    public void stop() {
    	t.stop();
    	t = null;
    }		
	
	public void update(Graphics g) {
	 paint(dbuffer_gfx);
	 g.drawImage(dbuffer,0,0,this);
	}
	
	public void PaintIntro(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0,0,getSize().width,getSize().height);
		g.drawImage(logo,logoxy.x,logoxy.y,this);
		g.drawImage(title,getSize().width/2 - 116,getSize().height/7,this);
		if (player1 == true) {
		 g.setColor(Color.blue);
		 g.drawString("1 Player Game",getSize().width/2 - 45,getSize().height/2 + getSize().height/3);
		 g.setColor(Color.white);
		 g.drawString("2 Player Game",getSize().width/2 - 45,getSize().height/2 + 15 + getSize().height/3);
		} else { 
		 g.setColor(Color.white);
		 g.drawString("1 Player Game",getSize().width/2 - 45,getSize().height/2 + getSize().height/3);
		 g.setColor(Color.blue);
		 g.drawString("2 Player Game",getSize().width/2 - 45,getSize().height/2 + 15 + getSize().height/3);	
		}
		g.setColor(Color.red);
		g.drawString("written by Dino Scarcella",getSize().width/2 - 90,getSize().height/7 + 110);
	}
	
	public void PaintGame1(Graphics g) {
		if (((score/10)%2)== 0) {
		   g.drawImage(back,0,0,this);
		}   
		if (((score/10)%2)== 1) {
		   g.drawImage(back1,0,0,this);	   
		}	
		for(int i = 0;i < brick.length;i++) {
			  g.drawImage(brickpic,brick[i].x,brick[i].y,this);
		}
		for(int i = 0;i < ball.length; i++) {
			if ((i % 2)==0) {
			  g.drawImage(ballpic1,ball[i].x,ball[i].y,this);
			}else{
			  g.drawImage(ballpic2,ball[i].x,ball[i].y,this);	
			}  
		}	
		
		g.drawImage(pad,padx - 25,getSize().height - 20,this);
		if (gameover == true) {
			g.setColor(Color.black);
			g.drawImage(gameoverpic,getSize().width / 2 - 48
						 ,getSize().height / 2 - 20,this);
			g.drawString("SCORE : " + score,getSize().width / 2 - 40
						 ,getSize().height);
		}	
	}
	
	public void PaintGame2(Graphics g) {
	 g.drawImage(back,0,0,this);	
	 g.drawImage(ballpic1,ball2.x,ball2.y,this);
	 g.drawImage(pad,padx - 25,getSize().height - 20,this);
	 g.drawImage(pad1,pad1x,0,this);
	 if (gameover == true) {
			g.setColor(Color.black);
			g.drawImage(gameoverpic,getSize().width / 2 - 48
						 ,getSize().height / 2 - 20,this);
			if (ball2.y == -7) g.drawString("Player 1 wins",getSize().width / 2 - 40 ,getSize().height); 
			if (ball2.y == getSize().height - 19) g.drawString("Player 2 wins",getSize().width / 2 - 40 ,getSize().height); 
	 }	
	}	

    public void paint(Graphics g) {
     g.setFont(new Font("Arial",Font.BOLD,14));		
	 if (gameflag == INIT_ACTIVE) {
		 
		 g.setColor(Color.black);
		 g.fillRect(0,0,getSize().width,getSize().height);
		 g.setColor(Color.white);
		 g.drawString("Loading, please wait...",20,20);
	
	 }	 
		 
	 if (gameflag == INTRO_ACTIVE) {	 
	  PaintIntro(g);
	 }	
	 
	 if (gameflag == GAME1_ACTIVE) {	 
	  PaintGame1(g);
	 }
	 
	 if (gameflag == GAME2_ACTIVE) {
	  PaintGame2(g);
	 } 
	}
	
   public void physicsgame1() {
	for(int j = 0;j < ball.length;j++) {	
nw:	 if (ball[j].nw == true) {
			if (ball[j].x <= 0) {
				ball[j].nw = false;
				ball[j].ne = true;
				if (ball[j].y <= getSize().height - 29) play(getDocumentBase(),"hitwall.au");
				break nw;
			}	
			if (ball[j].y <= 0) {
				ball[j].sw = true;
				ball[j].nw = false;
				if (ball[j].y <= getSize().height - 29) play(getDocumentBase(),"hitwall.au");
				break nw;
			}
forloopNW:	for(int i = 0; i < brick.length ; i++) {
			   if ((ball[j].x >= brick[i].x)&&(ball[j].x <= brick[i].x + 60)&&(ball[j].y >= brick[i].y)&&(ball[j].y <= brick[i].y + 10)) {
				   if ((ball[j].x <= brick[i].x + 60)&&(ball[j].x >= brick[i].x + 55)) { 
				    ball[j].nw = false;
				    ball[j].ne = true;
					play(getDocumentBase(),"hitwall.au");
					break forloopNW;
				   }
				   if ((ball[j].y <= brick[i].y + 10)&&(ball[j].y >= brick[i].y + 5)) {
					ball[j].nw = false;
					ball[j].sw = true;
					play(getDocumentBase(),"hitwall.au");
				   }	   
			   }	
			}	
		
		  ball[j].x-= ball[j].slope + 2 + score/10;
		  ball[j].y-= 2 + score/10;
	 }	
ne:	 if (ball[j].ne == true) {
			if (ball[j].x >= getSize().width - 9) {
				ball[j].nw = true ;
				ball[j].ne = false ;
				if (ball[j].y <= getSize().height - 29) play(getDocumentBase(),"hitwall.au");
				break ne;
			}	
			if (ball[j].y <= 0) {
				ball[j].se = true;
				ball[j].ne = false;
				if (ball[j].y <= getSize().height - 29) play(getDocumentBase(),"hitwall.au");
				break ne;
			}	
			
forloopNE:	for(int i = 0; i < brick.length ; i++) {
			   if ((ball[j].x >= brick[i].x - 9)&&(ball[j].x <= brick[i].x + 60)&&(ball[j].y >= brick[i].y)&&(ball[j].y <= brick[i].y + 10)) {
				   if ((ball[j].x >= brick[i].x - 9)&&(ball[j].x <= brick[i].x - 4)) { 
				    ball[j].ne = false;
				    ball[j].nw = true;
					play(getDocumentBase(),"hitwall.au");
					break forloopNE;
				   }
				   if ((ball[j].y <= brick[i].y + 10)&&(ball[j].y >= brick[i].y + 5)) {
					ball[j].ne = false;
					ball[j].se = true;
					play(getDocumentBase(),"hitwall.au");
				   }	   
			   }	
			}	
				
		  ball[j].x += ball[j].slope + 2 + score/10;
		  ball[j].y -= 2 + score/10;
	 }	
sw:	 if (ball[j].sw == true) {
			if (ball[j].x <= 0) {
				ball[j].sw = false;
				ball[j].se = true;
				if (ball[j].y <= getSize().height - 29) play(getDocumentBase(),"hitwall.au");
				break sw;
			}	
			if ((ball[j].y >= getSize().height - 29)&&(ball[j].y <= getSize().height - 19)) {
				if ((ball[j].x >= padx - 29)&&(ball[j].x <= padx - 19)) {
				 ball[j].sw = false;
				 ball[j].nw = true;
				 score ++;
				 ball[j].slope = 3;
				 play(getDocumentBase(),"hitpad.au");
				 break sw;
				} 
				if ((ball[j].x > padx - 19)&&(ball[j].x <= padx - 9)) {
				 ball[j].sw = false;
				 ball[j].nw = true;
				 score ++;
				 ball[j].slope = 1;
				 play(getDocumentBase(),"hitpad.au");
				 break sw;
				} 
				if ((ball[j].x > padx - 9)&&(ball[j].x <= padx + 1)) {
				 ball[j].sw = false;
				 ball[j].nw = true;
				 score ++;
				 ball[j].slope = 0;
				 play(getDocumentBase(),"hitpad.au");
				 break sw;
				} 
				if ((ball[j].x > padx + 1)&&(ball[j].x <= padx + 11)) {
				 ball[j].sw = false;
				 ball[j].nw = true;
				 score ++;
				 ball[j].slope = 1;
				 play(getDocumentBase(),"hitpad.au");
				 break sw;
				} 
				if ((ball[j].x > padx + 11)&&(ball[j].x <= padx + 21)) {
				 ball[j].sw = false;
				 ball[j].ne = true;
				 score ++;
				 ball[j].slope = 3;
				 play(getDocumentBase(),"hitpad.au");
				 break sw;
				} 
			}	
forloopSW:	for(int i = 0; i < brick.length ; i++) {
			   if ((ball[j].x >= brick[i].x)&&(ball[j].x <= brick[i].x + 60)&&(ball[j].y >= brick[i].y - 9)&&(ball[j].y <= brick[i].y + 10)) {
				   if ((ball[j].x <= brick[i].x + 60)&&(ball[j].x >= brick[i].x + 55)) { 
				    ball[j].sw = false;
				    ball[j].se = true;
					play(getDocumentBase(),"hitwall.au");
					break forloopSW;
				   }
				   if ((ball[j].y >= brick[i].y - 9 )&&(ball[j].y <= brick[i].y - 4)) {
					ball[j].sw = false;
					ball[j].nw = true;
					play(getDocumentBase(),"hitwall.au");
				   }	   
			   }	
			}	
				
		  ball[j].x-= ball[j].slope + 2 + score/10;
		  ball[j].y+= 2 + score/10;
	 }	
se:	 if (ball[j].se == true) {
			if (ball[j].x >= getSize().width - 9) {
				ball[j].sw = true;
				ball[j].se = false ;
				if (ball[j].y <= getSize().height - 29) play(getDocumentBase(),"hitwall.au");
				break se;
			}	
			if ((ball[j].y >= getSize().height - 29)&&(ball[j].y <= getSize().height - 19)) {
				if ((ball[j].x >= padx - 29)&&(ball[j].x <= padx - 19)) {
				 ball[j].se = false;
				 ball[j].nw = true;
				 score ++;
				 ball[j].slope = 3;
				 play(getDocumentBase(),"hitpad.au");
				 break se;
				} 
				if ((ball[j].x > padx - 19)&&(ball[j].x <= padx - 9)) {
				 ball[j].se = false;
				 ball[j].ne = true;
				 score ++;
				 ball[j].slope = 1;
				 play(getDocumentBase(),"hitpad.au");
				 break se;
				} 
				if ((ball[j].x > padx - 9)&&(ball[j].x <= padx + 1)) {
				 ball[j].se = false;
				 ball[j].ne = true;
				 score ++;
				 ball[j].slope = 0;
				 play(getDocumentBase(),"hitpad.au");
				 break se;
				} 
				if ((ball[j].x > padx + 1)&&(ball[j].x <= padx + 11)) {
				 ball[j].se = false;
				 ball[j].ne = true;
				 score ++;
				 ball[j].slope = 1;
				 play(getDocumentBase(),"hitpad.au");
				 break se;
				} 
				if ((ball[j].x > padx + 11)&&(ball[j].x <= padx + 21)) {
				 ball[j].se = false;
				 ball[j].ne = true;
				 score ++;
				 ball[j].slope = 3;
				 play(getDocumentBase(),"hitpad.au");
				 break se;
				}  
			}
forloopSE :	for(int i = 0; i < brick.length ; i++) {
			   if ((ball[j].x >= brick[i].x - 9)&&(ball[j].x <= brick[i].x + 60)&&(ball[j].y >= brick[i].y - 9)&&(ball[j].y <= brick[i].y + 10)) {
				   if ((ball[j].x >= brick[i].x - 9)&&(ball[j].x <= brick[i].x - 4)) { 
				    ball[j].se = false;
				    ball[j].sw = true;
					play(getDocumentBase(),"hitwall.au");
					break forloopSE;
				   }
				   if ((ball[j].y >= brick[i].y - 9)&&(ball[j].y <= brick[i].y - 4)) {
					ball[j].se = false;
					ball[j].ne = true;
					play(getDocumentBase(),"hitwall.au");
				   }	   
			   }	
			}	
			
		  ball[j].x+= ball[j].slope + 2 + score/10;
		  ball[j].y+= 2 + score/10;
	 }
	}
   }
   
   public void physicsgame2() {
ne:    if (ball2.ne == true) {
		   if (ball2.x >= getSize().width - 9) {
			   ball2.ne = false;
			   ball2.nw = true;
			   play(getDocumentBase(),"hitwall.au");
			   break ne;
		   }
		   if ((ball2.y <= 10)&&(ball2.y >= 0)) {
			    if ((ball2.x >= pad1x - 4)&&(ball2.x <= pad1x + 6)) {
				 ball2.ne = false ;
				 ball2.sw = true ;
				 ball2.slope = 3;
				 play(getDocumentBase(),"hitpad.au");
				 break ne;
				}	
				if ((ball2.x > pad1x + 6)&&(ball2.x <= pad1x + 16)) {
				 ball2.ne = false ;
				 ball2.se = true ;
				 ball2.slope = 1;
				 play(getDocumentBase(),"hitpad.au");
				 break ne;
				}	
				if ((ball2.x > pad1x + 16)&&(ball2.x <= pad1x + 26)) {
				 ball2.ne = false ;
				 ball2.se = true ;
				 ball2.slope = 0;
				 play(getDocumentBase(),"hitpad.au");
				 break ne;
				}	
				if ((ball2.x > pad1x + 26)&&(ball2.x <= pad1x + 36)) {
				 ball2.ne = false ;
				 ball2.se = true ;
				 ball2.slope = 1;
				 play(getDocumentBase(),"hitpad.au");
				 break ne;
				}	
				if ((ball2.x > pad1x + 36)&&(ball2.x <= pad1x + 46)) {
				 ball2.ne = false ;
				 ball2.se = true ;
				 ball2.slope = 3;
				 play(getDocumentBase(),"hitpad.au");
				 break ne;
				}	
		   }	   
		   ball2.x += 2 + ball2.slope;
		   ball2.y -= 2;
	   }
nw:	   if (ball2.nw == true) {
		   if (ball2.x <= 0) {
			   ball2.nw = false;
			   ball2.ne = true;
			   play(getDocumentBase(),"hitwall.au");
			   break nw;
		   }
		   if ((ball2.y <= 10)&&(ball2.y >= 0)) {
			    if ((ball2.x >= pad1x - 4)&&(ball2.x <= pad1x + 6)) {
				 ball2.nw = false ;
				 ball2.sw = true ;
				 ball2.slope = 3;
				 play(getDocumentBase(),"hitpad.au");
				 break nw;
				}	
				if ((ball2.x > pad1x + 6)&&(ball2.x <= pad1x + 16)) {
				 ball2.nw = false ;
				 ball2.sw = true ;
				 ball2.slope = 1;
				 play(getDocumentBase(),"hitpad.au");
				 break nw;
				}	
				if ((ball2.x > pad1x + 16)&&(ball2.x <= pad1x + 26)) {
				 ball2.nw = false ;
				 ball2.sw = true ;
				 ball2.slope = 0;
				 play(getDocumentBase(),"hitpad.au");
				 break nw;
				}	
				if ((ball2.x > pad1x + 26)&&(ball2.x <= pad1x + 36)) {
				 ball2.nw = false ;
				 ball2.sw = true ;
				 ball2.slope = 1;
				 play(getDocumentBase(),"hitpad.au");
				 break nw;
				}	
				if ((ball2.x > pad1x + 36)&&(ball2.x <= pad1x + 46)) {
				 ball2.nw = false ;
				 ball2.se = true ;
				 ball2.slope = 3;
				 play(getDocumentBase(),"hitpad.au");
				 break nw;
				}	
		   }	   
		   ball2.x -= 2 + ball2.slope;
		   ball2.y -= 2;
	   }
se:	   if (ball2.se == true) {
		   if (ball2.x >= getSize().width - 9) {
			   ball2.se = false;
			   ball2.sw = true;
			   play(getDocumentBase(),"hitwall.au");
			   break se;
		   }
		   if ((ball2.y >= getSize().height - 29)&&(ball2.y <= getSize().height - 19)) {
				if ((ball2.x >= padx - 29)&&(ball2.x <= padx - 19)) {
				 ball2.se = false;
				 ball2.nw = true;
				 ball2.slope = 3;
				 play(getDocumentBase(),"hitpad.au");
				 break se;
				} 
				if ((ball2.x > padx - 19)&&(ball2.x <= padx - 9)) {
				 ball2.se = false;
				 ball2.ne = true;
				 ball2.slope = 1;
				 play(getDocumentBase(),"hitpad.au");
				 break se;
				} 
				if ((ball2.x > padx - 9)&&(ball2.x <= padx + 1)) {
				 ball2.se = false;
				 ball2.ne = true;
				 ball2.slope = 0;
				 play(getDocumentBase(),"hitpad.au");
				 break se;
				} 
				if ((ball2.x > padx + 1)&&(ball2.x <= padx + 11)) {
				 ball2.se = false;
				 ball2.ne = true;
				 ball2.slope = 1;
				 play(getDocumentBase(),"hitpad.au");
				 break se;
				} 
				if ((ball2.x > padx + 11)&&(ball2.x <= padx + 21)) {
				 ball2.se = false;
				 ball2.ne = true;
				 ball2.slope = 3;
				 play(getDocumentBase(),"hitpad.au");
				 break se;
				}  
			}
		   ball2.x += 2 + ball2.slope;
		   ball2.y += 2;
	   }
sw:	   if (ball2.sw == true) {
		   if (ball2.x <= 0) {
			   ball2.sw = false;
			   ball2.se = true;
			   play(getDocumentBase(),"hitwall.au");
			   break sw;
		   }
		   if ((ball2.y >= getSize().height - 29)&&(ball2.y <= getSize().height - 19)) {
				if ((ball2.x >= padx - 29)&&(ball2.x <= padx - 19)) {
				 ball2.sw = false;
				 ball2.nw = true;
				 ball2.slope = 3;
				 play(getDocumentBase(),"hitpad.au");
				 break sw;
				} 
				if ((ball2.x > padx - 19)&&(ball2.x <= padx - 9)) {
				 ball2.sw = false;
				 ball2.nw = true;
				 ball2.slope = 1;
				 play(getDocumentBase(),"hitpad.au");
				 break sw;
				} 
				if ((ball2.x > padx - 9)&&(ball2.x <= padx + 1)) {
				 ball2.sw = false;
				 ball2.nw = true;
				 ball2.slope = 0;
				 play(getDocumentBase(),"hitpad.au");
				 break sw;
				} 
				if ((ball2.x > padx + 1)&&(ball2.x <= padx + 11)) {
				 ball2.sw = false;
				 ball2.nw = true;
				 ball2.slope = 1;
				 play(getDocumentBase(),"hitpad.au");
				 break sw;
				} 
				if ((ball2.x > padx + 11)&&(ball2.x <= padx + 21)) {
				 ball2.sw = false;
				 ball2.ne = true;
				 ball2.slope = 3;
				 play(getDocumentBase(),"hitpad.au");
				 break sw;
				} 
			}	
		   ball2.x -= 2 + ball2.slope;
		   ball2.y += 2;
	   }
   }	   
	
   public void physicsintro() {
	   if (logoxy.nw == true) {
		   if (logoxy.x <= 0) {
			   logoxy.nw = false;
			   logoxy.ne = true;
		   }
		   if (logoxy.y <= 0) {
			   logoxy.nw = false;
			   logoxy.sw = true;
		   }
		   logoxy.x -= 2;
		   logoxy.y -= 2;
	   } 	
	   if (logoxy.ne == true) {
		   if (logoxy.x >= getSize().width - 106) {
			   logoxy.ne = false;
			   logoxy.nw = true;
		   }
		   if (logoxy.y <= 0) {
			   logoxy.ne = false;
			   logoxy.se = true;
		   }
		   logoxy.x += 2;
		   logoxy.y -= 2;
	   } 
	   if (logoxy.sw == true) {
		   if (logoxy.x <= 0) {
			   logoxy.sw = false;
			   logoxy.se = true;
		   }
		   if (logoxy.y >= getSize().height - 95) {
			   logoxy.sw = false;
			   logoxy.nw = true;
		   }
		   logoxy.x -= 2;
		   logoxy.y += 2;
	   } 	
	   if (logoxy.se == true) {
		   if (logoxy.x >= getSize().width - 106) {
			   logoxy.se = false;
			   logoxy.sw = true;
		   }
		   if (logoxy.y >= getSize().height - 95) {
			   logoxy.se = false;
			   logoxy.ne = true;
		   }
		   logoxy.x += 2;
		   logoxy.y += 2;
	   } 	
   }
   
	public void run() {
	 while (true) {	
		switch (gameflag) {
		 
		 case INIT_ACTIVE : {
		   repaint();
		   gameflag = INTRO_ACTIVE;
		   try {
			 Thread.sleep(10);
		   } catch (Exception e) {
			 e.printStackTrace();
		   }  
		   break;
		 } 
         
		 case INTRO_ACTIVE : {									 								 					 
		   try {
			 Thread.sleep(10);
		   } catch (Exception e) {
			 e.printStackTrace();
		   }  						 
		   physicsintro();
		   repaint();	
		   break;
		 } 
		 
		 
		 case GAME1_ACTIVE : {
          initgame1();								 
		  while ((ball[0].y < getSize().height - 19)||(ball[1].y < getSize().height - 19)||(ball[2].y < getSize().height - 19)) {
			try {
			 Thread.sleep(10);
			} catch (Exception e) {
			 e.printStackTrace();
			}  
			physicsgame1();
			repaint();
		  }
		  gameover = true;
	      play(getDocumentBase(),"gong.au");
		  try {
			 Thread.sleep(3500);
		  } catch (Exception e) {
			 e.printStackTrace();
		  }  
		  gameover = false;
		  gameflag = INTRO_ACTIVE;
		  player1 = true;
		  break;
		 } 
							 
       	 case GAME2_ACTIVE : {
		  initgame2();		
		  try {
		   Thread.sleep(2000);
		  } catch (Exception e) {
		   e.printStackTrace();
		  }  
		  while ((ball2.y > -7)&&(ball2.y < getSize().height - 19)) {
			try {
			 Thread.sleep(10);
			} catch (Exception e) {
			 e.printStackTrace();
			}  
			physicsgame2();
			repaint();
		  }
		  gameover = true;
	      play(getDocumentBase(),"gong.au");
		  try {
			 Thread.sleep(3500);
		  } catch (Exception e) {
			 e.printStackTrace();
		  }  
		  gameflag = INTRO_ACTIVE;
		  player1 = true;
		  gameover = false;
		  break;
		 } 						 
        							 
		}		
	 }	
	}	
	
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {
		padx = e.getX();
		repaint();
	}
	
	public void keyPressed(KeyEvent e) { 	
		if (gameflag == INTRO_ACTIVE) {
		 if (e.getKeyCode() == 9) {  //tab
			 if (player1 == true) 
				 player1 = false;
			 else player1 = true;
		 }
		 if (e.getKeyCode() == 32) { //space
			 if (player1 == true) 
				 gameflag = GAME1_ACTIVE;
			 else gameflag = GAME2_ACTIVE;	  
		 }
	    }
		if (gameflag == GAME2_ACTIVE) {
			if ((e.getKeyChar() == 115)||(e.getKeyChar() == 83)) {
			   if (pad1x < getSize().width - 10) pad1x += 30;
			}	
			if ((e.getKeyChar() == 97)||(e.getKeyChar() == 65)) {
			   if (pad1x > 0) pad1x -= 30;
			}	
		}	
		repaint();
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
    }
}