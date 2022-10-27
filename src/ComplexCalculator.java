public class ComplexCalculator {

    String[] expressions;
    Calculator[] calculators;
    int numberOfCalculators;

    ComplexCalculator ( ) {
        expressions = new String[30];
        calculators = new Calculator[30];
        numberOfCalculators = 0;
    }

    public String add ( String sign ) {
        if ( sign.equals ( "(" ) )
            numberOfCalculators++;
        else if ( sign.equals ( ")" ) ) {
            expressions[numberOfCalculators] = calculators[numberOfCalculators].add ( Calculator.EQUALSIGN );
            numberOfCalculators--;
        }
        else
            expressions[numberOfCalculators] = calculators[numberOfCalculators].add ( sign );
        String expression = expressions[0];
        for ( int i = 1; i < numberOfCalculators; i++ )
            expression = "(" + expressions[i];
        return expression;
    }

}
