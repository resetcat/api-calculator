# api-calculator
---
### Project Description
1. Java spring project that takes API calls and makes calculations depending on the input type.
2. authorization is done through header api key
3. all returns are in json format
4. Also has a country list in alphabetical order

---

### Requirements:
Copy this project: `git@github.com:resetcat/api-calculator.git`

---
### Launch using docker
1. Have docker installed on your system. You can download it here https://www.docker.com/
2. Locate the root folder, open console and create docker file by entering in your console:
```text
    docker build -t api-calculator .
```
3. And run the app with command:
```text
    docker run -p 8080:8080 -t api-calculator
```
---
### Launch from console
1. Have java installed on your system. You can download it here https://www.java.com/download/
2. Has Apache Maven installed on your system. You can download it from the official website at https://maven.apache.
   org/download.cgi <br />
3. Navigate to the project directory in the console <br />
4. Run the project using the command:
```
    mvn spring-boot:run
```
---

### Launch from your IDE
You can also run the Spring Boot application from an IDE that supports Apache Maven, such as IntelliJ IDEA,
Eclipse, or NetBeans. To do this, you can import your Maven-based Spring Boot project into the IDE and use the IDE's built-in support for Maven to run the application.

---
### Endpoint description
1. `http://localhost:8080/calculator/task1` send a get request with your `json` body <br /> example:
```json
[
   {
      "x": 4,
      "y": 5,
      "operation": "*"
   }
]
 ``` 
return should be like this:
```json
[
   20
]
```
2. `http://localhost:8080/calculator/task2` send a get request with your `json` body <br /> example:
```json
[
   "1+1+1+1","5+5","1*2*3*4*5"
]
```
return would be:
```json
[
   4,
   10,
   120
]
```
3. `http://localhost:8080/calculator/task3` send a get request with your `json` body <br /> example:
```json
[
   [1,2,3,5,4],[2,6,3],[2,-4,10,11,2]
]
```
return:
```json
[
  {
    "biggest": 5,
    "smallest": 1
  },
  {
    "biggest": 6,
    "smallest": 2
  },
  {
    "biggest": 11,
    "smallest": -4
  }
]
```
4. `http://localhost:8080/countries` send a get request, and you will receive a list of countries in alphabetical order:
```json
{
   "countries": [
      "Afghanistan",
      "Albania",
      "Algeria",
      ...
   ]
}
```


