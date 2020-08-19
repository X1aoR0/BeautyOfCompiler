public class Token {

    public String inputStr = "age>=45";
    public int cur = 0;


    public static void main(String[] args) {

        DfaState dfaState = DfaState.Initial;
        Token token = new Token();
        token.AnalyseToken();

    }

    public void AnalyseToken(){
        //这里可以全部换成一个StringBuilder
        StringBuilder idBuilder = new StringBuilder();
        StringBuilder intBuilder = new StringBuilder();
        StringBuilder gtBuiler = new StringBuilder();

        DfaState dfaState = DfaState.Initial;
        boolean isOver = false;
        while(!isOver){
            switch (dfaState){
                case Initial:
                    if(isFinal()){
                        isOver = true;
                    }
                    else if(Character.isAlphabetic(lookAhead())){
                        dfaState = DfaState.Id;
                        idBuilder.append(getNextChar());
                    }else if(Character.isDigit(lookAhead())){
                        dfaState = DfaState.Intliteral;
                        intBuilder.append(getNextChar());
                    }else if(lookAhead() == '>'){
                        dfaState = DfaState.GT;
                        gtBuiler.append(getNextChar());
                    }
                    break;
                case GT:
                    if(isFinal()){
                        isOver = true;
                        printToken(gtBuiler,dfaState);
                    }
                    else if(lookAhead() == '=')
                        gtBuiler.append(getNextChar());
                    else{
                        printToken(gtBuiler,dfaState);
                        dfaState = DfaState.Initial;
                    }
                    break;
                case Id:
                    if(isFinal()){
                        isOver = true;
                        printToken(idBuilder,dfaState);

                    }
                    else if(Character.isAlphabetic(lookAhead()) || Character.isDigit(lookAhead())){
                        idBuilder.append(getNextChar());
                    }else {
                        printToken(idBuilder,dfaState);
                        dfaState = DfaState.Initial;
                    }
                    break;
                case Intliteral:
                    if(isFinal()){
                        isOver = true;
                        printToken(intBuilder,dfaState);
                    }
                    else if(Character.isDigit(lookAhead())){
                        intBuilder.append(getNextChar());
                    }else {
                        printToken(intBuilder,dfaState);
                        dfaState = DfaState.Initial;
                    }
                    break;
            }
        }

    }

    public void printToken(StringBuilder builder,DfaState dfaState){
        switch (dfaState){
            case GT:
                System.out.println(builder.toString()+" : "+"GT");
                break;
            case Id:
                System.out.println(builder.toString()+" : "+"ID");
                break;
            case Intliteral:
                System.out.println(builder.toString()+" : "+"Intliteral");
        }
        builder.delete(0,builder.length());

    }
    public char lookAhead(){
        return (inputStr.toCharArray())[cur];
    }

    public char getNextChar(){
        return (inputStr.toCharArray())[cur++];
    }

    public boolean isFinal(){
        return cur == inputStr.length();
    }
}
