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
    private Symbol createSymbol(int type) {
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }

    private Symbol createSymbol(int type, Object value) {
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }
%}

delim =      [ \t\r\n]
ws    =      {delim}+
l     =      [A-Za-z][A-Za-z0-9_-]*
d     =      [0-9]
%%

"if"            { return createSymbol(Symbols.T_if); }
"then"          { return createSymbol(Symbols.T_then); }
"else"          { return createSymbol(Symbols.T_else); }
"and"           { return createSymbol(Symbols.T_and); }
"do"            { return createSymbol(Symbols.T_do); }
"not"           { return createSymbol(Symbols.T_not); }
"or"            { return createSymbol(Symbols.T_or); }
"ref"           { return createSymbol(Symbols.T_ref); }
"return"        { return createSymbol(Symbols.T_return); }
"var"           { return createSymbol(Symbols.T_var); }
"nothing"       { return createSymbol(Symbols.T_nothing); }
"fun"           { return createSymbol(Symbols.T_fun); }
":"             { return createSymbol(Symbols.T_colon); }
";"             { return createSymbol(Symbols.T_semicolon); }
","             { return createSymbol(Symbols.T_coma); }
"."             { return createSymbol(Symbols.T_dot); }
"["             { return createSymbol(Symbols.T_OpBr); }
"]"             { return createSymbol(Symbols.T_ClBr); }
"{"             { return createSymbol(Symbols.T_OpCuBr); }
"}"             { return createSymbol(Symbols.T_ClCuBr); }
"<="            { return createSymbol(Symbols.T_SmEQ); }
">="            { return createSymbol(Symbols.T_BigEq); }
"<-"            { return createSymbol(Symbols.T_Insert); }
"<"             { return createSymbol(Symbols.T_Smaller); }
">"             { return createSymbol(Symbols.T_Bigger); }
"="             { return createSymbol(Symbols.T_eq); }
"("             { return createSymbol(Symbols.T_lpar); }
")"             { return createSymbol(Symbols.T_rpar); }
"+"             { return createSymbol(Symbols.T_plus); }
"-"             { return createSymbol(Symbols.T_minus); }
"*"             { return createSymbol(Symbols.T_times); }
"div"           { return createSymbol(Symbols.T_div); }
"mod"           { return createSymbol(Symbols.T_mod); }

// String literal: double-quoted, with simple escapes
\"([^\"\\]|\\.)*\"    { return createSymbol(Symbols.T_char); }

// Character literal
\'([^\'\\]|\\.)\'     { return createSymbol(Symbols.T_char); }

{l}+           { return createSymbol(Symbols.T_id, yytext()); }
{d}+           { return createSymbol(Symbols.T_num, Integer.valueOf(yytext())); }

{ws}            {}
\.*\n          {}