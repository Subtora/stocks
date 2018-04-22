import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;
import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.applet.*;


public class MyPanel extends JPanel implements ActionListener{

    private int bottom = 564;
    private point leadPoint = new point(-2,bottom/2);
    private point trailPoint = new point(-2,bottom/2);
    private point costPos = new point(20, 30);
    private point balPos = new point(700,30);

    private double cost = bottom/2;
    private double bal = 20.0;
    private double coinNum = 0;
    private int resetNum = 0;

    //for getRandInt
    private int max = 30;
    private int min = 10;

    //tick/second
    Timer clock = new Timer(50,this);

    MyPanel(){
        setSize(800,600);
        clock.start();
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
        }

        //set bull/bear color
        if(leadPoint.getY()>trailPoint.getY())g.setColor(Color.RED);
        if(leadPoint.getY()<trailPoint.getY())g.setColor(new Color(0,200,30));

        g2d.drawLine(leadPoint.getX(), leadPoint.getY(), trailPoint.getX(), trailPoint.getY());
        g2d.setColor(new Color(47,49,49));
        g2d.fillRect(-10,0,810,50);
        g2d.setColor(Color.WHITE);

        cost = 390-leadPoint.getY();



        g2d.drawString("Last Price:  $"+cost, costPos.getX(), costPos.getY());
        g2d.drawString("XRP: "+coinNum, balPos.getX()-200, balPos.getY());
        g2d.drawString("USD: $"+bal, balPos.getX(), balPos.getY());

        if(cost <= 0) System.out.println("Error: 0 COST");
    }//end paint


    public static void main(String[] args) {
        JFrame j = new JFrame("Stochastic");
        JPanel p = new MyPanel();

        JButton buy = new JButton("Buy");
        JButton sell = new JButton("Sell");

        p.setLayout(null);

        btnSetup(buy, 700, 100);
        btnSetup(sell, 700, 135);

        p.add(sell);
        p.add(buy);

        p.setVisible(true);

        j.setSize(800,600);
        j.setVisible(true);
        j.setResizable(false);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setContentPane(p);

        ///

    }//end main


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
    //general methods
    public double sin(double var){return Math.sin(var);}
    public double cos(double var){return Math.cos(var);}
    public double stoch(double var){
        int n = 5;
        //allow continuation of graph instead of reset
        var += (resetNum*800);
        double solution = 100*sin(.02*var)+bottom/2;

        return solution-(var%(getRandInt()))+(var%(100))-(var%(getRandInt()));
    }
    public static void btnSetup(JButton button, int x, int y){
        button.setBounds(x, y, 60, 30);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        //button.addActionListener(this);
    }
    public int getRandInt(){
        Random rand = new Random();
        int sol = rand.nextInt((this.max - this.min) + 1) + this.min;
        //System.out.println(sol);
        return sol;
    }

}//end class MyPanel

