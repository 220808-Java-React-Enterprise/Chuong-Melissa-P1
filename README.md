# P1 Reimbursement

## Project Description

Melissa Clark and John Nguyen created a Reimbursement System that allows multiple users to log in with different credentials and receive reimbursement for costs they have accrued through their company.  Using JWT we are able to give each user different permissions accourding to their roles as shown below.  

### Project Management
- Agile Methodology

### Frontend
 - Postman API Services

### Backend
- Java 8
- Tomcat
- Maven
- Postgres

### Devops
- Github
- Jenkins
- AWS

##### Relational Data Model
![Relational Model](https://github.com/220207-java-enterprise/assignments/blob/main/foundations-project/imgs/ERS%20Relational%20Model.png)

##### Reimbursement Types
Reimbursements are to be one of the following types:
- LODGING
- TRAVEL
- FOOD
- OTHER

##### System Use Case Diagrams
![System Use Case Diagrams](https://raw.githubusercontent.com/220207-java-enterprise/assignments/main/foundations-project/imgs/ERS%20Use%20Case%20Diagram.png)

##### Reimbursment Status State Flow
![Reimbursment Status State Flow](https://raw.githubusercontent.com/220207-java-enterprise/assignments/main/foundations-project/imgs/ERS%20State%20Flow%20Diagram.png)

### Functionalalities

- An new employee or new finance manager can request registration with the system
- An admin user can approve or deny new registration requests
- The system will register the user's information for payment processing
- A registered employee can authenticate with the system by providing valid credentials
- An authenticated employee can view and manage their pending reimbursement requests
- An authenticated employee can view their reimbursement request history (sortable and filterable)
- An authenticated employee can submit a new reimbursement request
- An authenticated finance manager can view a list of all pending reimbursement requests
- An authenticated finance manager can view a history of requests that they have approved/denied
- An authenticated finance manager can approve/deny reimbursement requests
- An admin user can deactivate user accounts, making them unable to log into the system
- An admin user can reset a registered user's password
- Authenticated employees are able to upload an receipt image along with their reimbursement request

### Creators: John Nguyen & Melissa Clark
