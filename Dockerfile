FROM openjdk:17-jdk-slim as builder

# Diretório de trabalho
WORKDIR /app

# Copiar os arquivos pom.xml e baixar dependências
COPY pom.xml .

RUN mvn dependency:go-offline

# Copiar o código fonte
COPY src /app/src

# Construir o projeto
RUN mvn clean package

# Imagem de execução
FROM openjdk:17-jdk-slim

# Diretório de trabalho
WORKDIR /app

# Copiar o JAR gerado no estágio anterior
COPY --from=builder /app/target/Order-0.0.1-SNAPSHOT.jar /app/Order-0.0.1-SNAPSHOT.jar

# Expor a porta da aplicação
EXPOSE 8081

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app/Order-0.0.1-SNAPSHOT.jar"]
