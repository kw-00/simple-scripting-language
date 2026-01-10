import java_cup.runtime.*;

%%

%cup 
%class Lexer

%%

"+"			{ return new Symbol(sym.PLUS); }
"*"			{ return new Symbol(sym.TIMES); }
[0-9]+	    		{ return new Symbol(sym.NUMBER, Integer.parseInt(yytext())); }
\s			{ /* nic */ }
