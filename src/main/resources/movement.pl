/*
    Prolog code to implement the movement of a colony. Given a list of reachable cells and the expression ranges for
    temperature, pressure and humidity of the queen, it finds the cell that fit best these ranges. So the new position
    of the queen (and therefore of the colony) is only the position of the cell found.
*/

% A generic position.
position(X, Y).

% A generic cell of the environment, described by a temperature, a pressure, a humidity and a position.
cell(T, P, H, position(X, Y)).

% A generic range.
range(Min, Max).

% A wrapper of a position and the respective fit.
positionWithFit(position(X, Y), Fit).

% Calculate the difference between a value and a range. Return 0 if the value is in the range.
diff(V, range(V1, V2), O) :- V < V1, O is V1-V, !.
diff(V, range(V1, V2), O) :- V > V2, O is V-V2, !.
diff(V, range(V1, V2), 0).

% Calculate the penalty of a range for a specific environmental parameter.
fit(Param, range(Min, Max), Fit):- diff(Param, range(Min, Max), Fit).

% Calculate the whole penalty of a colony for a given cell.
calculateFit(cell(T, P, H, position(X, Y)), range(T_Min, T_Max), range(P_Min, P_Max), range(H_Min, H_Max), positionWithFit(position(X, Y),Fit)) :-
	fit(T, range(T_Min, T_Max), T_Fit), fit(P, range(P_Min, P_Max), P_Fit), fit(H, range(H_Min, H_Max), H_Fit), Fit is T_Fit + P_Fit + H_Fit.

% Given two positionWithFit, calculate which position fit better the colony.
bestFit(positionWithFit(P1, F1), positionWithFit(P2, F2), positionWithFit(P1, F1)) :- F1 =< F2, !.
bestFit(positionWithFit(P1, F1), positionWithFit(P2, F2), positionWithFit(P2, F2)).

% Given a list of environment's cells, find the cell with best fit for the colony.
move([cell(T, P, H, Pos)], range(T_Min, T_Max), range(P_Min, P_Max), range(H_Min, H_Max), FitPos) :-
	calculateFit(cell(T, P, H, Pos), range(T_Min, T_Max), range(P_Min, P_Max), range(H_Min, H_Max), FitPos), !.

move([cell(T, P, H, Pos) | Tail], range(T_Min, T_Max), range(P_Min, P_Max), range(H_Min, H_Max), FitPos) :-
	calculateFit(cell(T, P, H, Pos), range(T_Min, T_Max), range(P_Min, P_Max), range(H_Min, H_Max), FitPosTemp),
	move(Tail, range(T_Min, T_Max), range(P_Min, P_Max), range(H_Min, H_Max), FitPosTemp, FitPos).

move([cell(T, P, H, Pos)], range(T_Min, T_Max), range(P_Min, P_Max), range(H_Min, H_Max), FitPosTemp, FitPos):-
	calculateFit(cell(T, P, H, Pos), range(T_Min, T_Max), range(P_Min, P_Max), range(H_Min, H_Max), FitCell),
	bestFit(FitCell, FitPosTemp, FitPos), !.

move([cell(T, P, H, Pos) | Tail], range(T_Min, T_Max), range(P_Min, P_Max), range(H_Min, H_Max), FitPosTemp, FitPos):-
	calculateFit(cell(T, P, H, Pos), range(T_Min, T_Max), range(P_Min, P_Max), range(H_Min, H_Max), FitCell),
	bestFit(FitCell, FitPosTemp, Temp),
	move(Tail, range(T_Min, T_Max), range(P_Min, P_Max), range(H_Min, H_Max), Temp, FitPos).

% Given a positionWithFit return the components of the position.
getPosition(positionWithFit(position(X, Y), F), X, Y).

