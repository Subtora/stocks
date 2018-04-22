import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;
import java.util.*;



public class View implements ActionListener {

    private JFrame frame;
    private JPanel topPanel;
    private JPanel centerPanel;

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

    public View(){
        setupFrame();
        setupPanels();
        setupButtons();

        //frame.setContentPane(panel);
        frame.setVisible(true);

        clock.start();
    }

    private void setupFrame() {
        frame = new JFrame("Stochastic");
        frame.setSize(800,600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
    }

    private void setupPanels() {
//        panel = new JPanel();
//        panel.setSize(800,600);
//        panel.setLayout(null);

        topPanel = new JPanel();
        centerPanel = new JPanel();

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        topPanel.setBackground(Color.cyan);
        centerPanel.setBackground(Color.white);
    }

    public void setupButtons() {
        JButton buyBtn = new JButton("Buy");
        JButton sellBtn = new JButton("Sell");

        buyBtn.addActionListener(this);
        sellBtn.addActionListener(this);

        //addButtonStyle(buyBtn, 0, 0);
        //addButtonStyle(sellBtn, 700, 135);

        topPanel.add(buyBtn);
        topPanel.add(sellBtn);
    }

    public void addButtonStyle(JButton button, int x, int y){
        button.setBounds(x, y, 60, 30);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
    }

//    public void paintComponent(Graphics g){
//        //paint occurs when the method repaint() is called (see actionPerformed method )
//        Graphics2D g2d = (Graphics2D) g;
//
//        if(leadPoint.getX() > 800)
//        {
//            super.paintComponent(g2d);
//            leadPoint = new point(-5,bottom/2);
//            trailPoint = new point(-5,bottom/2);
//
//            resetNum++;
//        }
//
//        //set bull/bear color
//        if(leadPoint.getY()>trailPoint.getY())g.setColor(Color.RED);
//        if(leadPoint.getY()<trailPoint.getY())g.setColor(new Color(0,200,30));
//
//        g2d.drawLine(leadPoint.getX(), leadPoint.getY(), trailPoint.getX(), trailPoint.getY());
//        g2d.setColor(new Color(47,49,49));
//        g2d.fillRect(-10,0,810,50);
//        g2d.setColor(Color.WHITE);
//
//        cost = 390-leadPoint.getY();
//
//
//        g2d.drawString("Last Price:  $"+cost, costPos.getX(), costPos.getY());
//        g2d.drawString("XRP: "+coinNum, balPos.getX()-200, balPos.getY());
//        g2d.drawString("USD: $"+bal, balPos.getX(), balPos.getY());
//
//        if(cost <= 0) System.out.println("Error: 0 COST");
//    }//end paint


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
        // repaint();
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

    public int getRandInt(){
        Random rand = new Random();
        int sol = rand.nextInt((this.max - this.min) + 1) + this.min;
        //System.out.println(sol);
        return sol;
    }

}//end class MyPanel

