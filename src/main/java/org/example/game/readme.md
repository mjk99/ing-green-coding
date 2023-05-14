This problem is variation of 0-1 Knapsack problem and Packaging Bin problem. Solutions where only one bin was analyzed at once (greedy algorithm approach) were much slower - approximately two times slower for number of clans = 20_000.

Time complexity is O(n^2), because in worst case for n clans we need to create n bins and iterate through them.