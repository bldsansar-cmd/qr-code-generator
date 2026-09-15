FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw -B dependency:go-offline
COPY src/ src/
RUN ./mvnw -B clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
RUN mkdir -p /app/data/images/qrcodes \
    /app/data/images/common/customize/logo \
    /app/data/images/common/customize/pixel \
    /app/data/images/common/customize/finder \
    /app/data/images/common/customize/frame \
    /app/data/images/common/customize/template
ENV IMAGE_FOLDER_QR_CODE=/app/data/images/qrcodes/ \
    IMAGE_FOLDER_CUSTOMIZE=/app/data/images/common/customize/ \
    IMAGE_FOLDER_CUSTOMIZE_LOGO=/app/data/images/common/customize/logo/ \
    IMAGE_FOLDER_COMMON=/app/data/images/common/
COPY --from=build /app/target/qrazy.war app.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.war"]
