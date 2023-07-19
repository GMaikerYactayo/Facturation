# Imagen base con Java 17
FROM openjdk:17

# Crea un directorio de trabajo en el contenedor
RUN mkdir -p /home/app

# Copiar el archivo JAR de la aplicación al contenedor
COPY target/Facturation-0.0.1-SNAPSHOT.jar /home/app/Facturation-0.0.1-SNAPSHOT.jar

# Puerto en el que la aplicación expone sus servicios
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/home/app/Facturation-0.0.1-SNAPSHOT.jar"]