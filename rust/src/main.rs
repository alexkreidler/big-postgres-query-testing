use fallible_iterator::FallibleIterator;
use postgres::{Client, NoTls};

fn main() {
    let mut client = Client::connect(
        "postgresql://postgres:pgpass@0.0.0.0:6120/nyc-taxi-data",
        NoTls,
    )
    .unwrap();
    // let limit: i64 = 10;
    // let limit = "ALL";
    let none: Option<i64> = None;
    let mut it = client
        .query_raw(
            "SELECT id, vendor_id, store_and_fwd_flag, passenger_count FROM trips LIMIT $1;",
            std::iter::once(none),
        )
        .unwrap();

    let mut total = 0;

    while let Some(_row) = it.next().unwrap() {
        total += 1;
    }
    println!("Total rows: {}", total)
}
