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

\| {
    return new Symbol(sym.PIPE);
}

>> {
    return new Symbol(sym.REDIR_A);
}

> {
    return new Symbol(sym.REDIR_W);
}


/* Commands */
echo|cat|wc|grep {
    return new Symbol(sym.COMMAND, yytext());
}

/* Statement End */
[;\n\r]+ {
    return new Symbol(sym.S_END, yytext());
}


/* Tokens */
\"(\\\"|\\\\|[^\"\\])*\" {
    return new Symbol(sym.STRING, yytext().substring(1, yytext().length() - 1));
}

[\w\/\.,\?\!]+ {
    return new Symbol(sym.TOKEN, yytext());
}


\s+ {
    return new Symbol(sym.SP, yytext());
}

. {/* nic */}
