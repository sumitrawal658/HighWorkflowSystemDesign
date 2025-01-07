Core Design Principle: Microservices Architecture
**1. Decomposition**
Objective: Decompose the application into independent, modular services to achieve scalability, maintainability, and fault isolation.
Approach:
Identify business domains and map them to specific services. Each service encapsulates the logic for a particular domain.
Example decomposition:
User Management Service: Handles user authentication, authorization, profile management, and roles.
Order Processing Service: Manages order creation, updates, tracking, and cancellations.
Payment Gateway Service: Handles payment processing, refund operations, and integration with third-party payment providers.
Notification Service: Sends notifications via email, SMS, or push notifications based on triggers from other services.
**2. Service Characteristics**
Loosely Coupled:
Services are independent and interact via APIs (e.g., REST, gRPC) or event streams (e.g., Kafka).
Changes in one service do not require modifications in others unless there's a direct dependency.
Independently Deployable:
Each service is packaged and deployed independently, enabling frequent updates without downtime for the entire system.
Resilient:
Use retry mechanisms, circuit breakers, and service timeouts to handle failures gracefully.
Domain Ownership:
Each service owns its domain logic and data, preventing shared dependencies.
**3. Database Per Service**
Rationale:
To maintain loose coupling, each microservice owns its database, ensuring that services can evolve independently.
This eliminates dependencies caused by a shared schema, making it easier to update or replace services.
Examples:
User Management Service:
Relational database like PostgreSQL for transactional user data (e.g., usernames, passwords, roles).
Order Processing Service:
Relational database for transactional order data (e.g., order status, timestamps).
NoSQL database like MongoDB for flexible storage of order details, such as product configurations.
Payment Gateway Service:
Encrypted relational database to store payment metadata (e.g., payment IDs, status, amount).
Notification Service:
Use a message queue (e.g., RabbitMQ) to manage outgoing notifications.
Optional: In-memory database like Redis for tracking undelivered messages.
Scalability Considerations:
Use sharding or partitioning for large datasets within each database.
Enable read replicas for read-heavy workloads.
Advantages of This Approach
Scalability:
Each service can scale horizontally or vertically based on its specific workload. For example:
The Payment Gateway Service might need to handle peak loads during flash sales.
The Notification Service can scale independently for bulk email or SMS campaigns.
Fault Isolation:
Failures in one service do not propagate to others. For instance, if the Payment Gateway Service is down, the User Management Service remains unaffected.
Technology Independence:
Each service can use the most suitable database or technology stack. For instance:
PostgreSQL for User Management (ACID compliance).
DynamoDB for the Order Service (high throughput and scalability).
Easier Maintenance:
Developers can focus on a single service, making debugging, testing, and upgrades more manageable.
Enhanced Security:
Each database is scoped to its service, reducing attack surfaces and minimizing data breaches.
has context menu

![img_5.png](img_5.png)