grammar QML;

class : 'class';

litr : NUMBER | STR;

STR  : '"' .*? '"';

NUMBER : [0-9]+ | [0-9] + '.' + [0-9]+ ;

LINEGAP  : [ \t\r\n]+ -> skip;

COMMENT : '//' .*? '\n' -> channel(HIDDEN);
COMMENT_MULTI : '/*' .*? '*/' -> channel(HIDDEN);
