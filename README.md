# Currency Exchanger
 
## Architecture

App is divided into three architectural layers: UI, Domain and Data

UI layer consists of Composable functions and ViewModel. Top level Composable is `ExchangeScreen` which layouts other smaller UI elements' Composables. Most of UI logic for this app is defined in `ExchangeViewModel`.

Domain layer consists of various business logic components:
- `BalancesManager` - updates balances after each conversion operation (checking if balance has enough money, etc.). It accepts a `BalanceRepository` as dependency. It also exposes current balances from repository for UI layer.
- `CommissionFeeManager` - calculates conversion commission fee based on various commission fee calculation rules.
- `ConversionManager` - does the conversion operation. It is dependant on `CommissionFeeManager`.
- `ExchangeRatesSyncManager` - takes care of syncing exchange rates every 60 seconds (duration can be modified). It accepts `ExchangeRatesRepository` as dependency. It also exposes current exchange rates cached in repository for UI layer.

Data layer consists of:
- Exchange rates API
- Balances and cached rates Room database tables
- Data models which are mapped from API or Room entities and exposed from Data layer to other layers in app.
- Repository classes responsible of communicating with various data sources (e.g. API, Room).

Communication between layers is mostly implemented using `Kotlin Flow`. In UI layer required flows from other layers are combined into single `StateFlow` for consumption by UI elements.

## Some additional notes

*Tests*: Not sure what was the requirement for test coverage in this task. 
If I am not mistaken there was no distinct requirement for app being fully covered with tests, though evaluation criteria included "testability", but that means whether system is testable, not being covered with tests.
Anyways, I did cover domain layer with some basic tests, but could be that you folks expect more test coverage.
