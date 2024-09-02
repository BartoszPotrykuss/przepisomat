Aplikacja webowa (Spring + React) pozwalająca na geneorowanie przepisów kuchennych za pomocą AI, na podstawie posiadanych przez użytkownika produktów. 
Użyte technologie: Java, Spring Boot, Hibernate, Spring Data, Spring Security (token JWT), Spring Cloud, Eureka Server, API-Gateway, mySQL, JUnit, Mockito, Spring AI


Część backendowa jest oparta na mikroserwisach:

- discovery-server oparty na netflix-eureka-server
- api-gateway, który oprócz sprawowania funkcji bramy, zabezpiecza zapytania oraz sprawdza czy w zapytaniu użyty jest prawidłowy token JWT. 
- identity-service, dzięki któremu można zarejestrować użytkownika oraz uzyskać token JWT
- product-service odpowiedzialny za zarządzanie produktami, którymi użytkownik dysponuje
- recipe-service odpowiedzialny za zarządzanie i generowanie za pomocą API OpenAI przepisów

Cześć frontendowa jest oparta na React.js. 

  Film z działania aplikacji:
https://youtu.be/8qjk-7GwpKU
