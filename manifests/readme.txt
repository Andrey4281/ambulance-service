#Установка postgres :
Предварительно необходимо добавить helm репу для postgres-sql:
Выполнить команду helm repo add bitnami https://charts.bitnami.com/bitnami
a) Сервис appeal
В папке manifests/appeal выполнить следующие команды:
(1) kubectl apply -f appeal-namespace.yaml
(2) kubectl apply -f appeal-secret.yaml
(3) helm -n appeal install appeal-database -f appeal-postgresql-values.yaml bitnami/postgresql
Подключение к БД:
kubectl --namespace appeal port-forward svc/appeal-database-postgresql 5434:5432


#Установка kafka:
(1) Откуда брать чарт
https://bitnami.com/stack/kafka/helm
Выполнить команду для установки:
(2) helm -n kafka install kafka-cluster bitnami/kafka

#Подключение к брокеру kafka из внешнего хоста:
(1) Перед подключением необходимо выполнить команду
helm -n kafka upgrade kafka-cluster -f kafka-values.yaml bitnami/kafka
(2) kubectl --namespace kafka port-forward svc/kafka-cluster-headless 9093:9093
(3) Возврат к прежним конфигам
helm -n kafka upgrade kafka-cluster -f original-kafka-values.yaml bitnami/kafka

#Установка cowl:
(1) helm repo add cloudhut https://raw.githubusercontent.com/cloudhut/charts/master/archives
(2) helm install -f cowl-values.yaml kowl cloudhut/kowl -n kafka
(3) Подключение к kowl для создания топиков и тд:
kubectl --namespace kafka port-forward svc/kowl 8080:80

#Установка keycloak:
(1) kubectl apply -f keycloak-namespace.yaml
(2) kubectl create -n keycloak-namespace -f https://raw.githubusercontent.com/keycloak/keycloak-quickstarts/latest/kubernetes-examples/keycloak.yaml

Для дебага:
(1) minikube tunnel
(2) go to url http://$(minikube ip):$(kubectl get services/keycloak -o go-template='{{(index .spec.ports 0).nodePort}}')

Для отката:
(1) kubectl delete namespace keycloak


#Полезные команды helm:
helm list --all-namespaces
helm uninstall kafka-cluster --namespace kafka
helm uninstall kowl --namespace kafka
helm show values bitnami/kafka > values.yaml
helm -n kafka install kafka-cluster bitnami/kafka
helm install --namespace kafka kafka-ui kafka-ui/kafka-ui --set envs.config.KAFKA_CLUSTERS_0_NAME=ambulance --set envs.config.KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=kafka-cluster-0.kafka-cluster-headless.kafka.svc.cluster.local:9092
helm -n kafka install kafka-cluster -f values.yaml bitnami/kafka

https://faun.pub/kafka-and-kowl-on-kubernetes-f9268c516870

helm -n kafka upgrade kafka-cluster -f kafka-values.yaml bitnami/kafka

helm upgrade -f cowl-values.yaml kowl cloudhut/kowl -n kafka
