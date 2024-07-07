grammar QML;

root : object+ ;
object : NAME object_part+ ;
object_part: '{' object_partin+ '}' | '{' '}';
object_partin: NAME ':' litr | object;
litr : NUMBER | STR;

STR : '"' .*? '"';
NAME : [a-zA-Z0-9_$]+;

NUMBER : [0-9]+ | '0x' [0-9]+ | [0-9] + '.' + [0-9]+ ;

LINEGAP : [ \t\r\n]+ -> skip;

COMMENT : '//' .*? '\n' -> channel(HIDDEN);
COMMENT_MULTI : '/*' .*? '*/' -> channel(HIDDEN);
