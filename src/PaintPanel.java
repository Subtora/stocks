import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PaintPanel extends JPanel implements ActionListener {

    private int bottom = 564;
    private point leadPoint = new point(-2,bottom/2);
    private point trailPoint = new point(-2,bottom/2);
    private point costPos = new point(20, 30);
    private point balPos = new point(700,30);

    private double cost = 0;
    private double bal = 20.0;
    private double coinNum = 0;
    private int resetNum = 0;

    //for getRandInt
    private int max = 30;
    private int min = 10;



    public PaintPanel() {
        setSize(800,600);
        //tick/second
        Timer clock = new Timer(100,this);
        clock.start();
    }

    public void buy() {

        if(bal != 0){
            coinNum += Math.round((bal/cost)*100.0)/100.0;
            System.out.println("buying: "+coinNum+" XRP for "+bal);
            bal = 0;
        }
    }

    public void sell() {

        if(bal == 0){
            bal += Math.round((coinNum * cost)*100.0)/100.0;
            System.out.println("Sold: "+coinNum+" for "+ bal);
            coinNum = 0;
        }
    }

    public void paintComponent(Graphics g){
        //paint occurs when the method repaint() is called (see actionPerformed method )
        Graphics2D g2d = (Graphics2D) g;


        if(leadPoint.getX() > 800)
        {
            super.paintComponent(g2d);
            leadPoint = new point(-5,bottom/2);
            trailPoint = new point(-5,bottom/2);

            resetNum++;
        }else if(leadPoint.getX() < 20 || leadPoint.getX() > 780)super.paintComponent(g2d);

        //set bull/bear color

        g2d.setColor(new Color(47,49,49));
        g2d.fillRect(0,0,800,50);

        if(leadPoint.getY()>trailPoint.getY())g.setColor(Color.RED);
        if(leadPoint.getY()<trailPoint.getY())g.setColor(new Color(8,200, 133));

        g2d.drawLine(leadPoint.getX(), leadPoint.getY(), trailPoint.getX(), trailPoint.getY());
        g2d.setColor(Color.WHITE);

        cost = 390-leadPoint.getY();


        g2d.drawString("Last Price:  $"+cost, costPos.getX(), costPos.getY());
        g2d.drawString("XRP: "+coinNum, balPos.getX()-200, balPos.getY());
        g2d.drawString("USD: $"+bal, balPos.getX(), balPos.getY());

        if(cost <= 0) System.out.println("Error: 0 COST");
    }//end paint

    private void updateVectors() {
        //initailize random stoch function

        double x = leadPoint.getX();
        double y = leadPoint.getY();

        trailPoint.setXY((int)x,(int)y);
        x+=3;
        y=bottom-stoch(x);
        leadPoint.setXY((int)x,(int)y);

    }//end updateVectors

    public void actionPerformed(ActionEvent arg0) {
        repaint();
        updateVectors();
    }

    public double sin(double var){return Math.sin(var);}

    public double stoch(double var){
        int n = 5;
        //allow continuation of graph instead of reset
        var += (resetNum*800);
        double solution = 75*sin(.02*var)+bottom/2;

        return solution-(var%(getRandInt()))+(var%(100))-(var%(getRandInt()));
    }

    public int getRandInt(){
        Random rand = new Random();
        int sol = rand.nextInt((this.max - this.min) + 1) + this.min;
        //System.out.println(sol);
        return sol;
    }
}
