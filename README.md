# Overview
The application is the beginning of a consolidated trading platform.  The end product will interface with existing trade platforms (such as Robinhood), stream data in, and perform automated trades.

As this software is being developed, there are design decisions that may not be the best (such as more monolithic vs microservice).  These concerns will be addressed as the software matures over time.

I am also recording all of the coding and uploading to my [YouTube channel](https://www.youtube.com/channel/UCz01y1iZGnSbk7RvTb6Lz6A).  Software developers can join in and recreate this same platform step by step, including the AWS infrastructure.

# Future Items
[] Fill out Robinhood integrations
    [] View accounts and positions
    [] Submit Buy order
    [] Submit Sell order
[] Integrate with other trading platforms
[] Stream live data
[] Implement basic trading algorithm

# Running Locally (Can see how via YouTube channel videos)
- Set up AWS tokens in run configurations
- Run via IDE or command line (passing in AWS tokens)

# Tech Debt
- This is intentionally designed as more of a monolithic application, with things such as the front end and back end deployed as a bundle.  This is to save on costs of AWS EC2 instances, Global Load Balancers, etc.
- Convert RestTemplate to newest Spring REST client.

# Tech Stack
- Java with Spring Boot
- AWS log streaming
- DynamoDB
- AWS Code Pipeline
- Angular

