**How to build:**
use Maven:

        mvn clean install 

**How to run:**

       docker-compose up -d

**Access urls:**

        API doc in json format: http://localhost:8082/v2/api-docs
        API doc in human-readable format: http://localhost:8082/swagger-ui/

**Example of requests:**

*Create new employee*

      *curl -X POST 'localhost:8082/employee' -H 'Content-Type: application/json' -d '
        { "firstName": "ZZZ",
          "lastName": "QQQ",
          "age": 101 }'*

*Change employee status*

       *curl -d "employeeId=<employee_id>&status=<DESIRED_STATUS>" localhost:8082/changeStatus*