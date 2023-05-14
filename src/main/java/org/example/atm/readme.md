This package contains all needed classes for ATM service.
The code is maximally optimized for minimal latency. After running benchmarks following decisions were made:
- using public fields instead of getters and setters
- instead of using separate DO for response entity (e.g. AtmDO), AtmDeserializer creates response directly from Task objects
- instead of using PriorityQueue or TreeMap for sorting the tasks simple HashMap was used and sorting was implemented from scratch. It's faster because these types have too much cost on insert and in the task we insert often but only need to sort once - at the end of processing the data.
- since the max "region" value is 9999, for more than 10K atms it might be better to use counting sort algorithm, but for small amounts of tasks/regions it doesn't improve performance significantly. Sorting of atms based on priority is done based on linear time sorting algorithm - counting sort. It improves performance significantly in this case, because range of priority value is small. 
- instead of removing duplicates at the end of processing it is more efficient to remove them in scope of one region
- using Short instead of Integer didn't improve nor worsen performance for any of Task fields.

Benchmarks were run with randomly generated data for 10000 tasks.

Ideas for further improvements:
- use arrays instead of lists and instead of Task object - very small performance gain possible if any but very hard to maintain
- deserialize and serialize methods could be improved by parsing the string based on number of letters in each field instead of checking if the field name matches expected one - it might be faster but also more error prone

Time complexity O(n*log(n))
