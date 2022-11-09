Установка postgres :
Предварительно необходимо добавить helm репу для postgres-sql:
Выполнить команду helm repo add bitnami https://charts.bitnami.com/bitnami
a) Сервис appeal
В папке manifests/appeal выполнить следующие команды:
1) kubectl apply -f appeal-namespace.yaml
2) kubectl apply -f appeal-secret.yaml
3) helm -n appeal install appeal-database -f appeal-postgresql-values.yaml bitnami/postgresql
Подключение к БД:
kubectl --namespace appeal port-forward svc/appeal-database-postgresql 5434:5432


https://www.datree.io/helm-chart/kafka-bitnami