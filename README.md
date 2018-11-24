# Checkstyle Checks

Extended set of checks for checkstyle. Checks cover some specific usage patterns and extend error reporting.

## Current Status

Now this project is the prototype implementation of required checks. This project does not contain any IDE plugins,
only checks implementation.
 
## Rinnging Checks 
 
Build this project: 

```shell
mvn package
```

Build artifact will be placed to target directory. To start checkstyle including these checks:

```shell
java -cp checkstyle-${VERSION}-all.jar:target/checkstyle-checks-0.0.1-SNAPSHOT.jar \
  com.puppycrawl.tools.checkstyle.Main \
  -c config.xml \
  src/
```
