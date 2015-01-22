import java.awt.Color;
import java.awt.Graphics;

public class Ship
{
  private final int height = 400;
  private final int width = 400;
  private boolean zPressed = false;
  private boolean xPressed = false;
  private final int ERADIUS = 50;
  private final int EPOWER = 20;
  private final int SHIPRADIUS = 10;
  private final int SHIPSPEED = 2;
  private final int FIRERADIUS = 20;
  private final int FIRESPEED = 12;
  private final int FIRERATE = 10;
  private int numBullets = 0;
  private final int MAXNUMBULLETS = 20;
  private int fireDelay = 0;
  private Bullet[] bullet = new Bullet[20];
  private double x;
  private double y;
  private double t;
  private int v;
  private GridControl gC;
  
  public Ship(int paramInt1, int paramInt2, int paramInt3, GridControl paramgridControl)
  {
    this.x = paramInt1;
    this.y = paramInt2;
    this.t = (paramInt3 / 180.0D * 3.141592653589793D);
    
    this.v = 0;
    
    this.gC = paramgridControl;
  }
  
  public void keyPress(int paramInt)
  {
    if (paramInt == 1004) {
      this.v += 1;
    } else if (paramInt == 1005) {
      this.v -= 1;
    } else if (paramInt == 1006) {
      this.v = 0;
    }
    if (paramInt == 122)
    {
      this.zPressed = true;
      this.xPressed = false;
    }
    else if (paramInt == 120)
    {
      this.xPressed = true;
      this.zPressed = false;
    }
  }
  
  public void keyRelease(int paramInt)
  {
    if (paramInt == 122) {
      this.zPressed = false;
    } else if (paramInt == 120) {
      this.xPressed = false;
    }
  }
  
  public void mouseMove(int paramInt1, int paramInt2)
  {
    this.t = Math.atan2(paramInt2 - this.y, paramInt1 - this.x);
  }
  
  private void createBullet(double paramDouble)
  {
    if (this.numBullets < 20)
    {
      this.bullet[this.numBullets] = new Bullet(this.x + Math.cos(paramDouble) * 20.0D, this.y + Math.sin(paramDouble) * 20.0D, Math.cos(paramDouble) * 12.0D * (this.v + 1), Math.sin(paramDouble) * 12.0D * (this.v + 1), this.gC);
      



      this.numBullets += 1;
    }
  }
  
  private void killBullet(int paramInt)
  {
    this.bullet[paramInt] = null;
    for (int i = paramInt; i < this.numBullets - 1; i++) {
      this.bullet[i] = this.bullet[(i + 1)];
    }
    this.numBullets -= 1;
  }
  
  public void run()
  {
    this.x += Math.cos(this.t) * this.v * 2.0D;
    this.y += Math.sin(this.t) * this.v * 2.0D;
    if (this.x - 10.0D < 0.0D) {
      this.x = 10.0D;
    }
    if (this.x + 10.0D > 400.0D) {
      this.x = 390.0D;
    }
    if (this.y - 10.0D < 0.0D) {
      this.y = 10.0D;
    }
    if (this.y + 10.0D > 400.0D) {
      this.y = 390.0D;
    }
    this.gC.createExplotion(this.x, this.y, 20.0D, 50);
    if (this.zPressed)
    {
      if (this.fireDelay == 0)
      {
        createBullet(this.t);
        
        this.fireDelay = 10;
      }
    }
    else if (this.xPressed) {
      if (this.fireDelay < 1)
      {
        createBullet(this.t - 0.1D);
        createBullet(this.t);
        createBullet(this.t + 0.1D);
        
        this.fireDelay = 10;
      }
    }
    if (this.fireDelay > 0) {
      this.fireDelay -= 1;
    }
    for (int i = 0; i < this.numBullets; i++) {
      if (this.bullet[i].run())
      {
        killBullet(i);
        i--;
      }
    }
  }
  
  public void drawShip(Graphics paramGraphics)
  {
    paramGraphics.setColor(Color.WHITE);
    paramGraphics.fillOval((int)this.x - 10, (int)this.y - 10, 20, 20);
    
    paramGraphics.setColor(Color.RED);
    paramGraphics.drawLine((int)this.x, (int)this.y, (int)(this.x + Math.cos(this.t) * 10.0D), (int)(this.y + Math.sin(this.t) * 10.0D));
    
    paramGraphics.setColor(Color.YELLOW);
    for (int i = 0; i < this.numBullets; i++) {
      this.bullet[i].drawBullet(paramGraphics);
    }
  }
}
