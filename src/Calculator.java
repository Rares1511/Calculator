public class Calculator {

    final static String DIGIT = "0123456789";
    final static String SPECIALSIGN = "πe.";
    final static String PISIGN = "π";
    final static String ESIGN = "e";
    final static String SIMPLESIGN = "+-×÷xʸmodexpʸ√xlogᵧx";
    final static String PRIOSIGN = "×÷^modyroot,e+,e-log base";
    final static String UNPRIOSIGN = "+-";
    final static String EXPSIGN = "exp";
    final static String MODULOSIGN = "mod";
    final static String PLUSSIGN = "+";
    final static String MINUSSIGN = "-";
    final static String MULSIGN = "×";
    final static String DIVSIGN = "÷";
    final static String POWERSIGN = "xʸ";
    final static String SINGULARSIGN = "¹/ₓx²√±%|x|logln10ˣn!x³³√xx2ˣeˣ";
    final static String LOGYSIGN = "logᵧx";
    final static String POWESIGN = "eˣ";
    final static String POW2SIGN = "2ˣ";
    final static String ROOTSIGN = "ʸ√x";
    final static String CUBICROOTSIGN = "³√x";
    final static String CUBICSIGN = "x³";
    final static String FACTORIALSIGN = "n!";
    final static String POW10SIGN = "10ˣ";
    final static String LOGSIGN = "log";
    final static String LNSIGN = "ln";
    final static String ABSSIGN = "|x|";
    final static String CHANGESIGN = "±";
    final static String INVSIGN = "¹/ₓ";
    final static String SQUARESIGN = "x²";
    final static String PERCSIGN = "%";
    final static String SQUAREDROOTSIGN = "√x";
    final static String ERASELASTSIGN = "CE";
    final static String DECIMALSIGN = ".";
    final static String EQUALSIGN = "=";
    final static String ERASELEFTSIGN = "←";
    final static String ERASEALLSIGN = "C";

    final static int INTVERIFICATIONPOW = 13;
    final static double error = 1e-14;

    String[] number = new String[3];
    String[] operation = new String[2];

    private static double factorial ( double number ) {
        if ( Math.abs ( ( int ) number - number ) > error )
            return number;
        int fact = 1;
        for ( int i = 1; i <= number; i++ )
            fact *= i;
        return fact;
    }

    private String operate ( String number, String operation ) {
        if ( number.charAt ( number.length ( ) - 1 ) == '.' && !operation.equals ( CHANGESIGN ) )
            number = number.substring ( 0, number.length ( ) - 1 );
        double num = Double.parseDouble ( number );
        switch ( operation ) {
            case INVSIGN:
                return Double.toString ( 1 / num );
            case SQUARESIGN:
                return Double.toString ( num * num );
            case SQUAREDROOTSIGN:
                return Double.toString ( Math.sqrt ( num ) );
            case CHANGESIGN:
                if ( number.contains ( "-" ) )
                    return number.substring ( 1 );
                else
                    return "-" + number;
            case PERCSIGN:
                return Double.toString ( num / 100 );
            case ERASELASTSIGN:
                return "";
            case ABSSIGN:
                return Double.toString ( Math.abs ( num ) );
            case LOGSIGN:
                return Double.toString ( Math.log10 ( num ) );
            case LNSIGN:
                return Double.toString ( Math.log ( num ) );
            case POW10SIGN:
                return Double.toString ( Math.pow ( 10, num ) );
            case FACTORIALSIGN:
                return Double.toString ( factorial ( num ) );
            case CUBICSIGN:
                return Double.toString ( num * num * num );
            case CUBICROOTSIGN:
                return Double.toString ( Math.pow ( num, 1.0 / 3 ) );
            case POW2SIGN:
                return Double.toString ( Math.pow ( 2, num ) );
            case POWESIGN:
                return Double.toString ( Math.pow ( Math.E, num ) );
        }
        return number;
    }

    private String calculate ( int position ) {
        double number1 = Double.parseDouble ( number[position] );
        double number2 = Double.parseDouble ( number[position + 1] );
        String op = operation[position];
        number[position + 1] = operation[position] = "";
        return switch ( op ) {
            case PLUSSIGN -> Double.toString ( number1 + number2 );
            case MINUSSIGN -> Double.toString ( number1 - number2 );
            case DIVSIGN -> Double.toString ( number1 / number2 );
            case MULSIGN -> Double.toString ( number1 * number2 );
            case "^" -> Double.toString ( Math.pow ( number1, number2 ) );
            case MODULOSIGN -> Double.toString ( number1 % number2 );
            case ",e+" -> Double.toString ( number1 * Math.pow ( 10, number2 ) );
            case ",e-" -> Double.toString ( number1 / Math.pow ( 10, number2 ) );
            case "yroot" -> Double.toString ( Math.pow ( number1, 1 / number2 ) );
            case " log base " -> Double.toString ( Math.log ( number1 ) / Math.log ( number2 ) );
            default -> "";
        };
    }

    private void beautifyChangeSign ( int numberPoz, int operationPoz ) {
        String numString = number[numberPoz];
        if ( numString.isEmpty ( ) || ( !numString.contains ( ".0" ) && numString.contains ( "." ) ) ) return;
        double num = Double.parseDouble ( number[numberPoz] );
        if ( num < 0 ) {
            if ( changeOperationSign(operationPoz) )
                number[numberPoz] = number[numberPoz].substring ( 1 );
        }
    }

    private String integerVerification ( String number ) {
        if ( number.isEmpty ( ) || number.endsWith ( "." ) )
            return  number;
        double num = Double.parseDouble ( number );
        num *= Math.pow ( 10, INTVERIFICATIONPOW );
        if ( Math.abs ( Math.round ( num )  - num ) < 1 )
            num = Math.round ( num ) / Math.pow ( 10, INTVERIFICATIONPOW );
        if ( Math.abs ( ( int ) num - num ) < error )
            return Integer.toString ( ( int ) num );
        return number;
    }

    private String specialValue ( String number, String sign ) {
        switch (sign) {
            case PISIGN:
                return Double.toString ( Math.PI );
            case ESIGN:
                return Double.toString ( Math.E );
            case DECIMALSIGN:
                if ( number.isEmpty ( ) )
                    return "0.";
                else if ( number.contains ( DECIMALSIGN ) )
                    return number;
                return number + DECIMALSIGN;
        }
        return number;
    }

    private String verifyIntegrity ( String number ) {
        if ( number.isEmpty ( ) || number.endsWith ( "." ) || number.equals ( MINUSSIGN ) ) return number;
        double num = Double.parseDouble ( number );
        if ( num == Double.NEGATIVE_INFINITY || num == Double.POSITIVE_INFINITY )
            return "";
        if ( Double.isNaN ( num ) )
            return "";
        return number;
    }

    private void addOperation ( String sign ) {
        if ( number[1].isEmpty ( ) && !number[0].isEmpty ( ) )
            operation[0] = sign;
        else if ( number[2].isEmpty ( ) && !number[1].isEmpty ( ) ) {
            if ( sign.equals ( PLUSSIGN ) || sign.equals ( MINUSSIGN )
                    || operation[0].equals ( DIVSIGN ) || operation[0].equals ( MULSIGN ) ) {
                number[0] = integerVerification ( calculate ( 0 ) );
                operation[0] = sign;
            }
            else
                operation[1] = sign;
        }
    }

    private void resolve ( ) {
        if ( !operation[1].isEmpty ( ) && !number[2].isEmpty() )
            number[1] = integerVerification ( calculate ( 1 ) );
        if ( !operation[0].isEmpty ( ) && !number[1].isEmpty ( ) )
            number[0] = integerVerification ( calculate ( 0 ) );
    }

    private boolean changeOperationSign ( int position ) {
        int length = operation[position].length ( );
        if ( operation[position].contains ( MINUSSIGN ) )
            operation[position] = operation[position].substring ( 0, length - 1 ) + PLUSSIGN;
        else if ( operation[position].contains ( PLUSSIGN ) )
            operation[position] = operation[position].substring ( 0, length - 1 ) + MINUSSIGN;
        else
            return false;
        return true;
    }

    public boolean lastIsOperation ( ) {
        if ( !number[2].isEmpty ( ) )
            return false;
        if ( !operation[1].isEmpty ( ) )
            return true;
        if ( !number[1].isEmpty ( ) )
            return false;
        return !operation[0].isEmpty();
    }

    private void addDigit ( String digit, int position ) {
        if ( number[position].equals ( "0" ) )
            number[position] = digit;
        else
            number[position] += digit;
    }

    public String add ( String sign ) {

        for ( int i = 0; i < 3; i++ )
            number[i] = verifyIntegrity ( number[i] );

        if ( sign.equals ( ERASEALLSIGN ) ) {
            number[0] = number[1] = number[2] = "";
            operation[0] = operation[1] = "";
        }
        else if ( DIGIT.contains ( sign ) ) {
            if ( !operation[1].isEmpty ( ) )
                addDigit ( sign, 2 );
            else if ( !operation[0].isEmpty ( ) )
                addDigit ( sign, 1 );
            else
                addDigit ( sign, 0 );
        }
        else if ( SPECIALSIGN.contains ( sign ) ) {
            if ( !operation[1].isEmpty ( ) )
                number[2] = specialValue ( number[2], sign );
            else if ( !operation[0].isEmpty ( ) )
                number[1] = specialValue ( number[1], sign );
            else
                number[0] = specialValue ( number[0], sign );
        }
        else if ( SINGULARSIGN.contains ( sign ) ) {
            if ( !number[2].isEmpty ( ) )
                number[2] = integerVerification ( operate ( number[2], sign ) );
            else if ( operation[1].contains ( PLUSSIGN ) || operation[1].contains ( MINUSSIGN ) && sign.equals ( CHANGESIGN ) )
                changeOperationSign ( 1 );
            else if ( !number[1].isEmpty ( ) )
                number[1] = integerVerification ( operate ( number[1], sign ) );
            else if ( operation[0].contains ( PLUSSIGN ) || operation[0].contains ( MINUSSIGN ) && sign.equals ( CHANGESIGN ) )
                changeOperationSign ( 0 );
            else if ( !number[0].isEmpty ( ) )
                number[0] = integerVerification ( operate ( number[0], sign ) );
        }
        else if ( SIMPLESIGN.contains ( sign ) ) {
            if ( !number[2].isEmpty ( ) )
                resolve ( );
            else if ( !number[1].isEmpty ( ) && PRIOSIGN.contains ( operation[0] ) && !UNPRIOSIGN.contains ( operation[0] ) )
                resolve ( );
            switch ( sign ) {
                case POWERSIGN -> addOperation ( "^" );
                case EXPSIGN -> addOperation ( ",e+" );
                case ROOTSIGN -> addOperation ( "yroot" );
                case LOGYSIGN -> addOperation ( " log base " );
                default -> addOperation ( sign );
            }
        }
        else if ( sign.equals ( ERASELASTSIGN ) ) {
            if ( !number[2].isEmpty ( ) )
                number[2] = "";
            else if ( !operation[1].isEmpty ( ) )
                operation[1] = "";
            else if ( !number[1].isEmpty ( ) )
                number[1] = "";
            else if ( !operation[0].isEmpty ( ) )
                operation[0] = "";
            else
                number[0] = "";
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
        else if ( sign.equals ( EQUALSIGN ) )
            resolve ( );
        beautifyChangeSign ( 1, 0 );
        beautifyChangeSign ( 2, 1 );
        return number[0] + operation[0] + number[1] + operation[1] + number[2];
    }

    public String getExpression ( ) {
        return number[0] + operation[0] + number[1] + operation[1] + number[2];
    }

    Calculator ( ) {
        number[0] = number[1] = number[2] = "";
        operation[0] = operation[1] = "";
    }

}