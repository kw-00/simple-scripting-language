import java_cup.runtime.*;

%%

%class Lexer
%cup

%%

/* Operators */
= {
    return new Symbol(sym.ASSIGNMENT_OP);
}

\$ {
    return new Symbol(sym.DOLLAR_OP);
}

\( {
    return new Symbol(sym.L_PAREN);
}

\) {
    return new Symbol(sym.R_PAREN);
}

> {
    return new Symbol(sym.REDIR_W);
}

>> {
    return new Symbol(sym.REDIR_A);
}

/* Commands */
echo {
    return new Symbol(sym.ECHO);
}

cat {
    return new Symbol(sym.CAT);
}

wc {
    return new Symbol(sym.WC);
}

/* Statement End */
[;\n\r]+ {
    return new Symbol(sym.S_END, yytext());
}


/* Tokens */
\"(\\\"|\\|[^\"])*\" {
    return new Symbol(sym.TOKEN, yytext().substring(1, yytext().length() - 1).replaceAll("[\n\r]", ""));
}

[\w\/\.,\?\!]+ {
    return new Symbol(sym.TOKEN, yytext());
}


\s { /* nic */ }

. {/* nic */}
