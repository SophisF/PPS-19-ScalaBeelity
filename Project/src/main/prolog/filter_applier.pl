%%% Author: Paolo Baldini
%%% Apply a filter in a specific sub-section of a matrix
%%%
%%% Examples:
%%% 	apply([[1,5,7,4,1],[3,2,1,8,9],[5,6,7,8,9],[1,3,5,5,5],[6,7,8,9,10]], [[1,3,6],[1,4,8],[9,10,5]], 0, 0, O)
%%% 	O / [[1,15,42,4,1],[3,8,8,8,9],[45,60,35,8,9],[1,3,5,5,5],[6,7,8,9,10]]
%%%
%%% 	apply([[1,5,7,4,1],[3,2,1,8,9],[5,6,7,8,9],[1,3,5,5,5],[6,7,8,9,10]], [[1,3,6],[1,4,8],[9,10,5]], 1, 3, O)
%%% 	O / [[1,5,7,4,1],[3,2,1,8,27],[5,6,7,8,36],[1,3,5,45,50],[6,7,8,9,10]]

% concat two list
concat([], L, L).
concat([H], L, [H|L]).
concat([H|T], L, [H|O]) :- concat(T, L, O).



% apply
%		+M = matrix
%		+F = filter (e.g.: gaussian)
%		+Y = OPTIONAL, skip first Y rows
%		+X = OPTIONAL, skip first X columns
%		-O = output matrix
apply(M, F, Y, X, O) :- skiptop(M, Y, M1, M2), apply(M2, F, X, O1), concat_(M1, O1, O).
concat_([], L, L).
concat_(L, [[]], L).
concat_(L1, L2, O) :- concat(L1, L2, O).

% no operations
apply([[]], [[]], _, [[]]).
apply([[]], [[_|_]], _, [[]]).
apply([[H|T]], [[]], _, [[H|T]]).

% columns descent
apply([[MH|MT]], [[FH|FT]], X, [O]) :- apply([MH|MT], [FH|FT], X, O).
apply([[MH|MT]|MT1], [[FH|FT]], X, [O1|MT1]) :- apply([MH|MT], [FH|FT], X, O1).
apply([[MH|MT]|MT1], [[FH|FT]|FT1], X, [O1|O2]) :- apply([MH|MT], [FH|FT], X, O1), apply(MT1, FT1, X, O2).

% rows movement
apply([], [], []).
apply([], [_|_], []).																													% on out-of-bound not continue to calculate. Allow things like: [3], [0,2] -> [0]
apply([H|T], [], [H|T]).																											% [3,2,3] -> [3,2,3]
apply([MH|MT], [FH|FT], [OH|OT]) :- OH is MH * FH, apply(MT, FT, OT).					% M = [3,2,3], F = [0,1,3] -> [0,2,9]
apply(M, F, X, O) :- skipleft(M, X, M1, M2), apply(M2, F, O1), concat(M1, O1, O).	% M = [3,2,3], F = [0,1,3], X = 1 -> [3,0,3]



% skip first N row of matrix from top
skiptop([], _, [], []).
skiptop([[H|T]|T1], 0, [], [[H|T]|T1]).
skiptop([[H|T]|T1], N, [[H|T]|O1], O2) :- N1 is N - 1, skiptop(T1, N1, O1, O2).

% skip first N column of matrix from left
skipleft([], _, [], []).
skipleft([H|T], 0, [], [H|T]).
skipleft([H|T], N, [H|O1], O2) :- N1 is N - 1, skipleft(T, N1, O1, O2).
