position(X, Y).
cell(T, P, H, position(X, Y)).
range(MIN, MAX).
fitPosition(position(X, Y), FIT).

diff(V, range(V1, V2), O) :- V < V1, O is V1-V, !.
diff(V, range(V1, V2), O) :- V > V2, O is V-V2, !.
diff(V, range(V1, V2), 0).

fit(PARAM, range(MIN, MAX), FIT):- diff(PARAM, range(MIN, MAX), FIT).

calculateFit(cell(T, P, H, position(X, Y)), range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), fitPosition(position(X, Y),FIT)) :-
	fit(T, range(T_MIN, T_MAX), T_FIT), fit(P, range(P_MIN, P_MAX), P_FIT), fit(H, range(H_MIN, H_MAX), H_FIT), FIT is T_FIT + P_FIT + H_FIT.

minPosFromTwo(fitPosition(P1, F1), fitPosition(P2, F2), fitPosition(P1, F1)) :- F1 =< F2, !.
minPosFromTwo(fitPosition(P1, F1), fitPosition(P2, F2), fitPosition(P2, F2)).

move([cell(T, P, H, POS)], range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FitPos) :-
	calculateFit(cell(T, P, H, POS), range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FitPos).

move([cell(T, P, H, POS) | TAIL], range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FitPos) :-
	calculateFit(cell(T, P, H, POS), range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FitPosTemp),
	move(TAIL, range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FitPosTemp, FitPos).

/*Se la coda ha solo una testa*/
move([cell(T, P, H, POS)], range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FitPosTemp, FitPos):-
	calculateFit(cell(T, P, H, POS), range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FitCell),
	minPosFromTwo(FitCell, FitPosTemp, FitPos), !.

move([cell(T, P, H, POS) | TAIL], range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FitPosTemp, FitPos):-
	calculateFit(cell(T, P, H, POS), range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), FitCell),
	minPosFromTwo(FitCell, FitPosTemp, Temp),
	move(TAIL, range(T_MIN, T_MAX), range(P_MIN, P_MAX), range(H_MIN, H_MAX), Temp, FitPos).

getPos(fitPosition(P, F), P).
