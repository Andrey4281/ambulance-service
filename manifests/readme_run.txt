Установка kafka,
В папке manifests/kafka выполнить следующие команды:
(1) kubectl apply -f kafka-namespace.yaml
(2) Откуда брать чарт
https://bitnami.com/stack/kafka/helm
Выполнить команду для установки:
(3) helm -n kafka install kafka-cluster bitnami/kafka

Установка cowl,
В папке manifests/kafka выполнить следующие команды:
(1) helm repo add cloudhut https://raw.githubusercontent.com/cloudhut/charts/master/archives
(2) helm install -f cowl-values.yaml kowl cloudhut/kowl -n kafka
(3) Подключение к kowl для создания топиков и тд:
kubectl --namespace kafka port-forward svc/kowl 8080:80

Установка postgres,
Предварительно необходимо добавить helm репу для postgres-sql,
Выполнить команду helm repo add bitnami https://charts.bitnami.com/bitnami,
a) Сервис appeal
В папке manifests/appeal выполнить следующие команды:
(1) kubectl apply -f appeal-db-namespace.yaml
(2) kubectl apply -f appeal-db-secret.yaml
(3) helm -n appeal-db install appeal-database -f appeal-postgresql-values.yaml bitnami/postgresql
Подключение к БД:
kubectl --namespace appeal-db port-forward svc/appeal-database-postgresql 5434:5432
б) Сервис doctor
В папке manifests/doctor выполнить следующие команды:
(1) kubectl apply -f doctor-db-namespace.yaml
(2) kubectl apply -f doctor-db-secret.yaml
(3) helm -n doctor-db install doctor-database -f doctor-postgresql-values.yaml bitnami/postgresql
Подключение к БД:
kubectl --namespace doctor-db port-forward svc/doctor-database-postgresql 5435:5432
в) Сервис nurse
В папке manifests/nurse выполнить следующие команды:
(1) kubectl apply -f nurse-db-namespace.yaml
(2) kubectl apply -f nurse-db-secret.yaml
(3) helm -n nurse-db install nurse-database -f nurse-postgresql-values.yaml bitnami/postgresql
Подключение к БД:
kubectl --namespace nurse port-forward svc/nurse-database-postgresql 5436:5432

Установка сервиса appeal:
В папке manifests/helm выполнить команду:
1) helm install appeal-service backend

Установка сервиса doctor:
В папке manifests/helm выполнить команду:
1) helm install -f doctor-values.yaml doctor-service backend

Установка сервиса nurse:
В папке manifests/helm выполнить команду:
1) helm install -f nurse-values.yaml nurse-service backend

Установка gateway:
В папке manifests/gateway выполнить команды:
1) kubectl apply -f gateway-namespace.yaml
2) kubectl apply -f gateway-config.yaml
3) kubectl apply -f gateway-deployment.yaml

