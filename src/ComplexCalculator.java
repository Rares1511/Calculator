import java.util.Objects;

public class ComplexCalculator {

    final static String ERASEALLSIGN = "C";
    final static String EQUALSIGN = "=";
    final static String ERASELEFTSIGN = "←";
    Calculator[] calculators = new Calculator[60];
    String[] expressions = new String[30];
    int numberOfParantheses = 0;
    int numberOfCalculators = 0;

    ComplexCalculator ( ) {
        for ( int i = 0; i < 30; i++ ) {
            calculators[i] = new Calculator();
            expressions[i] = "";
        }
    }

    public String add ( String sign ) {
        int totalNumber = numberOfCalculators + numberOfParantheses;
        switch ( sign ) {
            case "(" -> {
                if ( numberOfCalculators > 0 && !calculators[numberOfCalculators - 1].lastIsOperation ( ) )
                    calculators[numberOfCalculators - 1].add ( Calculator.MULSIGN );
                expressions[totalNumber] = "(";
                numberOfParantheses++;
            }
            case ")" -> {
                if ( numberOfParantheses == 0 )
                    break;
                String initExpr = calculators[numberOfCalculators - 1].getExpression ( );
                String result = calculators[numberOfCalculators - 1].add ( Calculator.EQUALSIGN );
                numberOfParantheses--;
                totalNumber--;
                expressions[totalNumber - 1] = Integer.toString ( numberOfCalculators - 1 );
                if ( ! Objects.equals ( result, initExpr ) ) {
                    calculators[numberOfCalculators - 1].add(Calculator.ERASEALLSIGN);
                    if ( numberOfCalculators > 1 )
                        numberOfCalculators--;
                    for (int i = 0; i < result.length(); i++) {
                        calculators[numberOfCalculators - 1].add(result.substring(i, i + 1));
                    }
                }
            }
            case EQUALSIGN -> {
                if ( numberOfCalculators == 0 ) break;
                String initExpr = calculators[numberOfCalculators - 1].getExpression ( );
                String result = "";
                if ( ! Objects.equals ( result, initExpr ) ) {
                    do {
                        result = calculators[numberOfCalculators - 1].add ( Calculator.EQUALSIGN );
                        if ( numberOfCalculators == 1 )
                            break;
                        calculators[numberOfCalculators - 1].add ( Calculator.ERASEALLSIGN );
                        numberOfCalculators--;
                        totalNumber = numberOfCalculators + numberOfParantheses;
                        expressions[totalNumber - 1] = Integer.toString(numberOfCalculators - 1);
                        for ( int i = 0; i < result.length ( ); i++ )
                            calculators[numberOfCalculators - 1].add(result.substring(i, i + 1));
                    } while ( numberOfCalculators > 0 );
                    numberOfParantheses = 0;
                    expressions[0] = Integer.toString ( 0 );
                }
            }
            case ERASELEFTSIGN -> {
                if ( totalNumber > 0 && expressions[totalNumber - 1].equals ( "(" ) ) {
                    numberOfParantheses--;
                    expressions[totalNumber - 1] = "";
                }
                else if ( numberOfCalculators > 0 ){
                    String expr = calculators[numberOfCalculators - 1].add ( Calculator.ERASELEFTSIGN );
                    if ( Objects.equals ( expr, "" ) )
                        numberOfCalculators--;
                }
            }
            case ERASEALLSIGN -> {
                for ( int i = 0; i < totalNumber; i++ ) {
                    expressions[i] = "";
                    calculators[i].add ( Calculator.ERASEALLSIGN );
                }
                numberOfCalculators = 0;
                numberOfParantheses = 0;
            }
            default -> {
                if (totalNumber == 0 || expressions[totalNumber - 1].equals("(")) {
                    numberOfCalculators++;expressions[totalNumber] = Integer.toString(numberOfCalculators - 1);
                }
                calculators[numberOfCalculators - 1].add(sign);
            }
        }
        totalNumber = numberOfCalculators + numberOfParantheses;
        StringBuilder expression = new StringBuilder();
        for ( int i = 0; i < totalNumber; i++ )
            if ( expressions[i].equals ( "(" ) )
                expression.append ( "(" );
            else {
                int position = Integer.parseInt ( expressions[i] );
                expression.append ( calculators[position].getExpression ( ) );
            }
        return expression.toString ( );
    }
}
