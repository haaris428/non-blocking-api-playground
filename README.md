# non-blocking-api-playground
Playground to play with blocking vs non blocking APIs 


## Build

You can build this using

```
./gradlew clean build
```

You should be able to run tests from intellij, but in case command line setup
is needed, you can run through gradle commands. TestNG tests are not added to the 
build so you will have to pass a property 
`./gradlew clean build -PfunctionalTests=True`

## Running App And Tests

To run springboot apps you can use gradle

for non-blocking-api

```
./gradlew :non-blocking-api:bootRun
```

for blocking-api

```
./gradlew :blocking-api:bootRun
```
