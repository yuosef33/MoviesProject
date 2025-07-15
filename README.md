# 🎬 MovieAI - Smart Movie Chat & Recommendation Backend

This is a Spring Boot backend project that combines AI chat functionality with movie recommendations using external APIs such as Gemini and TMDB. It supports authentication (JWT + Google OAuth2), file upload, Redis caching, and role-based access control.

---

## 🚀 Features

- ✅ **User Authentication:**
  - Email + Password (JWT)
  - Google OAuth2 Login
  - OTP verification and password reset

- 🤖 **AI Movie Chat Assistant:**
  - Gemini API integration
  - Stores chat history temporarily in Redis
  - Context-aware movie suggestions

- 🎞️ **Movie API Integration (TMDB):**
  - Fetch all movies
  - Search by name or genre
  - Get movie by ID
  - Get all genres

- ☁️ **Redis Usage:**
  - Store OTPs with TTL
  - Store chat messages per user with TTL
  - Store verification flows
  - Config Redies Template to Store Map
    
- 📸 **Upload & Download:**
  - Upload user image (max 10MB)
  - Upload image to cloud
  - Download via link

- 💬 **AI API Communication**
  - Integrated with Gemini API to generate movie-related responses

- 🔒 **Secure with:**
  - Spring Security + JWT
  - Role-based authorization
