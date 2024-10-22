# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el JAR en el contenedor
COPY target/server_grupo5.jar /app/server_grupo5.jar

# Exponer el puerto 8081
EXPOSE 8081

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "server_grupo5.jar"]