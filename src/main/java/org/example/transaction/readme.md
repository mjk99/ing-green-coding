This package contains all needed classes for Transaction service. The code is maximally optimized for minimal latency. After running benchmarks following improvements were made:
- Both BigDecimal and Long (if the value represents smallest subunit of currency) were tested. Using Long was faster if the accounts were new or almost always new for every transaction. For small fixed amount of accounts and many transactions between them BigDecimal was faster. Therefore the decision should be made after analysing the average request. Now the BigDecimal is used because it's faster for cases similiar to small example provided. Worse performance for Long type might be caused by unoptimized deserializing. In tests the comma was removed with replaceFirst and then the string was parsed to Long.  
- BigDecimal is faster after setting scale.
- For the same reasons as in ATM service, TreeMap and PriorityQueue are not optimal, because of insert costs.

Time complexity O(n*log(n))