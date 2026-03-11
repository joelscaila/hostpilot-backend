# 🏡 HostPilot Backend — MVP

Backend modular para un **agente conversacional inteligente** orientado a propiedades de alquiler. Incluye detección de idioma, clasificación de intención, plantillas dinámicas, memoria contextual y generación de respuestas mediante IA.

> **Nota:** En esta fase inicial del MVP, el proyecto usa **Ollama en local** para la generación de respuestas.  
> La arquitectura está diseñada para permitir una **migración sencilla a OpenAI, Azure OpenAI o cualquier proveedor cloud** sin cambios estructurales.

---

## 🚀 Características principales

### 🧠 Agente conversacional inteligente
- Detecta automáticamente el idioma del usuario (ES, EN, FR, ZH…)
- Clasifica la intención del mensaje (wifi, check-in, normas, etc.)
- Usa plantillas predefinidas cuando existe una intención conocida
- Usa IA libre cuando no hay plantilla disponible
- Traduce la respuesta al idioma del usuario

### 🏠 Gestión de propiedades
- Información cargada desde base de datos
- Mapeo automático con DTOs
- Repositorio JPA con H2 en memoria para el MVP

### 🤖 Integración con IA (Ollama → OpenAI-ready)
- Actualmente: Ollama local para desarrollo rápido y sin costes  
- Preparado para producción: OpenAI, Azure OpenAI, Groq, DeepSeek  
- El cambio es tan simple como sustituir el cliente en `AiService`

### 🧪 Test suite completa
- Tests unitarios para:
  - IntentService  
  - LanguageService  
  - TemplateService  
  - AgentService (flujo completo)

---

## 📂 Estructura del proyecto

src/  
 ├── main/java/com/hostpilot  
 │    ├── controller/ → Endpoints REST  
 │    ├── dto/ → Modelos de entrada/salida  
 │    ├── service/ → Lógica de negocio  
 │    ├── ai/ → Cliente IA (Ollama / OpenAI-ready)  
 │    ├── intent/ → Detección de intención  
 │    ├── template/ → Plantillas por intención  
 │    ├── model/ → Entidades JPA  
 │    ├── repository/ → Repositorios Spring Data  
 │    └── config/ → Configuración WebClient  
 │  
 └── test/java/com/hostpilot  
      ├── agent/ → Tests del flujo completo  
      ├── intent/ → Tests de intención  
      ├── language/ → Tests de idioma  
      └── template/ → Tests de plantillas  

---

## 🛠️ Tecnologías utilizadas

- Java 21  
- Spring Boot 3  
- Spring Web  
- Spring Data JPA  
- H2 Database (modo memoria)  
- Lombok  
- Ollama (solo en fase MVP)  
- JUnit 5  

---

## ▶️ Cómo ejecutar el proyecto

### 1. Clonar el repositorio
git clone https://github.com/joelscaila/hostpilot-backend.git  
cd hostpilot-backend

### 2. Iniciar Ollama (solo para el MVP)
ollama serve

### 3. Ejecutar la aplicación
mvn spring-boot:run

La API estará disponible en:  
http://localhost:8080

---

## 📡 Endpoints principales

### POST /properties

## 🏡 Ejemplo de Property

    {
      "id": 1,
      "name": "Apartamento Costa Brava",
      "wifiName": "CostaBravaWifi",
      "wifiPassword": "playa1234",
      "address": "Carrer del Mar 42, Girona",
      "checkInTime": "15:00",
      "checkOutTime": "11:00",
      "houseRules": [
        "No fumar dentro del apartamento",
        "No se permiten fiestas",
        "Respetar horarios de descanso"
      ],
      "emergencyContact": "+34 600 123 456",
      "notes": "La llave está en la caja fuerte junto a la puerta. Código: 4829."
    }

---

### POST /agent/reply
Genera una respuesta del agente en el idioma del usuario.

**Ejemplo de request:**

    {
      "propertyId": 1,
      "message": "¿Cuál es la contraseña del WiFi?"
    }

**Ejemplo de response:**

    {
      "reply": "La red WiFi se llama CostaBravaWifi y la contraseña es playa1234."
    }

---

## 🧪 Ejecutar los tests
mvn test

---

## 🧭 Roadmap

- [ ] Añadir más intenciones (supermercado, transporte, emergencias…)  
- [ ] Persistencia real (Postgres)  
- [ ] Interfaz web para el agente  
- [ ] Autenticación para propietarios  

---
