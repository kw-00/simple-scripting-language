import java_cup.runtime.*;

%%

%class Lexer
%cup

%%

echo {
    return new Symbol(sym.ECHO);
}

";" {
    return new Symbol(sym.SEMICOLON);
}

\w+ {
    return new Symbol(sym.TOKEN, yytext());
}

\s { /* nic */ }