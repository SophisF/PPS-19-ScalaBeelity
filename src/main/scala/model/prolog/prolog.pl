position(X, Y).
cell(T, P, H, position(X, Y)).
range(MIN, MAX).
fitPosition(position(X, Y), FIT).

diff(V, range(V1, V2), O) :- V < V1, O is V1-V, !.
diff(V, range(V1, V2), O) :- V > V2, O is V-V2, !.
diff(V, range(V1, V2), 0).



/*
per ora fit e diff separate 
*/
fit(PARAM, range(MIN, MAX), FIT):- diff(PARAM, range(MIN, MAX), FIT).

calculateFit(cell(T, P, H, position(X, Y)), range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FIT) :- 
fit(T, range(T_MIN, T_MAX), T_FIT), fit(P, range(P_MIN, P_MAX), P_FIT), fit(H, range(H_MIN, H_MAX), H_FIT), FIT is T_FIT + P_FIT + H_FIT. 

getPosition(position(X1, Y1), position(X2, Y2), FIT1, FIT2, position(X, Y)) :- FIT1 > FIT2, X is X1, Y is Y1.
getPosition(position(X1, Y1), position(X2, Y2), FIT1, FIT2, position(X, Y)) :- FIT2 >= FIT1, X is X2, Y is Y2.

maxFit(cell(T1, P1, H1, position(X1, Y1)), cell(T2, P2, H2, position(X2, Y2)), range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), position(X, Y)) :- 
calculateFit(cell(T1, P1, H1, position(X1, Y1)), range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), F1), 
calculateFit(cell(T2, P2, H2, position(X2, Y2)), range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), F2),
getPosition(position(X1, Y1), position(X2, Y2), F1, F2, position(X, Y)), !.

%Ritorna un ris(pos(1,2), 1)
minPosFromTwo(fitPosition(P1, F1), fitPosition(P2, F2), P1, F1) :- F1 =< F2, !.
minPosFromTwo(fitPosition(P1, F1), fitPosition(P2, F2), P2, F2).

%Lista, MinTemp, Min
minPos([fitPosition(P1, F1)], fitPosition(P1, F1)).
minPos([fitPosition(P1, F1)|Y], Min) :- minPos(Y, fitPosition(P1, F1), Min).
minPos([fitPosition(P1, F1)], Min0, Min) :- minPosFromTwo(fitPosition(P1, F1), Min0, Min), !.
minPos([fitPosition(P1, F1)|Y], Min0, Min) :-
		 minPosFromTwo(fitPosition(P1, F1), Min0, MinTemp), minPos(Y, MinTemp, Min).


move([cell(T, P, H, POS1)], range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), POS).
move([cell(T, P, H, POS) | TAIL], range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), POS) :- 
	calculateFit(cell(T, P, H, POS), range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FIT),
	move(TAIL, range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FIT, POS).

move([cell(T, P, H, POS2)], range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FIT, POS).
move([cell(T, P, H, POS2) | TAIL], range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FIT, POS) :-
	write(TAIL),
	move(TAIL, range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FIT, POS2).
	


/*
move([cell(T, P, H, POS2) | TAIL], range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FIT, POS):-
	calculateFit(cell(T, P, H, POS2), range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FIT2),
	minPosFromTwo(fitPosition(POS2, FIT2), fitPosition(POS, FIT), NEW_POS, NEW_FIT), 
	move(TAIL, range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), NEW_FIT, NEW_POS).
	*/
	

/*

%THIS FILE REPRESENTS THE THEORY
pos(X, Y).
value(T, H, P).
cell(value(T, H, P), pos(X, Y)).
ris(pos(X, Y), O).

diff(V, V1, O) :- V < V1, O is V1-V, !.
diff(V, V1, O) :- V > V1, O is V-V1.

fit(value(T, H, P), cell(value(T1, H1, P1), pos(X, Y)), ris(pos(X,Y), O)) :-
	diff(T, T1, DT), diff(H, H1, DH), diff(P, P1, DP), O is DT+DH+DP.
%fit(value(10,2,7), cell(value(2,8,3), pos(2,2)), O).

%Ritorna un ris(pos(1,2), 1)
minPosFromTwo(ris(X, R), ris(X2, R2), O) :- R =< R2, O = ris(X, R), !.
minPosFromTwo(ris(X, R), ris(X2, R2), O) :- R > R2, O = ris(X2, R2).

%Lista, MinTemp, Min
minPos([ris(X, R)], ris(X, R)).
minPos([ris(X, R)|Y], Min) :- minPos(Y, ris(X, R), Min).
minPos([ris(X, R)], Min0, Min) :- minPosFromTwo(ris(X, R), Min0, Min), !.
minPos([ris(X, R)|Y], Min0, Min) :-
		 minPosFromTwo(ris(X, R), Min0, MinTemp), minPos(Y, MinTemp, Min).

getMinPos(ris(X, R), X).

%minPos([ris(pos(2,3),7), ris(pos(1,1), 1), ris(pos(1,1), 8)], O), getMinPos(O, X).
*/