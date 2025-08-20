Tematica Foro – Spring Boot 3 + JWT + H2 (Español)

Requisitos***
Java 17
Maven

Ejecutar****
mvn spring-boot:run o ejecutar com.tematica.foro.TematicaForoApplication en IntelliJ.

Endpoints******
POST /auth/login → obtiene JWT (body: { "nombreUsuario":"admin", "contrasena":"123456" })
GET /topicos y GET /topicos/{id} → públicos
POST /topicos, PUT /topicos/{id}, DELETE /topicos/{id} → requieren Authorization: Bearer <token>

H2
Consola: /h2-console
JDBC: jdbc:h2:mem:tematicaforodb, usuario sa, sin contraseña
