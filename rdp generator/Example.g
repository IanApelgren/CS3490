a
c
d
b
e
%%
S
B
D
F
G
%%
S : a B c
S : D e F
B : a b
B : b a
D : d B d
F : c G
G : c G
%%
S : a d : EOF_TOK : no
B : a b : c d : no
D : d : e : no
F : c : EOF_TOK : no
G : c : EOF_TOK : yes
%%

