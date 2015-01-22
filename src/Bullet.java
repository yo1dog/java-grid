import java.awt.Graphics;

public class Bullet
{
  private final int height = 400;
  private final int width = 400;
  private int ePower = 0;
  private final int ERADIUS = 60;
  private final int MAXEPOWER = 30;
  private final int EPOWERGROW = 1;
  private final int BULLETRADIUS = 10;
  private double x;
  private double y;
  private double xv;
  private double yv;
  private final GridControl gC;
  
  public Bullet(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, GridControl paramgridControl)
  {
    this.x = paramDouble1;
    this.y = paramDouble2;
    this.xv = paramDouble3;
    this.yv = paramDouble4;
    
    this.gC = paramgridControl;
  }
  
  public boolean run()
  {
    this.x += this.xv;
    this.y += this.yv;
    
    this.gC.createExplotion(this.x, this.y, this.ePower, 60);
    
    this.ePower += 1;
    if (this.ePower > 30) {
      this.ePower = 30;
    }
    return (this.x < -60.0D) || (this.x > 460.0D) || (this.y < -60.0D) || (this.y > 460.0D);
  }
  
  public void drawBullet(Graphics paramGraphics)
  {
    paramGraphics.fillOval((int)this.x - 10, (int)this.y - 10, 20, 20);
  }
}
