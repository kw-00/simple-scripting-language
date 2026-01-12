import java_cup.runtime.*;

%%

%class Lexer
%cup

%%

echo {
    return new Symbol(sym.ECHO);
}

cat {
    return new Symbol(sym.CAT);
}

wc {
    return new Symbol(sym.WC);
}


[;\n\r]+ {
    return new Symbol(sym.S_END, yytext());
}

> {
    return new Symbol(sym.REDIR_W);
}

>> {
    return new Symbol(sym.REDIR_A);
}

\"(\\\"|\\|[^\"])*\" {
    return new Symbol(sym.TOKEN, yytext().substring(1, yytext().length() - 1).replaceAll("[\n\r]", ""));
}

[\w\/\.,\?\!]+ {
    return new Symbol(sym.TOKEN, yytext());
}


\s { /* nic */ }

. {/* nic */}
