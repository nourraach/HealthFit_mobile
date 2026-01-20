# 🏋️ HealthFit Mobile - Application Fitness Complète

## 🎯 Vue d'Ensemble

HealthFit est une application mobile Android complète dédiée au fitness et à la nutrition, développée avec une architecture full-stack moderne. L'application combine un frontend Android natif en Kotlin avec un backend Spring Boot robuste.

## ✨ Fonctionnalités Principales

### 🔐 Authentification Sécurisée
- Inscription utilisateur avec validation complète
- Connexion avec email/mot de passe
- Stockage sécurisé du token JWT
- Persistance de session
- Déconnexion sécurisée

### 📱 Interface Utilisateur Moderne
- Thème fitness moderne (orange/noir)
- Design responsive avec Material Design
- Validation en temps réel des formulaires
- Messages d'erreur personnalisés
- Loading states et animations fluides
- Interface intuitive et ergonomique

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

## 📖 Documentation Complète

- **[Guide de Démarrage](GUIDE_DEMARRAGE.md)** - Démarrage rapide
- **[Configuration](CONFIGURATION.md)** - Configuration détaillée
- **[Architecture](ARCHITECTURE.md)** - Architecture technique
- **[Tests Manuels](TESTS_MANUELS.md)** - Plan de tests
- **[Backend Setup](backend/SETUP_GUIDE.md)** - Configuration backend

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

## 📊 Statistiques du Projet

- **Lignes de code**: ~2000+
- **Fichiers Kotlin**: 15+
- **Activités Android**: 4
- **ViewModels**: 2
- **API Endpoints**: 10+
- **Documentation**: 6 fichiers détaillés

## 🎨 Design et UX

### Palette de Couleurs
- **Primary**: #FF6B35 (Orange énergique)
- **Accent**: #FFA726 (Orange clair)
- **Background**: #1A1A1A (Noir profond)
- **Surface**: #2D2D2D (Gris foncé)

### Principes UX
- Interface intuitive et moderne
- Validation en temps réel
- Feedback visuel immédiat
- Navigation fluide
- Accessibilité optimisée

## 🔐 Sécurité

- Authentification JWT robuste
- Validation côté client et serveur
- Stockage sécurisé des données sensibles
- Protection contre les attaques communes
- Chiffrement des communications

## 🚀 Déploiement

### Développement
- Backend: `http://localhost:8089`
- Frontend: Émulateur Android ou appareil physique

### Production
- Configuration HTTPS
- Base de données sécurisée
- APK signé pour distribution

## 🤝 Contribution

Les contributions sont les bienvenues ! Veuillez consulter les guidelines de contribution avant de soumettre une pull request.

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 👨‍💻 Développeur

**Noureddine Raach**
- GitHub: [@nourraach](https://github.com/nourraach)
- LinkedIn: [Votre profil LinkedIn]

## 🙏 Remerciements

Merci à tous ceux qui ont contribué à ce projet et à la communauté open source pour les outils et bibliothèques utilisés.

---

**Développé avec ❤️ pour la communauté fitness**

*HealthFit - Votre coach fitness dans votre poche* 💪