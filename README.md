# Project Name
Epsilon Investments

## Team Information

### Team Members
- Elias Alhazzaa
- Vincent Zheng
- Tom Xu
- Jerry Xu

---

## Project Description

### Domain

Finance

Stock market simulator for paper trade that allows individuals to get into trading 
in a risk-free environment. 

---

## User Stories

### User Story 1: Portfolio Navigation
The user wants to track the status of a/multiple stock market portfolio(s) via comparison of profit/loss, exposures, value,
sharpe ratio, beta index, treynor index, sortino ratio, jensen's alpha, transactions made, and date created for multiple 
running portfolios (should they choose to have multiple), and allow them to see day-to-day profit/loss on each portfolio. 

### User Story 2: Charts and Data (Jerry)
The user wants to search for securities and see individual statistics (current and historical pricing, candlestick pricing 
charts, relative strength index, moving averages, moving average convergence divergence, momentum, trend, volatility, 
volume, commodity channel index, moving average oscillator, mass index, price comparison, stochastic oscillator, bollinger 
bands, previous highs/lows, pivot points, on balance volume, accumulation/distribution indicator, average directional index, 
aroon indicator)about each stock market security, in a data visualization format (charts, line graphs) depending on if it's 
possible for that statistic to be visualized in a chart, and allowing them to compare various securities side-by-side. 

### User Story 3: News Access
The user wants to look at current world events and news, and access the internet for research purposes too, such as when 
looking for information on the particular company/security or for general world events. 

### User Story 4: Transactions
The user wants to make mock transactions on securities, involving different order types (market order, limit order, 
stop-loss order, stop-limit orders, fill-or-kill, trailing stop) and different position types (long, short, options; call and 
put), alongside various position presets (ex. a long and a call option simultaneously) to their liking. 

---

## Use Cases

### Use Case 1
Making a new portfolio

### Use Case 2
Deleting an existing portfolio

### Use Case 3
Comparing two or more portfolios via a specified metric

### Use Case 4
Seeing daily, weekly, monthly, yearly loss/profit of each portfolio

### Use Case 5
Seeing individual breakdowns of exposures for each portfolio, alongside the gains and losses from each security

### Use Case 6
Searching for individual securities

### Use Case 7
Seeing market price of individual stock market security

### Use Case 8
Checking historical prices from a set range with either a line or a candlestick chart

### Use Case 9
Side-by-side comparisons with various statistics of different stock market securities

### Use Case 10
Check for related news for potentially relevant world events

### Use Case 11
Get overall sentiment about a security

### Use Case 12
Find specific news and finance articles about various stocks

### Use Case 13
Find finance-related articles

### Use Case 14
Mock transactions with market orders

### Use Case 15
Mock transactions with specialized orders (limit orders, limit order, stop-loss order, stop-limit orders, fill-or-kill, trailing stop)

### Use Case 16
Mock transactions with long/short positions

### Use Case 17
Mock transactions with options positions

### Use Case 18
Mock transactions with position presets

## Use Case 19
Mock transactions with various amounts of one security

---

## Minimal Viable Product (MVP)

| Team Member | MVP Use Case |
|-------------|--------------|
| Tom | Use Case 1 |
| Tom | Use Case 2 |
| Tom | Use Case 4 |
| Tom | Use Case 5 |
| Jerry | Use Case 6 |
| Jerry | Use Case 7 |
| Jerry | Use Case 8 |
| Elias | Use Case 10 |
| Elias | Use Case 11 |
| Elias | Use Case 12 |
| Vincent | Use Case 14 |
| Vincent | Use Case 16 |
| Vincent | Use Case 19 |

---

## Entities

### Entity 1: Portfolio
TODO: Finish (confirm with Jerry)
Attributes: Net gain and loss day-to-day, aggregate profit/loss across entire timespan, other statistics
Methods: Getter methods, calculating technical indicators for portfolio, buying securities/options, selling securities/options, 

### Entity 2: Technical indicators
TODO: Finish (confirm with Jerry, not sure if this should be a whole entity or not or if it's just going to be a portfolio attribute)
Attributes: The indicator itself
Methods: Getter, getter with specific format of output

### Entity 3: Securities
TODO: Finish (confirm with Jerry)
Attributes: Qty, type/description, current value, value upon input, change in value
Methods: Getter for attributes, buying, selling

### Entity 4: Options 
TODO: Finish (confirm with Jerry)
Attributes: Qty, previous price, change in price, current price (so for longs buying and current price shorts same thing)
Methods: Getter for attributes, buying, selling, setting stop losses

---

## API Usage

Alpha Vantage:
https://www.alphavantage.co/documentation/

Used for accessing historical data about various securities. 

https://finnhub.io/docs/api/introduction

Used for searching about stocks/company/security news

---

## Design Diagrams

### UML Class Diagram


### Sequence Diagrams

---

## How to Run

[Instructions for running the program.]

---

## Notes

[Any additional project information.]
