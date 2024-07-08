grammar QML;

root: number | primitive | imp_call;

number: INT | FLOAT | BOOL;

ver_part: number | number '.';
ver: ver_part+;

imp_call: 'import ' obj_call ' ' ver;

obj_call_pth: OBJECT | OBJECT '.';
obj_call: obj_call_pth+;
obj_call_arg: primitive | primitive ',';
obj_call_args: obj_call_arg+;
obj_argcall: obj_call '(' ')' | obj_call '(' obj_call_args ')';

blk_code: '{' '}' | '{' primitive '}';

primitive
    : number
    | STR
    | obj_argcall
    | obj_call
    | primitive EQU primitive
    | primitive EQU_IND primitive
    | primitive BIGGER primitive
    | primitive SMALLER primitive
    | primitive NSMALLER primitive
    | primitive NBIGGER primitive
    | primitive AND primitive
    | primitive BAND primitive
    | primitive OR primitive
    | primitive BOR primitive
    | NOR primitive
    | BNOR primitive
    | primitive INLINE_IF_ST primitive INLINE_IF_ED primitive;

GAP: [ \t\r\n] -> skip;

INT: [0-9]+ | '0x' [0-9a-fA-F]+;
FLOAT: [0-9]+ '.' [0-9]+;
BOOL: 'true' | 'false';
EQU: '==';
EQU_IND: '===';
BIGGER: '>';
SMALLER: '<';
NSMALLER: '>=';
NBIGGER: '<=';

AND: '&&';
BAND: '&';
OR: '||';
BOR: '|';
NOR: '!';
BNOR: '~';

INLINE_IF_ST: '?';
INLINE_IF_ED: ':';

STR: '"' .*? '"';
OBJECT: [0-9A-Za-z]+;

COMMENT: '//' .*? '\n' -> channel(HIDDEN);
COMMENT_MULTI: '/*' .*? '*/' -> channel(HIDDEN);