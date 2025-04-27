package gr.hua.dit.compiler;

import java.io.*;
import java_cup.runtime.Symbol;

%%

%class Lexer
%unicode
%integer
%line
%column
%{

public static class Token {
        public static final int T_if      = 1;
        public static final int T_then    = 2;
        public static final int T_else    = 3;
        public static final int T_not     = 4;
        public static final int T_nothing = 5;
        public static final int T_or      = 6;
        public static final int T_ref     = 7;
        public static final int T_do      = 8;
        public static final int T_id      = 9;
        public static final int T_num     = 10;
        public static final int T_lpar    = 11;
        public static final int T_rpar    = 12;
        public static final int T_plus    = 13;
        public static final int T_minus   = 14;
        public static final int T_times   = 15;
        public static final int T_div     = 16;
        public static final int T_mod     = 17;
        public static final int T_eq      = 18;
        public static final int T_and     = 19;
        public static final int T_fun     = 20;
        public static final int T_return  = 21;
        public static final int T_var     = 22;

};


%}

delim =      [ \t\r\n]
ws    =      {delim}+
l     =      [A-Za-z][A-Za-z0-9_-]*
d     =      [0-9]
%%

"if"            { return Token.T_if; }
"then"          { return Token.T_then; }
"else"          { return Token.T_else; }
"and"           { return Token.T_and; }
"do"            { return Token.T_do; }
"not"           { return Token.T_not; }
"or"            { return Token.T_or; }
"ref"           { return Token.T_ref; }
"return"        { return Token.T_return; }
"var"           { return Token.T_var; }
"nothing"       { return Token.T_nothing; }
"fun"           { return Token.T_fun; }



"="             { return Token.T_eq; }
"("             { return Token.T_lpar; }
")"             { return Token.T_rpar; }
"+"             { return Token.T_plus; }

"*"             { return Token.T_times; }
"div"           { return Token.T_div; }
"mod"           { return Token.T_mod; }

{l}+           { return Token.T_id; }
{d}+           { return Token.T_num; }
"-"             { return Token.T_minus; }
{ws}            {}
\'.*\n          {}
