#####

Transaction
    
    a sequence of database operations that satisfies the ACID properties (which can be perceived as a single logical operation on the data) 
    
    Atomicity
        an indivisible and irreducible series of database operations such that either all occurs, or nothing occurs.
        A guarantee of atomicity prevents updates to the database occurring only partially, which can cause greater problems than rejecting the whole series outright.
    Consistency
        the requirement that any given database transaction must change affected data only in allowed ways. 
        Any data written to the database must be valid according to all defined rules, including constraints, cascades, triggers, and any combination thereof.
    Isolation
        how or when[clarification needed] the changes made by one operation become visible to others. 
        ensures that concurrent execution of transactions leaves the database in the same state that would have been obtained if the transactions were executed sequentially.
    Durability
        guarantees that transactions that have committed will survive permanently. 
        usually means that completed transactions (or their effects) are recorded in non-volatile memory.[citation needed]
        

[To the top](#)

[CPA]: https://en.wikipedia.org/wiki/CAP_theorem
[ACID]: https://en.wikipedia.org/wiki/ACID
