public class Calculator {

    final static String DIGIT = "0123456789";
    final static String SIMPLESIGN = "+-×÷";
    final static String PLUSSIGN = "+";
    final static String MINUSSIGN = "-";
    final static String MULSIGN = "×";
    final static String DIVSIGN = "÷";
    final static String SINGULARSIGN = "¹/ₓx²√±%CE.";
    final static String CHANGESIGN = "±";
    final static String INVSIGN = "¹/ₓ";
    final static String SQUARESIGN = "x²";
    final static String PERCSIGN = "%";
    final static String ROOTSIGN = "√";
    final static String ERASELASTSIGN = "CE";
    final static String DECIMALSIGN = ".";
    final static String EQUALSIGN = "=";
    final static String ERASELEFTSIGN = "←";
    final static String ERASEALLSIGN = "C";

    String[] number = new String[3];
    String[] operation = new String[2];

    private static String operate ( String number, String operation ) {
        if ( number.charAt ( number.length ( ) - 1 ) == '.' && !operation.equals ( CHANGESIGN ) )
            number = number.substring ( 0, number.length ( ) - 1 );
        switch (operation) {
            case INVSIGN:
                return Double.toString ( 1 / Double.parseDouble ( number ) );
            case SQUARESIGN:
                return Double.toString ( Double.parseDouble ( number ) * Double.parseDouble ( number ) );
            case ROOTSIGN:
                return Double.toString ( Math.sqrt ( Double.parseDouble ( number ) ) );
            case CHANGESIGN:
                if ( number.contains ( "-" ) )
                    return number.substring ( 1 );
                else
                    return "-" + number;
            case PERCSIGN:
                return Double.toString ( Double.parseDouble ( number ) / 100 );
            case ERASELASTSIGN:
                return "";
            case DECIMALSIGN:
                if ( number.contains ( DECIMALSIGN ) )
                    return number;
                else
                    return number + DECIMALSIGN;
        }
        return number;
    }

    private String calculate ( String num1, String op, String num2 ) {
        return switch (op) {
            case PLUSSIGN -> Double.toString(Double.parseDouble(num1) + Double.parseDouble(num2));
            case MINUSSIGN -> Double.toString(Double.parseDouble(num1) - Double.parseDouble(num2));
            case DIVSIGN -> Double.toString(Double.parseDouble(num1) / Double.parseDouble(num2));
            case MULSIGN -> Double.toString(Double.parseDouble(num1) * Double.parseDouble(num2));
            default -> "";
        };
    }

    private void beautifyChangeSign ( int numberPoz, int operationPoz ) {
        if ( number[numberPoz].isEmpty ( ) ) return;
        double num = Double.parseDouble ( number[numberPoz] );
        if ( num < 0 ) {
            if ( operation[operationPoz].equals ( MINUSSIGN ) )
                this.operation[operationPoz] = PLUSSIGN;
            else if ( operation[operationPoz].equals ( PLUSSIGN ) )
                this.operation[operationPoz] = MINUSSIGN;
            else
                return;
            this.number[numberPoz] = number[numberPoz].substring ( 1 );
        }
    }

    public String add ( String sign ) {
        System.out.println ( sign );
        if ( sign.equals ( ERASEALLSIGN ) ) {
            number[0] = number[1] = number[2] = "";
            operation[0] = operation[1] = "";
        }
        else if ( DIGIT.contains ( sign ) ) {
            if ( !operation[1].isEmpty ( ) )
                number[2] += sign;
            else if ( !operation[0].isEmpty ( ) )
                number[1] += sign;
            else
                number[0] += sign;
        }
        else if ( SIMPLESIGN.contains ( sign ) ) {
            if ( !number[2].isEmpty ( ) ) {
                number[1] = calculate ( number[1], operation[1], number[2] );
                number[0] = calculate ( number[0], operation[0], number[1] );
                number[1] = number[2] = "";
                operation[1] = "";
                operation[0] = sign;
            }
            else if ( operation[0].isEmpty ( ) && !number[0].isEmpty ( ) )
                operation[0] = sign;
            else if ( operation[1].isEmpty ( ) && !number[1].isEmpty ( ) ) {
                if ( sign.equals ( PLUSSIGN ) || sign.equals ( MINUSSIGN )
                || operation[0].equals ( DIVSIGN ) || operation[0].equals ( MULSIGN ) ) {
                    number[0] = calculate ( number[0], operation[0], number[1] );
                    number[1] = "";
                    operation[0] = sign;
                }
                else
                    operation[1] = sign;
            }
        }
        else if ( SINGULARSIGN.contains ( sign ) ) {
            if ( !number[2].isEmpty ( ) )
                number[2] = operate ( number[2], sign );
            else if ( !number[1].isEmpty ( ) && operation[1].isEmpty ( ) )
                number[1] = operate ( number[1], sign );
            else if ( !number[0].isEmpty ( ) && operation[0].isEmpty ( ) )
                number[0] = operate ( number[0], sign );
        }
        else if ( sign.equals ( ERASELEFTSIGN ) ) {
            if ( !number[2].isEmpty ( ) )
                number[2] = number[2].substring ( 0, number[2].length ( ) - 1 );
            else if ( !operation[1].isEmpty ( ) )
                operation[1] = "";
            else if ( !number[1].isEmpty ( ) )
                number[1] = number[1].substring ( 0, number[1].length ( ) - 1 );
            else if ( !operation[0].isEmpty ( ) )
                operation[0] = "";
            else if ( !number[0].isEmpty ( ) )
                number[0] = number[0].substring ( 0, number[0].length ( ) - 1 );
        }
        else if ( sign.equals ( EQUALSIGN ) ) {
            if ( number[2].isEmpty ( ) && !operation[1].isEmpty ( ) )
                return number[0] + operation[0] + number[1] + operation[1] + number[2];
            else if ( number[1].isEmpty ( ) && !operation[0].isEmpty ( ) )
                return number[0] + operation[0] + number[1] + operation[1] + number[2];
            if ( !operation[1].isEmpty ( ) )
                number[1] = calculate ( number[1], operation[1], number[2] );
            if ( !operation[0].isEmpty ( ) )
                number[0] = calculate ( number[0], operation[0], number[1] );
            number[1] = number[2] = "";
            operation[0] = operation[1] = "";
        }
        beautifyChangeSign ( 1, 0 );
        beautifyChangeSign ( 2, 1 );
        return number[0] + operation[0] + number[1] + operation[1] + number[2];
    }

    Calculator ( ) {
        number[0] = number[1] = number[2] = "";
        operation[0] = operation[1] = "";
    }

}
