package gr.hua.dit.compiler;

import java.io.*;
import java_cup.runtime.Symbol;

%%

%class Lexer
%unicode
%line
%column
%cup

%eofval{
    return createSymbol(Symbols.EOF);
%eofval}

%{
    private StringBuffer sb = new StringBuffer();

    private Symbol createSymbol(int type) {
        return new Symbol(type, yyline+1, yycolumn+1);
    }

    private Symbol createSymbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }
%}

delim =      [ \t\n]
ws    =      {delim}
l     =      [A-Za-z]
d     =      [0-9]
%%

"+"             { return createSymbol(Symbols.T_plus); }
"*"             { return createSymbol(Symbols.T_times); }
"("             { return createSymbol(Symbols.T_lpar); }
")"             { return createSymbol(Symbols.T_rpar); }

{d}+            { return createSymbol(Symbols.T_num, Integer.valueOf(yytext())); }

{ws}            {}
\'.*\n          {}
