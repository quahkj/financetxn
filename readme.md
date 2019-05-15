Please install gradle build tool and Java 8 first before build and run this application.

1) Clone this repo by executing 'git clone https://github.com/quahkj/financetxn.git'

2) cd to financetxn directory

3) Execute 'gradlew build' to run all unit test cases

4) Execute 'gradlew executablejar' to build the executable jar

5) Execute 'java -jar build/libs/financetxn.jar testfile.csv' to run the application

6) Use below inputs:
   accountId: ACC334455
   from: 20/10/2018 12:00:00
   to: 20/10/2018 19:00:00

There is another test file testfile2.csv that covers another test cases to try out.

Assumption:
1) Input file and records are all in a valid format.

2) Transaction are recorded in order.

3) All inputs to the application are entered correctly. There is no input validation checking due to time constraint.