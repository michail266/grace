package gr.hua.dit.compiler;

import java.io.*;
import java_cup.runtime.Symbol;

%%

%class Lexer
%unicode
%line
%column
%cup

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
        public static final int T_char    = 23;
        public static final int T_comment = 24;
        public static final int T_colon = 25 ;
        public static final int T_semicolon = 26 ;
        public static final int T_dot = 27 ;
        public static final int T_coma = 28 ;
        public static final int T_OpBr = 29 ;
        public static final int T_ClBr = 30 ;
        public static final int T_OpCuBr = 31 ;
        public static final int T_ClCuBr = 32 ;
        public static final int T_BigEq = 33 ;
        public static final int T_SmEQ = 34 ;
        public static final int T_Bigger = 35 ;
        public static final int T_Smaller = 36;
        public static final int T_Insert = 37 ;
        public static fianl int T_hashtag = 28 ;
};


%

delim =      [ \t\r\n]
ws    =      {delim}+
l     =      [A-Za-z][A-Za-z0-9_-]*
d     =      [0-9]
%%



\$\$([^$]|\$[^$])*\$\$   { /**/ }
\$[^\n]*                 { /* skip rest of this line */ }


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
":"             { return Token.T_colon;}
"#"             { return Token.T_hashtag;}
";"             { return Token.T_semicolon;}
","             { return Token.T_coma; }
"."             { return Token.T_dot; }
"["             { return Token.T_OpBr; }
"]"             { return Token.T_ClBr; }
"{"             { return Token.T_OpCuBr; }
"}"             { return Token.T_ClCuBr; }
"<="            { return Token.T_SmEQ; }
">="            { return Token.T_BigEq; }
"<-"            { return Token.T_Insert; }
"<"             { return Token.T_Smaller; }
">"             { return Token.T_Bigger; }
"="             { return Token.T_eq; }
"("             { return Token.T_lpar; }
")"             { return Token.T_rpar; }
"+"             { return Token.T_plus; }
"-"             { return Token.T_minus; }
"*"             { return Token.T_times; }
"div"           { return Token.T_div; }
"mod"           { return Token.T_mod; }

// string?literal: double?quoted, with simple escapes
  \"([^\"\\]|\\.)*\"    { return Token.T_char; }

  // char?literal (if you still want this):
  \'([^\'\\]|\\.)\'     { return Token.T_char; }


{l}+           { return Token.T_id; }
{d}+           { return Token.T_num; }

{ws}            {}
\.*\n          {}