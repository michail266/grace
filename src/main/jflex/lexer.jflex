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
        public static final int T_if = 1;
        public static final int T_then = 2;
        public static final int T_else = 3;
        public static final int T_begin = 4;
        public static final int T_end = 5;
        public static final int T_for = 6;
        public static final int T_do = 7;
        public static final int T_let= 8;
        public static final int T_print = 9;
        public static final int T_id = 10;
        public static final int T_num = 11;
        public static final int T_lpar = 12;
        public static final int T_rpar = 13;
        public static final int T_plus = 14;
        public static final int T_minus = 15;
        public static final int T_times = 16;
        public static final int T_div   = 17;
        public static final int T_eq    = 18;
};


%}

delim =      [ \t\r\n]
ws    =      {delim}+
l     =      [A-Za-z]
d     =      [0-9]
%%

"if"            { return Token.T_if; }
"then"          { return Token.T_then; }
"else"          { return Token.T_else; }
"begin"         { return Token.T_begin; }
"end"           { return Token.T_end; }
"for"           { return Token.T_for; }
"do"            { return Token.T_do; }
"let"           { return Token.T_let; }
"print"         { return Token.T_print; }

"="             { return Token.T_eq; }
"("             { return Token.T_lpar; }
")"             { return Token.T_rpar; }
"+"             { return Token.T_plus; }
"-"             { return Token.T_minus; }
"*"             { return Token.T_times; }
"/"             { return Token.T_div; }

{l}+            { return Token.T_id; }
{d}+            { return Token.T_num; }

{ws}            {}
\'.*\n          {}
