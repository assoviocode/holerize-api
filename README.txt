# holerize-api
Projeto para importação de holerites


-- Compilando
	> mvnw clean package -Dmaven.test.skip=true

-- Gerando Imagem Docker
	> docker build --build-arg JAR_FILE=target/*.jar -t asv_holerize-api .

-- Rodando Docker
	> docker run -d --rm -p 8085:8080 asv_holerize-api .

-- Image to Google Cloud
  > docker tag  asv_holerize-api gcr.io/infra-assovio-1/asv_holerize-api
  > docker push gcr.io/infra-assovio-1/asv_holerize-api
