This maven project contains API test suite for below endpoints-
1. satellites/[id]/positions
2. satellites/[id]/tles

Structure-
- Tests are designed using Cucumber BDD and REST Assured Java library.
- This project is a standard maven based Java project including src folder and pom.xml
- Cucumber Feature files are inside ./resources/Features
- /src/test/java contains Step Definitions, DTOs and supporting util classes.
- Tests can be run with unit testing framework TESTNG
 

Gaps-
Endpoint specification for satellites/[id]/positions says endpoint supports upto 10 timestamps in the request. However no error is returned when timestamps exceeds 10.
Hence could not add validation test for same.

