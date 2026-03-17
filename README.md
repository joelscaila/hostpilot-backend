#  HostPilot Backend — MVP

Modular backend for an **intelligent conversational agent** designed for rental properties. Includes language detection, intent classification, dynamic templates, contextual memory, and AI‑generated responses.

> **Note:** In this initial MVP phase, the project uses **Ollama locally** for response generation.  
> The architecture is designed to allow an **easy migration to OpenAI, Azure OpenAI, or any cloud provider** without structural changes.

---

##  Main Features

###  Intelligent Conversational Agent
- Automatically detects the user's language (ES, EN, FR, ZH…)
- Classifies message intent (wifi, check‑in, house rules, etc.)
- Uses predefined templates when a known intent is detected
- Falls back to AI generation when no template is available
- Translates the final response to the user's language

###  Property Management
- Property information loaded from the database
- Automatic DTO mapping
- JPA repository with in‑memory H2 for the MVP

###  AI Integration (Ollama → OpenAI‑ready)
- Currently: Local Ollama for fast, cost‑free development  
- Production‑ready for: OpenAI, Azure OpenAI, Groq, DeepSeek  
- Switching providers is as simple as replacing the client in `AiService`

###  Complete Test Suite
- Unit tests for:
  - IntentService  
  - LanguageService  
  - TemplateService  
  - AgentService (full pipeline)

---

##  Project Structure

src/  
 ├── main/java/com/hostpilot  
 │    ├── controller/ → REST endpoints  
 │    ├── dto/ → Input/output models  
 │    ├── service/ → Business logic  
 │    ├── ai/ → AI client (Ollama / OpenAI‑ready)  
 │    ├── intent/ → Intent detection  
 │    ├── template/ → Intent templates  
 │    ├── model/ → JPA entities  
 │    ├── repository/ → Spring Data repositories  
 │    └── config/ → WebClient configuration  
 │  
 └── test/java/com/hostpilot  
      ├── agent/ → Full pipeline tests  
      ├── intent/ → Intent tests  
      ├── language/ → Language tests  
      └── template/ → Template tests  

---

##  Technologies Used

- Java 21  
- Spring Boot 3  
- Spring Web  
- Spring Data JPA  
- H2 Database (in‑memory mode)  
- Lombok  
- Ollama (MVP only)  
- JUnit 5  

---

##  How to Run the Project

### 1. Clone the repository
git clone https://github.com/joelscaila/hostpilot-backend.git  
cd hostpilot-backend

### 2. Start Ollama (MVP only)
ollama serve

### 3. Run the application
mvn spring-boot:run

API available at:  
http://localhost:8080

---

##  Main Endpoints

### POST /properties

##  Example Property

    {
      "id": 1,
      "name": "Costa Brava Apartment",
      "wifiName": "CostaBravaWifi",
      "wifiPassword": "playa1234",
      "address": "Carrer del Mar 42, Girona",
      "checkInTime": "15:00",
      "checkOutTime": "11:00",
      "houseRules": [
        "No smoking inside the apartment",
        "No parties allowed",
        "Respect quiet hours"
      ],
      "emergencyContact": "+34 600 123 456",
      "notes": "The key is in the lockbox next to the door. Code: 4829."
    }

---

### POST /agent/reply
Generates an agent response in the user's language.

**Example request:**

    {
      "propertyId": 1,
      "message": "What is the WiFi password?"
    }

**Example response:**

    {
      "reply": "The WiFi network is CostaBravaWifi and the password is playa1234."
    }

---

##  Run Tests
mvn test

---

##  Roadmap

- [ ] Add more intents (supermarket, transport, emergencies…)  
- [ ] Real persistence (Postgres)  
- [ ] Web interface for the agent  
- [ ] Owner authentication  

---
