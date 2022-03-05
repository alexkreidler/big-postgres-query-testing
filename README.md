# Testing large Postgres queries

Tested a query with 4 columns: 2 text and 2 numeric, and 5.8 million rows of the [NYC Taxi dataset](https://github.com/alexkreidler/nyc-taxi-data), across Java, Python, and Rust versions.

With an eye towards converting them into Apache Arrow Tables.

## Results

### Java

Very limited:
```
time java -cp ./target/JDBC-PostgreSQL-SimpleExample-0.0.1-SNAPSHOT.jar Main
size: 500

________________________________________________________
Executed in  212.82 millis    fish           external 
   usr time  413.61 millis  330.00 micros  413.28 millis 
   sys time   52.17 millis   44.00 micros   52.12 millis 
```

Without result set size:
```
time java -cp ./target/JDBC-PostgreSQL-SimpleExample-0.0.1-SNAPSHOT.jar Main
size: 5826207

________________________________________________________
Executed in    8.86 secs   fish           external 
   usr time   60.02 secs  300.00 micros   60.02 secs 
   sys time    1.55 secs  217.00 micros    1.55 secs 
```

With result set size = 500,000:
```
time java -cp ./target/JDBC-PostgreSQL-SimpleExample-0.0.1-SNAPSHOT.jar Main
size: 5826207

________________________________________________________
Executed in    9.77 secs   fish           external 
   usr time   58.45 secs  380.00 micros   58.45 secs 
   sys time    2.25 secs   52.00 micros    2.25 secs 
```

### PSQL
```
 time psql $PG_CONNECTION_STRING -c 'SELECT id, vendor_id, store_and_fwd_flag, passenger_count FROM trips;' >/dev/null

________________________________________________________
Executed in    8.96 secs   fish           external 
   usr time    4.42 secs    0.00 micros    4.42 secs 
   sys time    0.27 secs  301.00 micros    0.27 secs 
```

### Python
```
time python ./query.py
Attempting to connect to: postgresql://postgres@0.0.0.0:6120/nyc-taxi-data
5826207

________________________________________________________
Executed in    3.67 secs   fish           external 
   usr time  1448.59 millis  286.00 micros  1448.30 millis 
   sys time  159.13 millis   22.00 micros  159.11 millis 
```

### Rust
```
time ./target/release/rust-postgres
Total rows: 5826207

________________________________________________________
Executed in    3.77 secs   fish           external 
   usr time    2.10 secs  496.00 micros    2.10 secs 
   sys time    0.06 secs    0.00 micros    0.06 secs 
```

Both Python and Rust dropped down after immediate repeats to around 2.4 seconds, with rust being about .1 second faster.

## Conclusions

Java is not great for this.

I read that JDBC is a more performant driver than ODBC and (maybe implied that it was better than) some Python stuff,
but that may be for individual updates, eg latency, rather than with large bulk queries.

See https://stackoverflow.com/a/58984748

## TODOs

maybe Golang?