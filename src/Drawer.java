import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class MyButton {
    JButton button;
    int mark;

    MyButton ( JButton button, int mark ) {
        this.button = button;
        this.mark = mark;
    }

    public static MyButton getButton ( String id, ActionListener actionListener, int mark ) {
        JButton button = new JButton ( id );
        button.setVerticalAlignment ( JButton.CENTER );
        button.addActionListener ( actionListener );
        return new MyButton ( button, mark );
    }
}

public class Drawer {

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            String textFieldText = calculator.add ( e.getActionCommand ( ) );
            textField.setFont ( getTextFont ( textField.getFont ( ), textFieldText, textField.getWidth ( ), textField.getHeight ( ) ) );
            textField.setText ( textFieldText );
            int hiddenStage = 2;
            if ( e.getActionCommand ( ).equals ( Calculator.ERASELASTSIGN ) )
                hiddenStage = 0;
            else if ( e.getActionCommand ( ).equals ( CHANGEFUNCSIGN ) )
                hiddenStage = 1;
            else if ( !e.getActionCommand ( ).equals ( Calculator.ERASEALLSIGN ) && myButtons[3].button.getText ( ).equals ( Calculator.ERASEALLSIGN ) )
                hiddenStage = 0;
            Rewrite ( hiddenStage );
        }
    };
    private KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed ( KeyEvent e ) {
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
            else if ( e.getKeyCode ( ) == KeyEvent.VK_P )
                text = calculator.add ( Calculator.PISIGN );
            else
                text = calculator.add ( Character.toString ( e.getKeyChar ( ) ) );
            textField.setFont ( getTextFont ( textField.getFont ( ), text, textField.getWidth ( ), textField.getHeight ( ) ) );
            textField.setText ( text );
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };
    private final String name;
    protected int startHeight;
    protected JFrame frame;
    protected int numberOfLines;
    protected int numberOfColumns;
    protected MyButton[] myButtons;
    protected String[] buttonSigns;
    protected JTextField textField;
    private final ComplexCalculator calculator = new ComplexCalculator ( );

    private final static String CHANGEFUNCSIGN = "2ⁿᵈ";
    private final static double pixelSizeModifier = 6.75 / 12;
    private static final String[][] hiddenButtonSigns = { { "CE" }, { "x³", "³√x", "ʸ√x", "2ˣ", "logᵧx", "eˣ" } };
    private static final int[][] hiddenButtonCodes = { { 3 }, { 5, 10, 15, 20, 25, 30 } };

    private static Font getTextFont ( Font font, String text, int width, int height ) {

        int size = font.getSize ( );
        double pixelSize = size * pixelSizeModifier;
        if ( pixelSize * text.length ( ) > width ) {
            while ( pixelSize * text.length() > width )
                pixelSize = ( --size ) * pixelSizeModifier;
        }
        else if ( text.length ( ) > 0 ) {
            while ( pixelSize * text.length() < width )
                pixelSize = ( ++size ) * pixelSizeModifier;
            size--;
        }
        while ( pixelSize > ( height / 2.0 ) )
            pixelSize = ( --size ) * pixelSizeModifier;
        return new Font ( font.getFontName ( ), Font.PLAIN, size );
    }

    private void Rewrite ( int hiddenStage ) {
        if ( name.equals ( "Standard" ) ) return;
        if ( hiddenStage >= hiddenButtonCodes.length ) return;
        for ( int i = 0; i < hiddenButtonCodes[hiddenStage].length && hiddenButtonSigns[hiddenStage][i] != null; i++ ) {
            int position = hiddenButtonCodes[hiddenStage][i];
            String trasnferText = myButtons[position].button.getText ( );
            myButtons[position].button.setText ( hiddenButtonSigns[hiddenStage][i] );
            hiddenButtonSigns[hiddenStage][i] = trasnferText;
        }
    }

    public String getName ( ) { return name; }

    public void setActionListener (ActionListener actionListener ) { this.actionListener = actionListener; }

    public void setKeyListener ( KeyListener keyListener ) { this.keyListener = keyListener; }

    public void setStartHeight ( int startHeight ) { this.startHeight = startHeight; }

    Drawer ( String name, JFrame frame, int numberOfLines, int numberOfColumns, String[] buttonSigns ) {
        this.name = name;
        this.frame = frame;
        this.numberOfColumns = numberOfColumns;
        this.numberOfLines = numberOfLines;
        this.buttonSigns = new String[numberOfColumns * numberOfLines];
        myButtons = new MyButton[numberOfColumns * numberOfLines];
        for ( int i = 0; i < buttonSigns.length; i++ ) {
            this.buttonSigns[i] = buttonSigns[i];
            myButtons[i] = MyButton.getButton ( buttonSigns[i], actionListener, i );
        }
        textField = new JTextField ( );

        for ( MyButton myButton : myButtons ) frame.add ( myButton.button );

        Font textFieldFont = new Font ( "Times New Roman", Font.PLAIN, 30 );
        textField.setFont ( textFieldFont );
        textField.setEditable ( false );
        textField.addKeyListener ( keyListener );
        textField.setBackground ( Color.LIGHT_GRAY );

        frame.add ( textField );

        draw ( );
    }

    public void add ( ) {
        for ( MyButton myButton : myButtons ) frame.add ( myButton.button );
        frame.add ( textField );
    }

    public void draw ( ) {

        Dimension actualSize = frame.getContentPane().getSize();

        int frameWidth = ( int ) actualSize.getWidth ( );
        int frameHeight = ( int ) actualSize.getHeight ( );

        int spaceBetweenX = frameWidth / 100;
        int spaceBetweenY = frameHeight / 100;
        int finalX = frameWidth - 2 * spaceBetweenX;
        int finalY = ( frameHeight - startHeight ) / 5;

        int fontSize = ( int ) Point.distance ( 0, 0, frameHeight * 0.70, frameWidth * 0.45 ) / 20;
        Font buttonFont = new Font ( "Calibri", Font.PLAIN, fontSize );

        Rectangle textFieldSize = new Rectangle ( spaceBetweenX, spaceBetweenY + startHeight, finalX, finalY );
        Rectangle[] bounds = new Rectangle[numberOfLines * numberOfColumns];

        int buttonHeight = ( int ) ( frameHeight - startHeight - textFieldSize.height - spaceBetweenY * ( numberOfLines + 1.75 ) ) / numberOfLines;
        int buttonWidth = ( frameWidth - spaceBetweenX * ( numberOfColumns + 3 ) ) / numberOfColumns;

        for ( int i = 0; i < numberOfLines; i++ ) {
            for ( int j = 0; j < numberOfColumns; j++ ) {
                int position = i * numberOfColumns + j;
                int startX = j * buttonWidth + ( j + 1 ) * spaceBetweenX;
                int startY = i * buttonHeight + ( int ) textFieldSize.getMaxY ( ) + ( i + 1 ) * spaceBetweenY;
                int width = buttonWidth;
                if ( j == numberOfColumns - 1 )
                    width = actualSize.width - ( int ) textFieldSize.getMinX ( ) - startX;
                bounds[position] = new Rectangle ( startX, startY, width, buttonHeight );
            }
        }

        for ( MyButton myButton : myButtons ) {
            myButton.button.setBounds ( bounds[myButton.mark] );
            myButton.button.setFont ( buttonFont );
        }
        textField.setBounds ( textFieldSize );
    }

    @Deprecated
    public void remove ( ) {
        for ( MyButton myButton : myButtons ) frame.remove ( myButton.button );
        frame.remove ( textField );
        frame.hide ( );
    }

}
