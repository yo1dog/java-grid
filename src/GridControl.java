import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GridControl
{
  private final int s = 20;
  private final int w = 21;
  private final int h = 21;
  private final int n = 441;
  private final double ELASTIC = 0.2D;
  private final double FRICTION = 0.1D;
  private double[][] grid = new double[441][4];
  private boolean[] gridA = new boolean[441];
  private double[] gridT = new double[441];
  private double[][] gridV = new double[441][3];
  private int[][] gridC = new int[441][4];
  Color c1;
  Color c2;
  GradientPaint gp;
  
  public GridControl()
  {
    for (int i = 0; i < 21; i++) {
      for (int j = 0; j < 21; j++)
      {
        int k = i * 21 + j;
        
        this.grid[k][0] = (j * 20);
        this.grid[k][1] = (i * 20);
        this.grid[k][2] = (j * 20);
        this.grid[k][3] = (i * 20);
        
        this.gridA[k] = false;
        this.gridT[k] = 0.0D;
        
        this.gridV[k][0] = 0.0D;
        this.gridV[k][1] = 0.0D;
        this.gridV[k][2] = 0.0D;
      }
    }
  }
  
  public void createExplotion(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt)
  {
    int i = ((int)paramDouble1 - paramInt) / 20;
    int j = ((int)paramDouble1 + paramInt) / 20 + 1;
    int k = ((int)paramDouble2 - paramInt) / 20;
    int m = ((int)paramDouble2 + paramInt) / 20 + 1;
    if (i < 0) {
      i = 0;
    }
    if (j > 21) {
      j = 21;
    }
    if (k < 0) {
      k = 0;
    }
    if (m > 21) {
      m = 21;
    }
    for (int i1 = k; i1 < m; i1++) {
      for (int i2 = i; i2 < j; i2++)
      {
        int i3 = i2 + i1 * 21;
        

        double d1 = paramDouble1 - this.grid[i3][0];
        double d2 = paramDouble2 - this.grid[i3][1];
        

        double d3 = Math.sqrt(d1 * d1 + d2 * d2);
        if (d3 < paramInt)
        {
          double d4 = Math.atan2(d2, d1);
          

          double d5 = (paramInt - d3) / paramInt * paramDouble3;
          

          this.gridV[i3][0] -= Math.cos(d4) * d5;
          this.gridV[i3][1] -= Math.sin(d4) * d5;
          

          this.gridT[i3] = Math.atan2(this.gridV[i3][1], this.gridV[i3][0]);
          this.gridV[i3][2] = Math.sqrt(this.gridV[i3][0] * this.gridV[i3][0] + this.gridV[i3][1] * this.gridV[i3][1]);
          

          this.gridA[i3] = true;
          

          double d6 = d5 / paramDouble3; int 
            tmp331_330 = 0; int[] tmp331_329 = this.gridC[i3];tmp331_329[tmp331_330] = ((int)(tmp331_329[tmp331_330] + 255.0D * d6));
          if (this.gridC[i3][0] > 255) {
            this.gridC[i3][0] = 255;
          }
        }
      }
    }
  }
  
  public void createImplotion(int paramInt1, int paramInt2, double paramDouble, int paramInt3)
  {
    int i = (paramInt1 - paramInt3) / 20;
    int j = (paramInt1 + paramInt3) / 20 + 1;
    int k = (paramInt2 - paramInt3) / 20;
    int m = (paramInt2 + paramInt3) / 20 + 1;
    if (i < 0) {
      i = 0;
    }
    if (j > 21) {
      j = 21;
    }
    if (k < 0) {
      k = 0;
    }
    if (m > 21) {
      m = 21;
    }
    for (int i1 = k; i1 < m; i1++) {
      for (int i2 = i; i2 < j; i2++)
      {
        int i3 = i2 + i1 * 21;
        

        double d1 = paramInt1 - this.grid[i3][0];
        double d2 = paramInt2 - this.grid[i3][1];
        

        double d3 = Math.sqrt(d1 * d1 + d2 * d2);
        




        double d4 = Math.atan2(d2, d1);
        

        double d5 = (1.0D - d3 / paramInt3) * paramDouble;
        if (d5 > d3 + 0.2D * paramDouble + 0.2D * d3) {
          d5 = d3 + 0.2D * paramDouble + 0.2D * d3;
        }
        if (d5 > 0.0D)
        {
          this.gridV[i3][0] += Math.cos(d4) * d5;
          this.gridV[i3][1] += Math.sin(d4) * d5;
          

          this.gridT[i3] = Math.atan2(this.gridV[i3][1], this.gridV[i3][0]);
          this.gridV[i3][2] = Math.sqrt(this.gridV[i3][0] * this.gridV[i3][0] + this.gridV[i3][1] * this.gridV[i3][1]);
          

          this.gridA[i3] = true;
        }
      }
    }
  }
  
  public void run()
  {
    for (int i = 0; i < 441; i++) {
      if (this.gridA[i])
      {
        this.grid[i][0] += this.gridV[i][0];
        this.grid[i][1] += this.gridV[i][1];
        

        double d1 = this.grid[i][2] - this.grid[i][0];
        double d2 = this.grid[i][3] - this.grid[i][1];
        double d3 = Math.sqrt(d1 * d1 + d2 * d2);
        



        this.gridV[i][2] -= this.gridV[i][2] * 0.1D;
        if (this.gridV[i][2] < 0.0D) {
          this.gridV[i][2] = 0.0D;
        }
        this.gridV[i][0] = (Math.cos(this.gridT[i]) * this.gridV[i][2]);
        this.gridV[i][1] = (Math.sin(this.gridT[i]) * this.gridV[i][2]);
        

        double d4 = Math.atan2(d2, d1);
        



        double d5 = d3 * 0.2D;
        

        this.gridV[i][0] += Math.cos(d4) * d5;
        this.gridV[i][1] += Math.sin(d4) * d5;
        

        this.gridT[i] = Math.atan2(this.gridV[i][1], this.gridV[i][0]);
        this.gridV[i][2] = Math.sqrt(this.gridV[i][0] * this.gridV[i][0] + this.gridV[i][1] * this.gridV[i][1]);
        

        double d6 = d3 / 32.0D;
        if (d6 > 1.0D) {
          d6 = 1.0D;
        }
        this.gridC[i][3] = ((int)(255.0D * d6));
        if ((d3 < 2.0D) && (this.gridV[i][2] < 0.2D))
        {
          this.grid[i][0] = this.grid[i][2];
          this.grid[i][1] = this.grid[i][3];
          
          this.gridV[i][0] = 0.0D;
          this.gridV[i][1] = 0.0D;
          
          this.gridC[i][0] = 0;
          this.gridC[i][1] = 0;
          this.gridC[i][2] = 0;
          this.gridA[i] = false;
        }
      }
    }
  }
  
  public void drawGrid(Graphics paramGraphics)
  {
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    









    paramGraphics.setColor(Color.blue);
    int j;
    int k;
    GradientPaint localGradientPaint;
    for (int i = 1; i < 21; i++) {
      for (j = 0; j < 21; j++)
      {
        k = i + j * 21;
        


        this.c1 = new Color((int)(64 + this.gridC[k][3] * 0.75D), 0, 0);
        this.c2 = new Color((int)(64 + this.gridC[k - 1][3] * 0.75D), 0, 0);
        
        localGradientPaint = new GradientPaint((int)this.grid[k][0], (int)this.grid[k][1], this.c1, (int)this.grid[(k - 1)][0], (int)this.grid[(k - 1)][1], this.c2);
        localGraphics2D.setPaint(localGradientPaint);
        
        localGraphics2D.drawLine((int)this.grid[k][0], (int)this.grid[k][1], (int)this.grid[(k - 1)][0], (int)this.grid[(k - 1)][1]);
      }
    }
    for (int i = 1; i < 21; i++) {
      for (j = 0; j < 21; j++)
      {
        k = j + i * 21;int m = j + (i - 1) * 21;
        


        this.c1 = new Color((int)(64 + this.gridC[k][3] * 0.75D), 0, 0);
        this.c2 = new Color((int)(64 + this.gridC[m][3] * 0.75D), 0, 0);
        
        localGradientPaint = new GradientPaint((int)this.grid[k][0], (int)this.grid[k][1], this.c1, (int)this.grid[m][0], (int)this.grid[m][1], this.c2);
        localGraphics2D.setPaint(localGradientPaint);
        
        localGraphics2D.drawLine((int)this.grid[k][0], (int)this.grid[k][1], (int)this.grid[m][0], (int)this.grid[m][1]);
      }
    }
  }
}
