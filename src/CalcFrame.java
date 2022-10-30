import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalcFrame extends JFrame implements KeyListener, ComponentListener, ActionListener {

    public static JFrame frame = new JFrame ( );
    public static JLabel calcLabel;
    public static JButton optionsButton;
    private static final ScientificFrame scFrame = new ScientificFrame ( frame );

    CalcFrame ( ) { }


    @Deprecated
    public static void main ( String[] args ) {

        CalcFrame calcFrame = new CalcFrame ( );

        scFrame.setActionListener ( calcFrame );
        scFrame.setKeyListener ( calcFrame );

        frame.setMinimumSize ( new Dimension( 500, 650 ) );
        frame.setLocation ( 450, 200 );
        optionsButton = new JButton ( "☰" );
        calcLabel = new JLabel ( );

        optionsButton.setBorder ( BorderFactory.createEmptyBorder ( ) );
        optionsButton.setVerticalAlignment ( JButton.TOP );
        optionsButton.addActionListener ( calcFrame );

        calcLabel.setText ( "Scientific" );
        calcLabel.setVerticalAlignment ( JLabel.TOP );

        try { UIManager.setLookAndFeel ( UIManager.getSystemLookAndFeelClassName ( ) ); }
        catch ( Exception e ) { e.printStackTrace ( ); }

        frame.add ( optionsButton );
        frame.add ( calcLabel );

        frame.addComponentListener ( calcFrame );
        frame.setLayout ( null );
        frame.setVisible ( true );
        frame.show ( );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

        frame.setName ( scFrame.Name );

    }

    @Override
    public void actionPerformed ( ActionEvent e ) {
        System.out.println ( e.getActionCommand ( ) );
    }

    @Override
    public void componentResized ( ComponentEvent e ) {
        Dimension actualSize = frame.getContentPane().getSize();

        int frameWidth = ( int ) actualSize.getWidth ( );
        int frameHeight = ( int ) actualSize.getHeight ( );

        int spaceBetweenX = frameWidth / 100;
        int spaceBetweenY = frameHeight / 100;
        int finalX = frameWidth - 2 * spaceBetweenX;
        int finalY = frameHeight / 10;

        int fontSize = ( int ) Point.distance ( 0, 0, frameHeight * 0.75, frameWidth * 0.45 ) / 30;
        Font buttonFont = new Font ( "", Font.PLAIN, fontSize );
        Font labelFont = new Font ( "Calibri", Font.PLAIN, ( int ) ( fontSize * 1.5 ) );

        optionsButton.setFont ( buttonFont );
        optionsButton.setBounds ( spaceBetweenX, spaceBetweenY, finalX / 5, ( int ) ( finalY / 1.5 )  );

        calcLabel.setBounds ( spaceBetweenX + finalX / 5, spaceBetweenY, finalX, ( int ) ( finalY / 1.5 ) );
        calcLabel.setFont ( labelFont );

        scFrame.setStartHeight ( calcLabel.getHeight ( ) + spaceBetweenY );
        scFrame.draw ( );
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
