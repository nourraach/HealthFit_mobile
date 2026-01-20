# 🏋️ HealthFit Mobile - Application Fitness Complète

## 🎯 Vue d'Ensemble

HealthFit est une application mobile Android complète dédiée au fitness et à la nutrition, développée avec une architecture full-stack moderne. L'application combine un frontend Android natif en Kotlin avec un backend Spring Boot robuste.

## ✨ Fonctionnalités Principales

### 🔐 Authentification Sécurisée
- Inscription utilisateur avec validation complète
- Stockage sécurisé du token JWT
- Persistance de session
- Déconnexion sécurisée

### 🏋️ Fonctionnalités Fitness
- Programmes d'entraînement personnalisés
- Plans nutritionnels avec système de favoris
- Suivi de progression en temps réel
- Système de badges motivants
- Statistiques détaillées

### 🤖 Intelligence Artificielle
- Chatbot IA intégré pour conseils fitness
- Recommandations personnalisées
- Assistance en temps réel

### 👥 Communauté
- Messagerie communautaire
- Partage d'expériences
- Motivation collective

## 🏗️ Architecture Technique

### Frontend (Android)
- **Langage**: Kotlin 100%
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI**: Material Components, ViewBinding
- **Networking**: Retrofit + Gson + OkHttp
- **Asynchrone**: Coroutines + LiveData
- **Sécurité**: JWT + SharedPreferences chiffrées

### Backend (Spring Boot)
- **Langage**: Java 17+
- **Framework**: Spring Boot + Spring Security
- **Base de données**: PostgreSQL
- **API**: REST avec JWT
- **IA**: Intégration Ollama
- **Documentation**: API complètement documentée

## 🚀 Installation et Configuration

### Prérequis
- Android Studio Arctic Fox ou supérieur
- JDK 17 ou supérieur
- PostgreSQL
- Git

### 1. Cloner le Repository
```bash
git clone https://github.com/nourraach/HealthFit_mobile.git
cd HealthFit_mobile
```

### 2. Configuration Backend
```bash
cd backend
# Configurer PostgreSQL (voir backend/SETUP_GUIDE.md)
./mvnw spring-boot:run
```

### 3. Configuration Frontend
```bash
# Ouvrir le projet Android dans Android Studio
# Configurer l'URL du backend dans RetrofitClient.kt
# Synchroniser Gradle et lancer l'application
```


## 🛠️ Technologies Utilisées

### Mobile (Android)
- Kotlin
- MVVM Architecture
- Retrofit 2.9.0
- Material Components
- Coroutines 1.7.3
- ViewBinding
- LiveData

### Backend
- Spring Boot
- Spring Security
- PostgreSQL
- JWT Authentication
- Ollama AI Integration
- Maven

