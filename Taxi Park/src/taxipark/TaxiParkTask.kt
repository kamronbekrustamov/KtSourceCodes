package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        (allDrivers.filter { driver ->
            trips.none { trip -> driver.name == trip.driver.name }
        }).toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        (allPassengers.filter {passenger ->
            trips.count {trip -> trip.passengers
                    .any {it.name == passenger.name} } >= minTrips
        }).toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        (allPassengers.filter { passenger ->
            trips.count { trip -> trip.passengers
                    .any { it.name == passenger.name } && trip.driver.name == driver.name } > 1
        }).toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        (allPassengers.filter { passenger ->
            trips.count { trip -> trip.passengers
                            .any { it.name == passenger.name } && trip.discount == null } <
            trips.count { trip -> trip.passengers
                        .any { it.name == passenger.name } && trip.discount != null }
        }).toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if(trips.isEmpty()) return null
    val intervalsMap = trips.groupBy { it.duration / 10 }
    val (interval, v) = intervalsMap.maxBy { (_, value) -> value.size }!!
    return IntRange(interval * 10, interval * 10 + 9)
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if(trips.isEmpty()) return false
    val totalIncome = trips.sumByDouble { trip -> trip.cost }
    val individualIncomes = allDrivers.map { driver ->
        (trips.filter { driver.name == it.driver.name})
                .sumByDouble { trip -> trip.cost }}.toMutableList()
    individualIncomes.sort()
    individualIncomes.reverse()
    var sum = 0.0
    val driversCount = (allDrivers.size * 0.2).toInt()
    for(i in 0 until driversCount) {
        sum += individualIncomes[i]
    }
    return sum >= 0.8 * totalIncome
}