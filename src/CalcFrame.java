import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CalcFrame implements KeyListener, ComponentListener, ActionListener {

    public static JFrame frame = new JFrame ( "Calculator" );
    public static JPopupMenu popupMenu = new JPopupMenu ( "☰" );
    public static JLabel calcLabel;
    public static JButton optionsButton;
    private static final Drawer[] drawers = new Drawer[2];
    private static String currentCalc;
    private static int currentCalcCode = 0;
    private static final String[] menuItemSigns = new String[30];

    CalcFrame ( ) { }

    @Deprecated
    public static void main ( String[] args ) throws FileNotFoundException {

        CalcFrame calcFrame = new CalcFrame ( );

        File calcConfigFile = new File ( "calculator config.txt" );
        Scanner scanner = new Scanner ( calcConfigFile );

        int currentDrawer = 0;
        while ( scanner.hasNextLine ( ) ) {
            menuItemSigns[currentDrawer] = scanner.nextLine ( );
            int lines = scanner.nextInt();
            int columns = scanner.nextInt();
            scanner.nextLine();
            String[] buttonSigns = new String[lines * columns];
            for (int i = 0; i < lines; i++) {
                String[] line = scanner.nextLine ( ).split ( "," );
                System.arraycopy ( line, 0, buttonSigns, i * columns, line.length );
            }
            drawers[currentDrawer++] = new Drawer ( menuItemSigns[currentDrawer - 1], frame, lines, columns, buttonSigns );
            if (scanner.hasNextLine ( ) )
                scanner.nextLine ( );
        }

        currentCalc = drawers[0].getName ( );

        Font menuItemFont = new Font ( "Calibri", Font.PLAIN, 40 );
        JMenuItem[] items = new JMenuItem[menuItemSigns.length];
        for ( int i = 0; i < items.length; i++ ) {
            items[i] = new JMenuItem ( menuItemSigns[i] );
            items[i].setFont ( menuItemFont );
            items[i].addActionListener ( calcFrame );
            popupMenu.add ( items[i] );
        }

        for ( Drawer drawer : drawers ) { drawer.setActionListener ( calcFrame ); drawer.setKeyListener ( calcFrame ); }

        frame.setMinimumSize ( new Dimension( 500, 650 ) );
        frame.setLocation ( 450, 200 );
        optionsButton = new JButton ( "☰" );
        calcLabel = new JLabel ( );

        optionsButton.setBorder ( BorderFactory.createEmptyBorder ( ) );
        optionsButton.setVerticalAlignment ( JButton.TOP );
        optionsButton.addActionListener ( calcFrame );

        calcLabel.setText ( currentCalc );
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

    }

    @Override @Deprecated
    public void actionPerformed ( ActionEvent e ) {
        String command = e.getActionCommand ( );
        if ( command.equals ( "☰" ) )
            popupMenu.show ( frame, optionsButton.getX ( ), optionsButton.getY ( ) );
        else {
            if ( command.equals ( currentCalc ) )
                return;
            for ( int i = 0; i < menuItemSigns.length; i++ ) {
                if ( command.equals ( menuItemSigns[i] ) ) {
                    drawers[currentCalcCode].remove ( );
                    currentCalcCode = i;
                    currentCalc = drawers[i].getName ( );
                    drawers[i].add ( );
                    drawers[i].draw ( );
                    componentResized ( null );
                    calcLabel.setText ( currentCalc );
                    frame.hide ( );
                }
            }
        }
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

        drawers[currentCalcCode].setStartHeight ( calcLabel.getHeight ( ) + spaceBetweenY );
        drawers[currentCalcCode].draw ( );
    }

    @Override
    public void componentMoved ( ComponentEvent e ) { }

    @Override
    public void componentShown ( ComponentEvent e ) { }

    @Override @Deprecated
    public void componentHidden ( ComponentEvent e ) { frame.show ( ); }

    @Override
    public void keyTyped ( KeyEvent e ) { }

    @Override
    public void keyPressed ( KeyEvent e ) { }

    @Override
    public void keyReleased ( KeyEvent e ) { }
}
