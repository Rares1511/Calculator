import com.sun.source.doctree.AttributeTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

public class CalcFrame extends JFrame implements ActionListener, ComponentListener, KeyListener {

    public static JFrame frame;
    public final static Rectangle frameSize = new Rectangle ( 0, 0, 350, 650 );
    public static JTextField textField;
    static final int numberOfLines = 7;
    static final int numberofColums = 5;
    static MyButton[] myButtons = new MyButton[numberofColums * numberOfLines];
    static Calculator calculator = new Calculator ( );

    CalcFrame ( ) { }

    private MyButton getButton ( String id, CalcFrame calcFrame ) {

        JButton button = new JButton ( id );
        button.addActionListener ( calcFrame );
        button.setVerticalAlignment ( JButton.CENTER );
        return new MyButton ( button, MyButton.numberOfButtons );

    }

    private Font getTextFieldFont ( JTextField textField, String textFieldText ) {
        Font textFieldFont = textField.getFont ( );
        int size = textFieldFont.getSize ( );
        double pixelSize = size * 6.75 / 12;
        if ( pixelSize * textFieldText.length ( ) > textField.getWidth ( ) ) {
            while (pixelSize * textFieldText.length() > textField.getWidth()) {
                size--;
                pixelSize = size * 6.75 / 12;
            }
        }
        else {
            if ( textFieldText.length ( ) > 0 ) {
                while (pixelSize * textFieldText.length() < textField.getWidth()) {
                    size++;
                    pixelSize = size * 6.75 / 12;
                }
                size--;
            }
        }
        if ( pixelSize > ( textField.getHeight ( ) / 2.0 ) ) {
            while ( pixelSize > ( textField.getHeight () / 2.0 ) ) {
                size--;
                pixelSize = size * 6.75 / 12;
            }
        }
        return new Font ( "Calibri", Font.PLAIN, size );

    }

    @Deprecated
    public static void main ( String[] args ) {

        CalcFrame calcFrame = new CalcFrame ( );

        frame = new JFrame ( "Calculator" );
        frame.setLocation ( 600, 150 );
        frame.setMinimumSize ( new Dimension ( 500, 650 ) );
        frame.setSize ( frameSize.width, frameSize.height );

        textField = new JTextField ( );

        String[] buttonSigns = { "2ⁿᵈ", "π", "e", "mod", "←",
                                 "x²", "¹/ₓ", "|x|", "C", "CE",
                                 "√", "(", ")", "n!", "÷",
                                 "xʸ", "7", "8", "9", "×",
                                 "10ˣ", "4", "5", "6", "-",
                                 "log", "1", "2", "3", "+",
                                 "ln", "±", "0", ".", "="};

        for ( int i = 0; i < buttonSigns.length; i++ )
            myButtons[i] = calcFrame.getButton ( buttonSigns[i], calcFrame );

        calcFrame.componentResized ( null );

        try { UIManager.setLookAndFeel ( UIManager.getSystemLookAndFeelClassName ( ) ); }
        catch ( Exception e ) { e.printStackTrace ( ); }

        Font textFieldFont = new Font ( "Calibri", Font.PLAIN, 30 );
        textField.setFont ( textFieldFont );
        textField.setEditable ( false );
        textField.addKeyListener ( calcFrame );
        textField.setBackground ( Color.LIGHT_GRAY );
        textField.setAlignmentX ( JTextField.LEFT_ALIGNMENT );
        textField.setAlignmentY ( JTextField.BOTTOM_ALIGNMENT );

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
        String textFieldText = calculator.add ( e.getActionCommand ( ) );
        textField.setFont ( getTextFieldFont ( textField, textFieldText ) );
        textField.setText ( textFieldText );
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

        int fontSize = ( int ) Point.distance ( 0, 0, frameHeight * 0.50, frameWidth * 0.75 ) / 20;
        Font buttonFont = new Font ( "Calibri", Font.PLAIN, fontSize );

        Rectangle textFieldSize = new Rectangle ( spaceBetweenX, spaceBetweenY, finalX, finalY );
        Rectangle[] bounds = new Rectangle[numberOfLines * numberofColums];

        int buttonHeight = ( int ) ( frameHeight - textFieldSize.height - spaceBetweenY * ( numberOfLines + 1.75 ) ) / numberOfLines;
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

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println ( "It has been typed n00b" );
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println ( "Bench pressed xD :)" );
        String text;
        if ( e.getKeyChar ( ) == '/' )
            text = calculator.add ( Calculator.DIVSIGN );
        else if ( e.getKeyChar ( ) == '*' )
            text = calculator.add ( Calculator.MULSIGN );
        else if ( e.getKeyCode ( ) == KeyEvent.VK_ENTER )
            text = calculator.add ( Calculator.EQUALSIGN );
        else if ( e.getKeyCode ( ) == KeyEvent.VK_BACK_SPACE )
            text = calculator.add ( Calculator.ERASELEFTSIGN );
        else if ( e.getKeyCode ( ) == KeyEvent.VK_DELETE )
            text = calculator.add ( "CE" );
        else if ( e.getKeyChar ( ) == '!' )
            text = calculator.add ( Calculator.FACTORIALSIGN );
        else if ( e.getKeyChar ( ) == '^' )
            text = calculator.add ( Calculator.POWERSIGN );
        else
            text = calculator.add ( Character.toString ( e.getKeyChar ( ) ) );
        textField.setFont ( getTextFieldFont ( textField, text ) );
        textField.setText ( text );
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println ( "Released the Kraken" );
    }
}
