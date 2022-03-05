# Note: the module name is psycopg, not psycopg3
import os
import psycopg2

connstr = os.environ.get("PG_CONNECTION_STRING")
print("Attempting to connect to:", connstr)

def main():
    # Connect to an existing database
    with psycopg2.connect(connstr) as conn:

        # Open a cursor to perform database operations
        with conn.cursor() as cur:

            cur.execute("""
                SELECT id, vendor_id, store_and_fwd_flag, passenger_count FROM trips;
                """)

            # # Pass data to fill a query placeholders and let Psycopg perform
            # # the correct conversion (no SQL injections!)
            # cur.execute(
            #     "INSERT INTO test (num, data) VALUES (%s, %s)",
            #     (100, "abc'def"))

            # # Query the database and obtain data as Python objects.
            # cur.execute("SELECT * FROM test")
            # cur.fetchone()
            # will return (1, 100, "abc'def")

            # You can use `cur.fetchmany()`, `cur.fetchall()` to return a list
            # of several records, or even iterate on the cursor
            total = 0
            for record in cur:
                total += 1

            print(total)

            # Make the changes to the database persistent
            # conn.commit()
main()