import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

public class Grid
  extends Applet
  implements Runnable
{
  private final int height = 400;
  private final int width = 400;
  private boolean click = false;
  private boolean rightClick = false;
  private final int MAXNUMBALLS = 20;
  private int numBalls = 0;
  private int[][] ball = new int[20][6];
  private final GridControl gC = new GridControl();
  private final Ship ship = new Ship(200, 200, 90, this.gC);
  private final Thread th = new Thread(this);
  private int mx;
  private int my;
  Graphics dbg;
  Image dbImage;
  
  public void init()
  {
    for (int i = 0; i < 20; i++)
    {
      this.ball[i][0] = ((int)(Math.random() * 400.0D));
      this.ball[i][1] = ((int)(Math.random() * 400.0D));
      
      this.ball[i][2] = (3 + (int)(Math.random() * 3.0D));
      this.ball[i][3] = (3 + (int)(Math.random() * 3.0D));
      
      this.ball[i][4] = (30 + (int)(Math.random() * 30.0D));
      this.ball[i][5] = (this.ball[i][4] / 4);
    }
  }
  
  public void start()
  {
    this.th.start();
  }
  
  public void stop()
  {
    this.th.stop();
  }
  
  public boolean mouseDrag(Event paramEvent, int paramInt1, int paramInt2)
  {
    this.mx = paramInt1;
    this.my = paramInt2;
    this.ship.mouseMove(paramInt1, paramInt2);
    
    return true;
  }
  
  public boolean mouseMove(Event paramEvent, int paramInt1, int paramInt2)
  {
    this.ship.mouseMove(paramInt1, paramInt2);
    
    return true;
  }
  
  public boolean mouseDown(Event paramEvent, int paramInt1, int paramInt2)
  {
    if (paramEvent.metaDown()) {
      this.rightClick = true;
    } else {
      this.click = true;
    }
    this.mx = paramInt1;
    this.my = paramInt2;
    
    return true;
  }
  
  public boolean mouseUp(Event paramEvent, int paramInt1, int paramInt2)
  {
    if (paramEvent.metaDown()) {
      this.rightClick = false;
    } else {
      this.click = false;
    }
    return true;
  }
  
  public boolean keyDown(Event paramEvent, int paramInt)
  {
    if (paramInt == 61) {
      if (this.numBalls < 20) {
        this.numBalls += 1;
      }
    }
    if (paramInt == 45) {
      if (this.numBalls > 0) {
        this.numBalls -= 1;
      }
    }
    this.ship.keyPress(paramInt);
    
    return true;
  }
  
  public boolean keyUp(Event paramEvent, int paramInt)
  {
    this.ship.keyRelease(paramInt);
    
    return true;
  }
  
  public void run()
  {
    Thread.currentThread().setPriority(10);
    for (;;)
    {
      this.ship.run();
      if (this.click) {
        this.gC.createExplotion(this.mx, this.my, 10.0D, 50);
      }
      if (this.rightClick) {
        this.gC.createImplotion(this.mx, this.my, 10.0D, 100);
      }
      for (int i = 0; i < this.numBalls; i++)
      {
        this.ball[i][0] += this.ball[i][2];
        this.ball[i][1] += this.ball[i][3];
        if (this.ball[i][0] < 0)
        {
          this.ball[i][0] = 0;
          this.ball[i][2] *= -1;
        }
        if (this.ball[i][0] > 400)
        {
          this.ball[i][0] = 400;
          this.ball[i][2] *= -1;
        }
        if (this.ball[i][1] < 0)
        {
          this.ball[i][1] = 0;
          this.ball[i][3] *= -1;
        }
        if (this.ball[i][1] > 400)
        {
          this.ball[i][1] = 400;
          this.ball[i][3] *= -1;
        }
        this.gC.createExplotion(this.ball[i][0], this.ball[i][1], this.ball[i][5], this.ball[i][4]);
      }
      this.gC.run();
      
      repaint();
      try
      {
        Thread.sleep(16L);
      }
      catch (InterruptedException localInterruptedException) {}
      Thread.currentThread().setPriority(10);
    }
  }
  
  public void paint(Graphics paramGraphics)
  {
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    RenderingHints localRenderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    







    this.gC.drawGrid(paramGraphics);
    
    localGraphics2D.setRenderingHints(localRenderingHints);
    
    this.ship.drawShip(paramGraphics);
    
    paramGraphics.setColor(Color.RED);
    for (int i = 0; i < this.numBalls; i++) {
      paramGraphics.fillOval(this.ball[i][0] - 8, this.ball[i][1] - 8, 16, 16);
    }
  }
  
  public void update(Graphics paramGraphics)
  {
    if (this.dbImage == null)
    {
      this.dbImage = createImage(getSize().width, getSize().height);
      this.dbg = this.dbImage.getGraphics();
    }
    this.dbg.setColor(Color.black);
    this.dbg.fillRect(0, 0, 400, 400);
    
    this.dbg.setColor(getForeground());
    paint(this.dbg);
    
    paramGraphics.drawImage(this.dbImage, 0, 0, this);
  }
}
