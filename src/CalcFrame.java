import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JButton;

class MyButton {

    JButton button;
    int mark;
    static int numberOfButtons = 0;

    MyButton ( JButton button, int mark ) {
        this.button = button;
        this.mark = mark;
        numberOfButtons++;
    }

}

public class CalcFrame extends JFrame implements ActionListener, ComponentListener {

    public static JFrame frame;
    public final static Rectangle frameSize = new Rectangle ( 0, 0, 350, 650 );
    public static JTextField textField;
    static final int numberOfLines = 6;
    static final int numberofColums = 4;
    static MyButton[] myButtons = new MyButton[30];
    static Calculator calculator = new Calculator ( );

    CalcFrame ( ) { }

    private MyButton getButton ( String id, CalcFrame calcFrame ) {

        JButton button = new JButton ( id );
        button.addActionListener ( calcFrame );
        button.setVerticalAlignment ( JButton.CENTER );
        return new MyButton ( button, MyButton.numberOfButtons );

    }

    @Deprecated
    public static void main ( String[] args ) {

        CalcFrame calcFrame = new CalcFrame ( );

        frame = new JFrame ( "Calculator" );
        frame.setBounds ( frameSize );
        frame.setMinimumSize ( frame.getSize ( ) );

        textField = new JTextField ( );

        String[] buttonSigns = { "%", "CE", "C", "←", "¹/ₓ", "x²", "√", "÷", "7", "8", "9", "×", "6", "5", "4", "-",
                "1", "2", "3", "+", "±", "0", ".", "=" };

        for ( int i = 0; i < buttonSigns.length; i++ ) {
            myButtons[i] = calcFrame.getButton ( buttonSigns[i], calcFrame );
        }

        calcFrame.componentResized ( null );

        try { UIManager.setLookAndFeel ( UIManager.getSystemLookAndFeelClassName ( ) ); }
        catch ( Exception e ) { e.printStackTrace ( ); }

        textField.setEditable ( false );
        textField.setBackground ( Color.LIGHT_GRAY );

        for ( int i = 0; i < MyButton.numberOfButtons; i++ )
            frame.add ( myButtons[i].button );

        frame.add ( textField );
        frame.setLayout ( null );
        frame.setVisible ( true );
        frame.show ( );

        frame.addComponentListener ( calcFrame );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

    }

    @Override
    public void actionPerformed ( ActionEvent e ) {
        textField.setText ( calculator.add ( e.getActionCommand ( ) ) );
    }

    @Override
    public void componentResized ( ComponentEvent e ) {
        System.out.println ( "It has been resized" );
        Dimension actualSize = frame.getContentPane().getSize();

        int frameWidth = ( int ) actualSize.getWidth ( );
        int frameHeight = ( int ) actualSize.getHeight ( );

        int spaceBetweenX = frameWidth / 100;
        int spaceBetweenY = frameHeight / 100;
        int finalX = frameWidth - 2 * spaceBetweenX;
        int finalY = frameHeight / 5;

        int fontSize = ( int ) Point.distance ( 0, 0, frameHeight, frameWidth ) / 21;
        Font buttonFont = new Font ( "Calibri", Font.PLAIN, fontSize );

        Rectangle textFieldSize = new Rectangle ( spaceBetweenX, spaceBetweenY, finalX, finalY );
        Rectangle[] bounds = new Rectangle[30];

        int buttonHeight = ( frameHeight - textFieldSize.height - spaceBetweenY * ( numberOfLines + 2 ) ) / numberOfLines;
        int buttonWidth = ( frameWidth - spaceBetweenX * ( numberofColums + 3 ) ) / numberofColums;

        for ( int i = 0; i < numberOfLines; i++ ) {
            for ( int j = 0; j < numberofColums; j++ ) {
                int position = i * numberofColums + j;
                int startX = j * buttonWidth + ( j + 1 ) * spaceBetweenX;
                int startY = i * buttonHeight + ( int ) textFieldSize.getMaxY ( ) + ( i + 1 ) * spaceBetweenY;
                int width = buttonWidth;
                if ( j == numberofColums - 1 )
                    width = actualSize.width - ( int ) textFieldSize.getMinX ( ) - startX;
                bounds[position] = new Rectangle ( startX, startY, width, buttonHeight );
            }
        }

        for ( int i = 0; i < MyButton.numberOfButtons; i++ ) {
            myButtons[i].button.setBounds ( bounds[myButtons[i].mark] );
            myButtons[i].button.setFont ( buttonFont );
        }
        textField.setBounds ( textFieldSize );
        textField.setFont ( buttonFont );
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        System.out.println ( "It has been moved" );
    }

    @Override
    public void componentShown(ComponentEvent e) {
        System.out.println ( "It has been shown" );
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        System.out.println ( "It has been hidden" );
    }
}
